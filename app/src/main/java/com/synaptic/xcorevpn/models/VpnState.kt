package com.synaptic.xcorevpn.models

enum class VpnState() {
    Active,
    Disable,
    Connecting,
    NoConfigFile,
    Unknown;

    val statusText: String get() = when(this){
        Active-> "VPN on"
        Disable -> "VPN off"
        Connecting -> "Connection..."
        NoConfigFile -> "Please accept VPN Configuration"
        Unknown -> "Unknown"
    }
}


