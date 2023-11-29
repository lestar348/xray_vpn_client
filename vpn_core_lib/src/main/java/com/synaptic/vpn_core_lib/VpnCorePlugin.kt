package com.synaptic.vpn_core_lib

import android.content.Context
import android.net.VpnService
import android.util.Log
import com.synaptic.vpn_core_lib.interfaces.VpnControl
import com.synaptic.vpn_core_lib.interfaces.VpnSettings
import com.synaptic.vpn_core_lib.models.VPNConnectException
import com.synaptic.vpn_core_lib.models.VpnState
import com.synaptic.vpn_core_lib.setup.MmkvManager
import com.synaptic.vpn_core_lib.setup.models.ServerAffiliationInfo
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.utils.Utils

import com.tencent.mmkv.MMKV


/** VpnCorePlugin */
class VpnCorePlugin(context: Context) : VpnControl, VpnSettings {

    init {
        MMKV.initialize(context)
        shared = this
    }

    companion object {
        lateinit var shared: VpnCorePlugin
    }

    override var vpnState: VpnState = VpnState.Unknown

    override var selectedServerUUID: String? = null
        get() = MmkvManager.getSelectedServerUUID()

    override var allServersConfigs: List<ServerConfig> = listOf()
        get() {
            val serversGUIDs = savedServersID()
            var configs = mutableListOf<ServerConfig>()
            for(configID in serversGUIDs){
                val serverConf = serverConfigByID(configID) ?: continue
                configs.add(serverConf)
            }
            return configs
        }

    override var allServersServerAffiliationInfo: List<ServerAffiliationInfo> = listOf()
        get() {
            val serversGUIDs = savedServersID()
            var configs = mutableListOf<ServerAffiliationInfo>()
            for(configID in serversGUIDs){
                val serverConf = serverAffiliationInfoByID(configID) ?: continue
                configs.add(serverConf)
            }
            return configs
        }


    // VPN control section

    override fun startVPN(context: Context) {
        Log.d(ConfigurationConstants.ANG_PACKAGE, "Start vpn connection")
        val intent = VpnService.prepare(context)
        if (intent == null) {
            val success = Utils.startVServiceFromToggle(context)
            if (!success) {
                throw VPNConnectException.internalError
            }
            Log.d(ConfigurationConstants.ANG_PACKAGE, "Connected")
        } else {
            Log.e(ConfigurationConstants.ANG_PACKAGE, "need vpn permission")
            throw VPNConnectException.needPermission
        }
    }

    override fun stopVPN(context: Context) = Utils.stopVService(context)

    // VPN Settings section

    override fun serverConfigByID(guid: String): ServerConfig? = MmkvManager.decodeServerConfig(guid)

    override fun serverAffiliationInfoByID(guid: String): ServerAffiliationInfo? = MmkvManager.decodeServerAffiliationInfo(guid)

    override fun savedServersID(): MutableList<String> = MmkvManager.decodeServerList()

    override fun selectServer(guid: String) = MmkvManager.selectServer(guid)

    override fun saveServer(guid: String, config: ServerConfig): String = MmkvManager.encodeServerConfig(guid, config)

    override fun removeServerConfig(guid: String) = MmkvManager.removeServer(guid)

}
