package com.synaptic.xcorevpn.models


enum class ProtocolType(val value: Int, val protocolScheme: String) {
    VMESS(1, "vmess://"),
    CUSTOM(2, ""),
    SHADOWSOCKS(3, "ss://"),
    SOCKS(4, "socks://"),
    VLESS(5, "vless://"),
    TROJAN(6, "trojan://"),
    WIREGUARD(7, "wireguard://");

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value }
        fun fromString(value: String?) = run {
            when (value?.lowercase()){
                "vmess" -> VMESS
                "ss"-> SHADOWSOCKS
                "socks" -> SOCKS
                "vless" -> VLESS
                "trojan" -> TROJAN
                "wireguard" -> WIREGUARD
                "" -> CUSTOM
                else -> null
            }
        }
    }
}