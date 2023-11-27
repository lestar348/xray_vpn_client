package com.synaptic.xcorevpn.services.network.core

import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.synaptic.xcorevpn.MainActivity


class NetworkCore(private val host: String, private val authToken: String?) {

    private val cache = DiskBasedCache(MainActivity.context.cacheDir, 1024 * 1024)
    private val network = BasicNetwork(HurlStack())
    private val requestQueue = RequestQueue(cache, network).apply {
        start()
    }

    private val defaultHeaders = mapOf("Accept" to "application/json", "Content-Type" to "application/json")
    fun <T> post(path: String, body:  String, headers: MutableMap<String, String>? = null, clazz: Class<T>, responseHandler:(T)->Unit)  {
        var finalHeaders = defaultHeaders.toMutableMap()
        if(headers != null) { finalHeaders = (finalHeaders + headers) as MutableMap<String, String> }
        if(authToken != null) { finalHeaders["Authorization"] = authToken }

        val post = RequestPOST(url = host + path, body = body, headers = finalHeaders, clazz = clazz, listener = { response ->
            responseHandler(response)
        }, errorListener = { error ->
//            val type = object: TypeToken<Map<String, String>>(){}.type
//            val res = GsonBuilder().create().fromJson<Map<String, String>>(error.networkResponse.data, type)
            print(error)

        })
        requestQueue.add(post)
    }
}