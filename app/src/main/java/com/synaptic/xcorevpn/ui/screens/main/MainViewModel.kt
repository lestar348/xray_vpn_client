//package com.synaptic.xcorevpn.ui.screens.main
//
//import android.content.Context
//import android.net.VpnService
//import androidx.lifecycle.ViewModel
//import com.synaptic.xcorevpn.AppConstants
//import com.synaptic.xcorevpn.models.ConfigEvent
//import com.synaptic.xcorevpn.models.VpnState
//import com.synaptic.xcorevpn.services.XRayServiceManager
//import com.synaptic.xcorevpn.util.MmkvManager
//import com.synaptic.xcorevpn.util.Utils
//import com.tencent.mmkv.MMKV
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//
//
//class MainViewModel : ViewModel() {
//    private val settingsStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_SETTING, MMKV.MULTI_PROCESS_MODE) }
//    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }
//
//    private val _serverList by lazy {
//        var list = MmkvManager.decodeServerList()
//        if(list.isEmpty()){
//            updateVPNState(VpnState.NoConfigFile)
//        } else {
//            updateVPNState(VpnState.Disable)
//        }
//        return@lazy MutableStateFlow(list)
//    }
//
//
//    private val _vpnState = MutableStateFlow(VpnState.Unknown)
//
//    val vpnState: StateFlow<VpnState> = _vpnState.asStateFlow()
//    val serverList: StateFlow<MutableList<String>> = _serverList.asStateFlow()
//
//    fun handleConfigEvent(configEvent: ConfigEvent){
//        if(configEvent == ConfigEvent.NeedUpdate){
//            updateServerList()
//        }
//    }
//
//    /*
//    * Activate vpn, return false if need request VPN permission
//    * */
//    // TODO replace Boolean return value to Error?
//    fun activeVPN(context: Context) : Boolean {
//        updateVPNState( VpnState.Connecting )
//        if ((settingsStorage?.decodeString(AppConstants.PREF_MODE) ?: "VPN") == "VPN") {
//            val intent = VpnService.prepare(context)
//            if (intent == null) {
//                startXray(context)
//            } else {
//                return false
//            }
//        }else{
//            startXray(context)
//        }
//        updateVPNState( VpnState.Active )
//        return true
//    }
//
//    fun disableVPN(context: Context) {
//        Utils.stopVService(context)
//        updateVPNState( VpnState.Disable )
//    }
//
//    private fun startXray(context: Context){
//        if (mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER).isNullOrEmpty()) {
//            return
//        }
//        XRayServiceManager.startV2Ray(context)
//    }
//
//    private fun updateServerList(){
//        _serverList.update { MmkvManager.decodeServerList() }
//        if(serverList.value.isEmpty()){
//            updateVPNState(VpnState.NoConfigFile)
//        }else if(vpnState.value == VpnState.NoConfigFile || vpnState.value == VpnState.Unknown) {
//            updateVPNState(VpnState.Disable)
//        }
//    }
//
//    private fun updateVPNState(newState: VpnState){
//        _vpnState.update { newState }
//    }
//}