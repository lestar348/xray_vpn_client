package com.synaptic.xcorevpn.ui.screens.servers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.synaptic.xcorevpn.AppConstants.ANG_PACKAGE
import com.synaptic.xcorevpn.models.ServerConfig
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.util.MmkvManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServerViewModel: ViewModel() {

    private val _selectedServerUUID by lazy {
        MutableStateFlow(MmkvManager.getSelectedServerUUID())
    }

    val selectedServerUUID: StateFlow<String?> = _selectedServerUUID.asStateFlow()

    var serverList = MmkvManager.decodeServerList().map {
        MmkvManager.decodeServerConfig(guid = it)
    }

    fun onServerTap(server: ServerConfig){
        if(server.subscriptionId != _selectedServerUUID.value) {
            MmkvManager.selectServer(server.subscriptionId)
            _selectedServerUUID.update {
                server.subscriptionId
            }
        }
    }
}