package com.synaptic.xcorevpn.services.network.core

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class RequestPOST<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val body:  String,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.POST, url, errorListener) {

    private val gson = Gson()

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {

            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }

// request body goes here
//    val jsonBody = JSONObject()
//    jsonBody.put("attribute1", "value1")
//    val requestBody = jsonBody.toString()
    override fun getBody(): ByteArray? = body.toByteArray(charset("utf-8"))

    override fun getHeaders(): Map<String, String>? = headers

    override fun deliverResponse(response: T) = listener.onResponse(response)
}