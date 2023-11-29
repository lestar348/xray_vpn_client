package com.synaptic.vpn_core_lib.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.synaptic.vpn_core_lib.ConfigurationConstants
import com.synaptic.vpn_core_lib.ConfigurationConstants.ANG_PACKAGE
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
//        if (settingsStorage?.decodeBool(ConfigurationConstants.PREF_PROXY_SHARING) == true) {
//            //context.toast(R.string.toast_warning_pref_proxysharing_short)
//        } else {
//            // context.toast(R.string.toast_services_start)
//        }
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
            service.registerReceiver(mMsgReceive, mFilter)
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


    fun cancelNotification() {
        val service = serviceControl?.get()?.getService() ?: return
        service.stopForeground(true)
        mSubscription?.unsubscribe()
        mSubscription = null
    }


}
