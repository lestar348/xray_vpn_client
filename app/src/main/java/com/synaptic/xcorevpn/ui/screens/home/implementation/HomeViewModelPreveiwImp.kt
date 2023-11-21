package com.synaptic.xcorevpn.ui.screens.home.implementation

import android.content.Context
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModelPreveiwImp: HomeViewModel() {
    private val _vpnState = MutableStateFlow(VpnState.Unknown)
    private val _currentServer = MutableStateFlow("Poland")

    override val vpnState = _vpnState.asStateFlow()
    override val currentServerLocation = _currentServer.asStateFlow()

    override fun toggleVPN(context: Context) {
        _vpnState.update { VpnState.Active }
    }

}