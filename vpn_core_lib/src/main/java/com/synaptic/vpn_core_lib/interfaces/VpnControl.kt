package com.synaptic.vpn_core_lib.interfaces

import android.content.Context
import com.synaptic.vpn_core_lib.models.VPNConnectException
import com.synaptic.vpn_core_lib.models.VpnState

interface VpnControl {

    var vpnState: VpnState

    @Throws(VPNConnectException::class)
    fun startVPN(context: Context)

    fun stopVPN(context: Context)
}