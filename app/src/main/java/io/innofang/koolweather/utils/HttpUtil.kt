package io.innofang.koolweather.utils

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:12
 * Description:
 */

class HttpUtil {

    companion object {
        fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
            OkHttpClient()
                    .newCall(Request.Builder().url(address).build())
                    .enqueue(callback)
        }
    }

}