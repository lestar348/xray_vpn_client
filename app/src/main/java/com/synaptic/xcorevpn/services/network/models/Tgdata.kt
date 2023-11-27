package com.synaptic.xcorevpn.services.network.models

import com.google.gson.annotations.SerializedName

data class Tgdata (
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("tg_id") val tgId: String?,
    val username: String?,
    val user: String?) {
    val isEmpty: Boolean
        get() = firstName == null && lastName == null && tgId == null && username == null && user == null
}
