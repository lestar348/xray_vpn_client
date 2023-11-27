package com.synaptic.xcorevpn.services.network.implemantation

import com.google.gson.Gson
import com.synaptic.xcorevpn.services.network.Api
import com.synaptic.xcorevpn.services.network.ApiList
import com.synaptic.xcorevpn.services.network.core.NetworkCore
import com.synaptic.xcorevpn.services.network.models.CheckStatusResp
import com.synaptic.xcorevpn.services.network.models.ConfirmEmail
import com.synaptic.xcorevpn.services.network.models.ConfirmEmailReq
import com.synaptic.xcorevpn.services.network.models.GetServers
import com.synaptic.xcorevpn.services.network.models.RegUserReq
import com.synaptic.xcorevpn.services.network.models.RegUserResp
import com.synaptic.xcorevpn.services.network.models.Subscription
import com.synaptic.xcorevpn.services.network.models.Tgdata
import com.synaptic.xcorevpn.util.DeviceUtils

class ApiImpl: Api {

    private val core = NetworkCore(host = ApiList.domenAPI, authToken = "Bearer 8Yhusjq34v1WRjuzDCB7FszKfJ7ThQwjAlVYMt1gG9ab6uXP4quZB2HxzdQPCmBK")
    private val gson = Gson()

    override fun regUser(data: RegUserReq, responseHandler: (RegUserResp) -> Unit) {
        core.post(path = ApiList.regUser, body = gson.toJson(data), clazz = RegUserResp::class.java, responseHandler = responseHandler)
    }

    override fun confirmEmail(data: ConfirmEmailReq, responseHandler: (Tgdata) -> Unit) {
        core.post(path = ApiList.confirmEmail, body = gson.toJson(data), clazz = ConfirmEmail::class.java, responseHandler = { response ->
            responseHandler(response.tgdata!!)
        })
    }

    override fun getServers(responseHandler: (GetServers) -> Unit) {
        val data = mapOf("device_id" to DeviceUtils.deviceID)
        core.post(path = ApiList.getServers, body = gson.toJson(data), clazz = GetServers::class.java, responseHandler = responseHandler)
    }

    override fun checkStatus(responseHandler: (Subscription) -> Unit) {
        val data = mapOf("device_id" to DeviceUtils.deviceID)
        core.post(path = ApiList.regUser, body = gson.toJson(data), clazz = CheckStatusResp::class.java, responseHandler = { response ->
            responseHandler(response.subscription)
        })
    }

    override fun delUser(responseHandler: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }
}