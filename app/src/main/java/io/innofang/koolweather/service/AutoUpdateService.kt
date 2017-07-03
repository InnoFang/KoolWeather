package io.innofang.koolweather.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Log
import io.innofang.koolweather.constant.Api
import io.innofang.koolweather.ui.activities.WeatherActivity
import io.innofang.koolweather.utils.HttpUtil
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class AutoUpdateService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateWeather()
        updateBingPic()
        val manager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // 8 小时毫秒数
        val anHour = 8 * 60 * 60 * 1000
        val triggerAtTime = SystemClock.elapsedRealtime() + anHour
        val i = Intent(this@AutoUpdateService, AutoUpdateService::class.java)
        val pi = PendingIntent.getService(this@AutoUpdateService, 0, i, 0)
        manager.cancel(pi)
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 更新天气信息
     */
    private fun updateWeather() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this@AutoUpdateService)
        val weatherString = prefs.getString(WeatherActivity.PREFS_WEATHER, null)
        weatherString?.let {
            // 有缓存时直接解析天气数据
            val weather = HttpUtil.handleWeatherResponse(weatherString)
            val weatherId = weather.HeWeather!![0].basic!!.id

            HttpUtil.sendOkHttpRequest(Api.URL_WEATHER(weatherId!!), object : okhttp3.Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val responseText = response!!.body()!!.string()
                    val weather = HttpUtil.handleWeatherResponse(responseText)

                    if ("ok" == weather.HeWeather!![0].status!!) {
                        val editor = PreferenceManager
                                .getDefaultSharedPreferences(this@AutoUpdateService).edit()
                        editor.putString(WeatherActivity.PREFS_WEATHER, responseText)
                        editor.apply()
                    }

                }

                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("tag", "loading weather ", e)
                }

            })
        }
    }

    /**
     * 更新必应每日一图
     */
    private fun updateBingPic() {
        HttpUtil.sendOkHttpRequest(Api.URL_BING_PIC, object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val bingPic = response!!.body()!!.string()
                val editor = PreferenceManager
                        .getDefaultSharedPreferences(this@AutoUpdateService).edit()
                editor.putString(WeatherActivity.PREFS_BING_PIC, bingPic)
                editor.apply()
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("tag", "loading bing picture ", e)
            }

        })
    }
}
