package com.synaptic.vpn_core_lib.core


import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.synaptic.vpn_core_lib.interfaces.ServiceControl


import java.lang.ref.SoftReference

class XRayProxyOnlyService : Service(), ServiceControl {
    override fun onCreate() {
        super.onCreate()
        XRayServiceManager.serviceControl = SoftReference(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        XRayServiceManager.startV2rayPoint()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        XRayServiceManager.stopV2rayPoint()
    }

    override fun getService(): Service {
        return this
    }

    override fun startService() {
        // do nothing
    }

    override fun stopService() {
        stopSelf()
    }

    override fun vpnProtect(socket: Int): Boolean {
        return true
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
