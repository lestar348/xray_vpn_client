package com.synaptic.xcorevpn.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.synaptic.xcorevpn.models.VPNConnectException
import com.synaptic.xcorevpn.models.VpnState
import kotlinx.coroutines.flow.StateFlow

abstract class HomeViewModel: ViewModel() {

    abstract val vpnState: StateFlow<VpnState>

    abstract val currentServerLocation: StateFlow<String?>

    @Throws(VPNConnectException::class)
    abstract fun toggleVPN(context: Context)

    // TODO add subscription variable and method
}