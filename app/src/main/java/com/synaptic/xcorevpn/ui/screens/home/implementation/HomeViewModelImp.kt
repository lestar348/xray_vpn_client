package com.synaptic.xcorevpn.ui.screens.home.implementation

import android.content.Context
import com.synaptic.vpn_core_lib.VpnCorePlugin
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModelImp : HomeViewModel() {

    private val _vpnState = MutableStateFlow(VpnState.Unknown)

    private val _currentServer by lazy{
        val currentServerID = VpnCorePlugin.shared.selectedServerUUID ?: return@lazy MutableStateFlow(null)
        val config = VpnCorePlugin.shared.serverConfigByID(currentServerID) ?: return@lazy MutableStateFlow(null)
        return@lazy MutableStateFlow(config.remarks)
    }

    override val vpnState = _vpnState.asStateFlow()
    override val currentServerLocation = _currentServer.asStateFlow()

    init {
        if(_currentServer.value == null){
            updateVPNState(VpnState.NoConfigFile)
        }else{
            updateVPNState(VpnState.Disable)
        }
    }

    override fun toggleVPN(context: Context) {
        if (_vpnState.value == VpnState.Active) {
            VpnCorePlugin.shared.stopVPN(context)
            updateVPNState( VpnState.Disable )
        } else if (_vpnState.value == VpnState.Disable) {
            startVPN(context)
        }
    }

    private fun startVPN(context: Context) {
        updateVPNState(VpnState.Connecting)
        VpnCorePlugin.shared.startVPN(context)
        updateVPNState(VpnState.Active)
    }

    private fun updateVPNState(newState: VpnState) {
        _vpnState.update { newState }
    }
}