package com.synaptic.xcorevpn.services.network.models

import com.google.gson.annotations.SerializedName

data class ConfirmEmailReq(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("email_code") val emailCode: String,
)
