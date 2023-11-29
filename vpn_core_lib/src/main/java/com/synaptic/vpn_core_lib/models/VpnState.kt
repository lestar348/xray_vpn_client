package com.synaptic.vpn_core_lib.models

enum class VpnState() {
    Active,
    Disable,
    Connecting,
    Disconnecting,
    NoConfigFile,
    Unknown;

    val statusText: String get() = when(this){
        Active-> "VPN on"
        Disable -> "VPN off"
        Connecting -> "Connection..."
        Disconnecting -> "Disconnecting..."
        NoConfigFile -> "Please accept VPN Configuration"
        Unknown -> "Unknown"

    }
}