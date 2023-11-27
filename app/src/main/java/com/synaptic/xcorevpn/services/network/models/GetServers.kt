package com.synaptic.xcorevpn.services.network.models

import com.google.gson.annotations.SerializedName

data class GetServers(
    @SerializedName("data") val dataInBase64: String
)
