package com.synaptic.xcorevpn.services.network.models

import com.google.gson.annotations.SerializedName

data class CheckStatusResp (
    val result: String,
    val subscription: Subscription
)

data class Subscription (
    val service: Long,
    val status: String,
    val cost: String,
    @SerializedName("prodamus_order_id") val prodamusOrderid: Long,
    @SerializedName("finish_date") val finishDate: String
)