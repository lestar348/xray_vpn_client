package com.synaptic.xcorevpn.ui.screens.home.implementation

import android.content.Context
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.app.ActivityCompat.startActivityForResult

import com.synaptic.xcorevpn.AppConstants
import com.synaptic.xcorevpn.models.ConnectProblem
import com.synaptic.xcorevpn.models.VPNConnectException
import com.synaptic.xcorevpn.models.VpnState
import com.synaptic.xcorevpn.services.XRayServiceManager
import com.synaptic.xcorevpn.ui.screens.home.HomeViewModel
import com.synaptic.xcorevpn.util.MmkvManager
import com.synaptic.xcorevpn.util.Utils
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModelImp : HomeViewModel() {

    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }

    private val _vpnState = MutableStateFlow(VpnState.Unknown)

    private val _currentServer by lazy{
        val currentServerID = mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER) ?: return@lazy MutableStateFlow(null)
        val config = MmkvManager.decodeServerConfig(currentServerID) ?: return@lazy MutableStateFlow(null)
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
            Utils.stopVService(context)
            updateVPNState( VpnState.Disable )
        } else if (_vpnState.value == VpnState.Disable) {
            startVPN(context)
        }
    }

    private fun startVPN(context: Context) {
        val success: Boolean
        updateVPNState(VpnState.Connecting)
        val intent = VpnService.prepare(context)
        if (intent == null) {
            success = Utils.startVServiceFromToggle(context)
        } else {
            updateVPNState(VpnState.Disable)
            throw VPNConnectException(message = "Need accept configuration", problem = ConnectProblem.NeedAcceptPermission)
        }
        if(success){
            updateVPNState(VpnState.Active)
        } else{
            updateVPNState(VpnState.Disable)
            throw VPNConnectException(message = "Internal Error", problem = ConnectProblem.InternalError)
        }
    }

    private fun updateVPNState(newState: VpnState) {
        _vpnState.update { newState }
    }
}