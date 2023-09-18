package com.synaptic.xcorevpn.models

import com.synaptic.xcorevpn.AppConstants.TAG_AGENT
import com.synaptic.xcorevpn.AppConstants.TAG_BLOCKED
import com.synaptic.xcorevpn.AppConstants.TAG_DIRECT
import com.synaptic.xcorevpn.util.Utils


data class ServerConfig(
    val configVersion: Int = 3,
    val configType: ProtocolType,
    var subscriptionId: String = "",
    val addedTime: Long = System.currentTimeMillis(),
    var remarks: String = "",
    val outboundBean: XrayConfig.OutboundBean? = null,
    var fullConfig: XrayConfig? = null
) {
    companion object {
        fun create(configType: ProtocolType): ServerConfig {
            when(configType) {
                ProtocolType.VMESS, ProtocolType.VLESS ->
                    return ServerConfig(
                        configType = configType,
                        outboundBean = XrayConfig.OutboundBean(
                            protocol = configType.name.lowercase(),
                            settings = XrayConfig.OutboundBean.OutSettingsBean(
                                vnext = listOf(XrayConfig.OutboundBean.OutSettingsBean.VnextBean(
                                    users = listOf(XrayConfig.OutboundBean.OutSettingsBean.VnextBean.UsersBean())))),
                            streamSettings = XrayConfig.OutboundBean.StreamSettingsBean()))
                ProtocolType.CUSTOM, ProtocolType.WIREGUARD ->
                    return ServerConfig(configType = configType)
                ProtocolType.SHADOWSOCKS, ProtocolType.SOCKS, ProtocolType.TROJAN ->
                    return ServerConfig(
                        configType = configType,
                        outboundBean = XrayConfig.OutboundBean(
                            protocol = configType.name.lowercase(),
                            settings = XrayConfig.OutboundBean.OutSettingsBean(
                                servers = listOf(XrayConfig.OutboundBean.OutSettingsBean.ServersBean())),
                            streamSettings = XrayConfig.OutboundBean.StreamSettingsBean()))
            }
        }
    }

    fun getProxyOutbound(): XrayConfig.OutboundBean? {
        if (configType != ProtocolType.CUSTOM) {
            return outboundBean
        }
        return fullConfig?.getProxyOutbound()
    }

    fun getAllOutboundTags(): MutableList<String> {
        if (configType != ProtocolType.CUSTOM) {
            return mutableListOf(TAG_AGENT, TAG_DIRECT, TAG_BLOCKED)
        }
        fullConfig?.let { config ->
            return config.outbounds.map { it.tag }.toMutableList()
        }
        return mutableListOf()
    }

    fun getV2rayPointDomainAndPort(): String {
        val address = getProxyOutbound()?.getServerAddress().orEmpty()
        val port = getProxyOutbound()?.getServerPort()
        return if (Utils.isIpv6Address(address)) {
            String.format("[%s]:%s", address, port)
        } else {
            String.format("%s:%s", address, port)
        }
    }
}
