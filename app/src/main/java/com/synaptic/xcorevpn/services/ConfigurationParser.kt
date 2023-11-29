package com.synaptic.xcorevpn.services

import android.net.Uri
import android.util.Log
import com.synaptic.vpn_core_lib.VpnCorePlugin
import com.synaptic.vpn_core_lib.setup.models.ProtocolType
import com.synaptic.vpn_core_lib.setup.models.ServerConfig
import com.synaptic.vpn_core_lib.setup.models.XrayConfig
import com.synaptic.xcorevpn.AppConstants
import com.synaptic.xcorevpn.extensions.fromBase64
import com.synaptic.xcorevpn.models.ConfigEvent
import com.synaptic.xcorevpn.services.network.Network

import java.util.UUID

object ConfigurationParser {

    /*
    Parse configuration from Uri with custom schema
    */
    fun getConfiguration(url: Uri) {
        val cleanedURL = cleanURI(url) ?: return
        if (!validateURL(cleanedURL)) return
        Network().httpGet(cleanedURL.toString()) {
            parseConfiguration(it)
        }

    }

    private fun cleanURI(url: Uri): Uri? {
        return Uri.parse(url.toString().replace("xcoredemo://import/", ""))
    }

    private fun validateURL(url: Uri): Boolean {
        return url.scheme == "https"
    }

    private fun parseConfiguration(encodedConfig: String) {
        val decodedConfig = encodedConfig.fromBase64()
        val configurationList = decodedConfig.split("\n")
        configurationList.forEach { saveConfig(config = it) }
        EventHandler().postEvent(ConfigEvent.NeedUpdate)
        Log.d(AppConstants.ANG_PACKAGE, "parse complete")
    }

    private fun saveConfig(config: String) {
        val configURI = Uri.parse(config)
        val protocolType = ProtocolType.fromString(configURI.scheme) ?: return
        val uuid = configURI.userInfo ?: return
        val address = configURI.host ?: return
        val port = configURI.port

        val security = configURI.getQueryParameter("security") ?: return
        val sni = configURI.getQueryParameter("sni") ?: return
        val fingerprint = configURI.getQueryParameter("fp") ?: return
        val publicKey = configURI.getQueryParameter("pbk") ?: return
        val shortID = configURI.getQueryParameter("sid") ?: return
        val transport = configURI.getQueryParameter("type") ?: return
        val flow = configURI.getQueryParameter("flow") ?: return

        val country = configURI.fragment ?: "Unknown"

        val serverConfig = ServerConfig.create(protocolType)

        serverConfig.remarks = country
        serverConfig.outboundBean?.settings?.vnext?.get(0)?.let { vnext ->
            saveVnext(vnext, port, serverConfig, address = address, flow = flow, uuid = uuid)
        }
        serverConfig.outboundBean?.settings?.servers?.get(0)?.let { server ->
            saveServers(server, port, serverConfig, address, uuid, security)
        }
        serverConfig.outboundBean?.streamSettings?.let {
            saveStreamSettings(
                it,
                transport = transport,
                sniString = sni,
                security = security,
                fingerprint = fingerprint,
                publicKey = publicKey,
                shortId = shortID,
            )
        }
        if(serverConfig.subscriptionId.isEmpty()) {
            serverConfig.subscriptionId = UUID.randomUUID().toString()
        }
        VpnCorePlugin.shared.saveServer(serverConfig.subscriptionId, serverConfig)
    }

    private fun saveStreamSettings(
        streamSetting: XrayConfig.OutboundBean.StreamSettingsBean,
        transport: String,
        sniString: String,
        headerType: String = "none",
        requestHost: String = "",
        path: String = "",
        alpns: String = "",
        security: String,
        fingerprint: String,
        publicKey: String,
        shortId: String,
        spiderX: String = "",
        allowinSecures: Boolean? = null
    ) {

        var sni = streamSetting.populateTransportSettings(
            transport = transport,
            headerType = headerType,
            host = requestHost,
            path = path,
            seed = path,
            quicSecurity = requestHost,
            key = path,
            mode = headerType,
            serviceName = path
        )
        if (sniString.isNotBlank()) {
            sni = sniString
        }
        val allowInsecure =
            allowinSecures ?:  false

        streamSetting.populateTlsSettings(
            streamSecurity = security,
            allowInsecure = allowInsecure,
            sni = sni,
            fingerprint = fingerprint,
            alpns = alpns,
            publicKey = publicKey,
            shortId = shortId,
            spiderX = spiderX
        )
    }

    private fun transportTypes(network: String?): Array<out String> {
        return when (network) {
            "tcp" -> {
                arrayOf("none", "http")
            }

            "kcp", "quic" -> {
                arrayOf("none", "srtp", "utp", "wechat-video", "dtls", "wireguard")
            }

            "grpc" -> {
                arrayOf("gun", "multi")
            }

            else -> {
                arrayOf("---")
            }
        }
    }

    private fun saveServers(
        server: XrayConfig.OutboundBean.OutSettingsBean.ServersBean,
        port: Int,
        config: ServerConfig,
        address: String,
        uuid: String,
        security: String
    ) {
        server.address = address
        server.port = port

// TODO implement SHADOWSOCKS, SOCKS, TROJAN (SHADOWSOCKS server.method)
//        if (config.configType == ProtocolType.SHADOWSOCKS) {
//            server.password = uuid
//            server.method = shadowsocksSecuritys[sp_security?.selectedItemPosition ?: 0]
//        } else if (config.configType == ProtocolType.SOCKS) {
//            if (TextUtils.isEmpty(security) && TextUtils.isEmpty(uuid)) {
//                server.users = null
//            } else {
//                val socksUsersBean =
//                    XrayConfig.OutboundBean.OutSettingsBean.ServersBean.SocksUsersBean()
//                socksUsersBean.user = security
//                socksUsersBean.pass = uuid
//                server.users = listOf(socksUsersBean)
//            }
//        } else if (config.configType == ProtocolType.TROJAN) {
//            server.password = uuid
//        }
    }

    private fun saveVnext(
        vnext: XrayConfig.OutboundBean.OutSettingsBean.VnextBean,
        port: Int,
        config: ServerConfig,
        address: String,
        uuid: String,
        flow: String,
        security: String = "none"
    ) {
        vnext.address = address
        vnext.port = port
        vnext.users[0].id = uuid
// TODO Implement VMESS
//        if (config.configType == ProtocolType.VMESS) {
//
//            vnext.users[0].alterId = Utils.parseInt(et_alterId?.text.toString())
//            vnext.users[0].security = securitys[sp_security?.selectedItemPosition ?: 0]
//        }
        if (config.configType == ProtocolType.VLESS) {
            vnext.users[0].encryption = security
            vnext.users[0].flow = flow
        }
    }


}