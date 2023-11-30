package com.synaptic.vpn_core_lib.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.synaptic.vpn_core_lib.ConfigurationConstants
import com.synaptic.vpn_core_lib.ConfigurationConstants.ANG_PACKAGE
import com.synaptic.vpn_core_lib.R
import com.synaptic.vpn_core_lib.VpnCorePlugin
import com.synaptic.vpn_core_lib.interfaces.ServiceControl
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.utils.MessageUtil
import com.synaptic.vpn_core_lib.utils.Utils
import com.synaptic.vpn_core_lib.utils.XrayConfigUtil
import com.tencent.mmkv.MMKV
import go.Seq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import libv2ray.Libv2ray
import libv2ray.V2RayPoint
import libv2ray.V2RayVPNServiceSupportsSet
import rx.Subscription
import java.lang.ref.SoftReference


object XRayServiceManager {

    private const val NOTIFICATION_ID = 1
    private const val NOTIFICATION_PENDING_INTENT_CONTENT = 0
    private const val NOTIFICATION_PENDING_INTENT_STOP_V2RAY = 1
    private const val NOTIFICATION_ICON_THRESHOLD = 3000

    private var mNotificationManager: NotificationManager? = null
    private var mBuilder: NotificationCompat.Builder? = null

    val v2rayPoint: V2RayPoint =
        Libv2ray.newV2RayPoint(V2RayCallback(), Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
    private val mMsgReceive = ReceiveMessageHandler()
    private val mainStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_MAIN,
            MMKV.MULTI_PROCESS_MODE
        )
    }
    private val settingsStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_SETTING,
            MMKV.MULTI_PROCESS_MODE
        )
    }

    var serviceControl: SoftReference<ServiceControl>? = null
        set(value) {
            field = value
            Seq.setContext(value?.get()?.getService()?.applicationContext)
            Libv2ray.initV2Env(Utils.userAssetPath(value?.get()?.getService()))
        }
    var currentConfig: ServerConfig? = null

    private var lastQueryTime = 0L
    private var mSubscription: Subscription? = null

    fun startV2Ray(context: Context) {

        val intent =
            if (settingsStorage?.decodeString(ConfigurationConstants.PREF_MODE) ?: "VPN" == "VPN") {
                Intent(context.applicationContext, XRayVpnService::class.java)
            } else {
                Intent(context.applicationContext, XRayProxyOnlyService::class.java)
            }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            Log.d(ANG_PACKAGE, "VPN startForeground Service")
            context.startForegroundService(intent)
        } else {
            Log.d(ANG_PACKAGE, "VPN start Service")
            context.startService(intent)
        }
    }

    private class V2RayCallback : V2RayVPNServiceSupportsSet {
        override fun shutdown(): Long {
            val serviceControl = serviceControl?.get() ?: return -1
            // called by go
            return try {
                serviceControl.stopService()
                0
            } catch (e: Exception) {
                Log.d(ANG_PACKAGE, e.toString())
                -1
            }
        }

        override fun prepare(): Long {
            return 0
        }

        override fun protect(l: Long): Boolean {
            val serviceControl = serviceControl?.get() ?: return true
            return serviceControl.vpnProtect(l.toInt())
        }

        override fun onEmitStatus(l: Long, s: String?): Long {
            //Logger.d(s)
            return 0
        }

        override fun setup(s: String): Long {
            val serviceControl = serviceControl?.get() ?: return -1
            //Logger.d(s)
            return try {
                serviceControl.startService()
                lastQueryTime = System.currentTimeMillis()
                0
            } catch (e: Exception) {
                Log.d(ANG_PACKAGE, e.toString())
                -1
            }
        }
    }

    fun startV2rayPoint() {
        Log.d(ANG_PACKAGE, "V2rayPoint starting")
        val service = serviceControl?.get()?.getService() ?:  return
        Log.d(ANG_PACKAGE, "V2rayPoint get service")
        val guid = mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER) ?: return
        Log.d(ANG_PACKAGE, "V2rayPoint get guid")
        val config = MmkvManager.decodeServerConfig(guid) ?: return
        Log.d(ANG_PACKAGE, "V2rayPoint get config")
        if (v2rayPoint.isRunning) {
            return
        }

        val result = XrayConfigUtil.getV2rayConfig(service, guid)
        if (!result.status) {
            Log.e(ANG_PACKAGE, "V2rayPoint can't get V2rayConfig")
            return
        }

        try {
            val mFilter = IntentFilter(ConfigurationConstants.BROADCAST_ACTION_SERVICE)
            mFilter.addAction(Intent.ACTION_SCREEN_ON)
            mFilter.addAction(Intent.ACTION_SCREEN_OFF)
            mFilter.addAction(Intent.ACTION_USER_PRESENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                service.registerReceiver(mMsgReceive, mFilter, RECEIVER_EXPORTED)
            }else{
                service.registerReceiver(mMsgReceive, mFilter)
            }

        } catch (e: Exception) {
            Log.d(ANG_PACKAGE, e.toString())
        }

        v2rayPoint.configureFileContent = result.content
        v2rayPoint.domainName = config.getV2rayPointDomainAndPort()
        currentConfig = config

        try {
            v2rayPoint.runLoop(
                settingsStorage?.decodeBool(ConfigurationConstants.PREF_PREFER_IPV6) ?: false
            )
        } catch (e: Exception) {
            Log.d(ANG_PACKAGE, e.toString())
        }

        if (v2rayPoint.isRunning) {
            MessageUtil.sendMsg2UI(service, ConfigurationConstants.MSG_STATE_START_SUCCESS, "")
            showNotification()
        } else {
            MessageUtil.sendMsg2UI(service, ConfigurationConstants.MSG_STATE_START_FAILURE, "")
            cancelNotification()
        }

    }

    fun stopV2rayPoint() {
        val service = serviceControl?.get()?.getService() ?: return

        if (v2rayPoint.isRunning) {
            GlobalScope.launch(Dispatchers.Default) {
                try {
                    v2rayPoint.stopLoop()
                } catch (e: Exception) {
                    Log.d(ANG_PACKAGE, e.toString())
                }
            }
        }

        MessageUtil.sendMsg2UI(service, ConfigurationConstants.MSG_STATE_STOP_SUCCESS, "")
        cancelNotification()

        try {
            service.unregisterReceiver(mMsgReceive)
        } catch (e: Exception) {
            Log.d(ANG_PACKAGE, e.toString())
        }
    }

    private class ReceiveMessageHandler : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val serviceControl = serviceControl?.get() ?: return
            when (intent?.getIntExtra("key", 0)) {
                ConfigurationConstants.MSG_REGISTER_CLIENT -> {
                    //Logger.e("ReceiveMessageHandler", intent?.getIntExtra("key", 0).toString())
                    if (v2rayPoint.isRunning) {
                        MessageUtil.sendMsg2UI(
                            serviceControl.getService(),
                            ConfigurationConstants.MSG_STATE_RUNNING,
                            ""
                        )
                    } else {
                        MessageUtil.sendMsg2UI(
                            serviceControl.getService(),
                            ConfigurationConstants.MSG_STATE_NOT_RUNNING,
                            ""
                        )
                    }
                }

                ConfigurationConstants.MSG_UNREGISTER_CLIENT -> {
                    // nothing to do
                }

                ConfigurationConstants.MSG_STATE_START -> {
                    // nothing to do
                }

                ConfigurationConstants.MSG_STATE_STOP -> {
                    serviceControl.stopService()
                }

                ConfigurationConstants.MSG_STATE_RESTART -> {
                    startV2rayPoint()
                }

                ConfigurationConstants.MSG_MEASURE_DELAY -> {
                    measureV2rayDelay()
                }
            }
        }
    }

    private fun measureV2rayDelay() {
        GlobalScope.launch(Dispatchers.IO) {
            val service = serviceControl?.get()?.getService() ?: return@launch
            var time = -1L
            var errstr = ""
            if (v2rayPoint.isRunning) {
                try {
                    time = v2rayPoint.measureDelay()
                } catch (e: Exception) {
                    Log.d(ANG_PACKAGE, "measureV2rayDelay: $e")
                    errstr = e.message?.substringAfter("\":") ?: "empty message"
                }
            }
            val result = if (time == -1L) {
                "Fail to detect internet connection: $errstr"
            } else {
                "Success: HTTP connection took $time"
            }

            MessageUtil.sendMsg2UI(
                service,
                ConfigurationConstants.MSG_MEASURE_DELAY_SUCCESS,
                result
            )
        }
    }

    private fun showNotification() {
        val service = serviceControl?.get()?.getService() ?: return

        val startMainIntent = Intent(service, VpnCorePlugin.shared.mainIntentClass::class.java)
        //val startMainIntent = Intent(service, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(service,
            NOTIFICATION_PENDING_INTENT_CONTENT, startMainIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopV2RayIntent = Intent(ConfigurationConstants.BROADCAST_ACTION_SERVICE)
        stopV2RayIntent.`package` = ANG_PACKAGE
        stopV2RayIntent.putExtra("key", ConfigurationConstants.MSG_STATE_STOP)

        val stopV2RayPendingIntent = PendingIntent.getBroadcast(service,
            NOTIFICATION_PENDING_INTENT_STOP_V2RAY, stopV2RayIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        mBuilder = NotificationCompat.Builder(service, channelId)
            .setContentTitle(currentConfig?.remarks)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setShowWhen(false)
            .setOnlyAlertOnce(true)
            .setContentIntent(contentPendingIntent)
            .addAction(
                R.drawable.ic_close_grey_800_24dp,
                service.getString(R.string.notification_action_stop_v2ray),
                stopV2RayPendingIntent)
        //.build()

        //mBuilder?.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)  //取消震动,铃声其他都不好使

        service.startForeground(NOTIFICATION_ID, mBuilder?.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "RAY_NG_M_CH_ID"
        val channelName = "V2rayNG Background Service"
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.DKGRAY
        chan.importance = NotificationManager.IMPORTANCE_NONE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getNotificationManager()?.createNotificationChannel(chan)
        return channelId
    }

    fun cancelNotification() {
        val service = serviceControl?.get()?.getService() ?: return
        service.stopForeground(true)
        mBuilder = null
        mSubscription?.unsubscribe()
        mSubscription = null
    }

//    private fun updateNotification(contentText: String?, proxyTraffic: Long, directTraffic: Long) {
//        if (mBuilder != null) {
//            if (proxyTraffic < NOTIFICATION_ICON_THRESHOLD && directTraffic < NOTIFICATION_ICON_THRESHOLD) {
//                mBuilder?.setSmallIcon(R.drawable.ic_stat_name)
//            } else if (proxyTraffic > directTraffic) {
//                mBuilder?.setSmallIcon(R.drawable.ic_stat_name)
//            } else {
//                mBuilder?.setSmallIcon(R.drawable.ic_stat_name)
//            }
//            mBuilder?.setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
//            mBuilder?.setContentText(contentText) // Emui4.1 need content text even if style is set as BigTextStyle
//            getNotificationManager()?.notify(NOTIFICATION_ID, mBuilder?.build())
//        }
//    }

    private fun getNotificationManager(): NotificationManager? {
        if (mNotificationManager == null) {
            val service = serviceControl?.get()?.getService() ?: return null
            mNotificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mNotificationManager
    }

}
