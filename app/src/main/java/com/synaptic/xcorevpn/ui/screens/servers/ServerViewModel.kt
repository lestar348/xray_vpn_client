package com.synaptic.xcorevpn.ui.screens.servers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.synaptic.vpn_core_lib.VpnCorePlugin
import com.synaptic.vpn_core_lib.setup.models.ServerConfig

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServerViewModel: ViewModel() {

    private val _selectedServerUUID by lazy {
        MutableStateFlow(VpnCorePlugin.shared.selectedServerUUID)
    }

    val selectedServerUUID: StateFlow<String?> = _selectedServerUUID.asStateFlow()

    var serverList = VpnCorePlugin.shared.allServersConfigs

    fun onServerTap(server: ServerConfig){
        if(server.subscriptionId != _selectedServerUUID.value) {
            VpnCorePlugin.shared.selectServer(server.subscriptionId)
            _selectedServerUUID.update {
                server.subscriptionId
            }
        }
    }
}