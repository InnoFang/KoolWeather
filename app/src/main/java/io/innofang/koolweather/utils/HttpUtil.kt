package io.innofang.koolweather.utils

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import io.innofang.koolweather.bean.Weather
import io.innofang.koolweather.db.City
import io.innofang.koolweather.db.County
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
            Log.i("tag", "response : $response")
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

        fun handleCityResponse(response: String, provinceId: Int): Boolean {
            if (!TextUtils.isEmpty(response)) {
                try {
                    val allCities = JSONArray(response)
                    val manager = ForecastDbManager.instance()
                    for (i in 0 until allCities.length()) {
                        val cityObject = allCities.getJSONObject(i)
                        val city = City(mutableMapOf(
                                "cityName" to cityObject.getString("name"),
                                "cityCode" to cityObject.getInt("id"),
                                "provinceId" to provinceId
                        ))
                        manager.addData(city)
                    }
                    return true
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return false
        }

        fun handleCountyResponse(response: String, cityId: Int): Boolean {
            if (!TextUtils.isEmpty(response)) {
                try {
                    val allCounties = JSONArray(response)
                    val manager = ForecastDbManager.instance()
                    (0 until allCounties.length())
                            .map { allCounties.getJSONObject(it) }
                            .map {
                                County(mutableMapOf(
                                        "countyName" to it.getString("name"),
                                        "weatherId" to it.getString("weather_id"),
                                        "cityId" to cityId
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

        fun handleWeatherResponse(response: String): Weather {
            return Gson().fromJson(response, Weather::class.java)
        }

    }

}