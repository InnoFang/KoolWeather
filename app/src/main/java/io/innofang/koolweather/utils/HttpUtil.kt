package io.innofang.koolweather.utils

import android.text.TextUtils
import io.innofang.koolweather.db.ForecastDbManager
import io.innofang.koolweather.db.Province
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException

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
                try {
                    val allProvinces = JSONArray(response)
                    val manager = ForecastDbManager.instance()
                    (0 until allProvinces.length())
                            .map { allProvinces.getJSONObject(it) }
                            .map {
                                Province(mutableMapOf(
                                        "provinceName" to it.getString("name"),
                                        "provinceCode" to it.getInt("id")
                                ))
                            }
                            .forEach { manager.addData(it) }
                    return true
                } catch (e: JSONException) {
                    e.printStackTrace()
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