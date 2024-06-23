package com.huiiro.ncn

import android.util.Log
import com.huiiro.ncn.http.Api
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Test {

    fun testOkhttp() {
        val client = OkHttpClient()

        val url = Api.CROW_INFO

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d("Test", "onResponse: ")
                Log.d("Test", response.body!!.string())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("Test", "onFailure: ")
            }
        })

    }
}