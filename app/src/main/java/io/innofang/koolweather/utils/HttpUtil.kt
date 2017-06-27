package io.innofang.koolweather.utils

import android.text.TextUtils
import io.innofang.koolweather.db.Province
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:12
 * Description:
 */

class HttpUtil {

    companion object {
        fun sendOkHttpRequest(url: String, callback: okhttp3.Callback) {
            OkHttpClient()
                    .newCall(Request.Builder().url(url).build())
                    .enqueue(callback)
        }

        fun handleProvinceResponse(response: String): Boolean {
            if (!TextUtils.isEmpty(response)) {
                val allProvinces = JSONArray(response)
                for (i in 0 until allProvinces.length()) {
                    val provinceObject = allProvinces.getJSONObject(i)
                    val province = Province(mutableMapOf(
                            "provinceCode" to provinceObject.getString("id"),
                            "provinceName" to provinceObject.getString("name")
                    ))
                }
            }
            return false
        }

        fun handleCityResponse(response: String): Boolean {

            return false
        }

        fun handleCountyResponse(response: String): Boolean {

            return false
        }
    }

}