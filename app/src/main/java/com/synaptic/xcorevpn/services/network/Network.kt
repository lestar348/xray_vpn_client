package com.synaptic.xcorevpn.services.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class Network {

    private suspend fun get(url: String): String{
        val result = withContext(Dispatchers.IO){
            val url = URL(url)
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            String(conn.inputStream.readBytes())
        }
        return  result
    }

    fun httpGet(url: String, onSuccess: (String)->Unit ){
        GlobalScope.launch(Dispatchers.Main) {
            val result = get(url)
            onSuccess(result)
        }
    }

}