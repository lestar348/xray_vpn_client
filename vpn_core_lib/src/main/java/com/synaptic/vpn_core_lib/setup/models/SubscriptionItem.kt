package com.synaptic.vpn_core_lib.setup.models

data class SubscriptionItem(
    var remarks: String = "",
    var url: String = "",
    var enabled: Boolean = true,
    val addedTime: Long = System.currentTimeMillis()) {
}
