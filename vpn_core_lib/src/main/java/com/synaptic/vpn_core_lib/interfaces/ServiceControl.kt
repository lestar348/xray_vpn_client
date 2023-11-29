package com.synaptic.vpn_core_lib.interfaces

import android.app.Service

interface ServiceControl {
    fun getService(): Service

    fun startService()

    fun stopService()

    fun vpnProtect(socket: Int): Boolean
}