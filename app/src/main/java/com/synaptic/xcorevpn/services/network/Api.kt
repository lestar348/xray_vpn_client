package com.synaptic.xcorevpn.services.network

import com.synaptic.xcorevpn.services.network.models.ConfirmEmailReq
import com.synaptic.xcorevpn.services.network.models.GetServers
import com.synaptic.xcorevpn.services.network.models.RegUserReq
import com.synaptic.xcorevpn.services.network.models.RegUserResp
import com.synaptic.xcorevpn.services.network.models.Subscription
import com.synaptic.xcorevpn.services.network.models.Tgdata

interface Api {
    fun regUser(data: RegUserReq, responseHandler:(RegUserResp)->Unit)

    fun confirmEmail(data: ConfirmEmailReq, responseHandler:(Tgdata)->Unit)

    fun getServers(responseHandler:(GetServers)->Unit)

    fun checkStatus(responseHandler:(Subscription)->Unit)

    fun delUser(responseHandler:(Boolean)->Unit)
}