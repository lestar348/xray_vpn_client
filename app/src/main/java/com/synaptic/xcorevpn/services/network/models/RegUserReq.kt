package com.synaptic.xcorevpn.services.network.models

import com.google.gson.annotations.SerializedName

data class RegUserReq (
    @SerializedName("device_id") val deviceId: String,
    val email: String,
    @SerializedName("device_model") val deviceModel: String
)
