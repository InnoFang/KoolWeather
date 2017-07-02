package io.innofang.koolweather.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import io.innofang.koolweather.R
import io.innofang.koolweather.bean.Weather
import io.innofang.koolweather.constant.Cons
import io.innofang.koolweather.utils.HttpUtil
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class WeatherActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_WEATHER_ID = "weather_id"
        val PREFS_WEATHER = "weather"
        fun start(context: Context, weatherId: String) {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra(EXTRA_WEATHER_ID, weatherId)
            context.startActivity(intent)
        }
    }


    private val weatherLayout: ScrollView by lazy { findViewById(R.id.weather_scroll_view) as ScrollView }
    private val titleCityTextView: TextView by lazy { findViewById(R.id.title_city_text_view) as TextView }
    private val titleUpdateTimeTextView: TextView by lazy { findViewById(R.id.title_update_time_text_view) as TextView }
    private val degreeTextView: TextView by lazy { findViewById(R.id.now_degree_text_view) as TextView }
    private val weatherInfoTextView: TextView by lazy { findViewById(R.id.now_weather_info_text_view) as TextView }
    private val forecastLayout: LinearLayout by lazy { findViewById(R.id.forecast_layout) as LinearLayout }
    private val aqiTextView: TextView by lazy { findViewById(R.id.aqi_text_view) as TextView }
    private val pm25TextView: TextView by lazy { findViewById(R.id.pm25_text_view) as TextView }
    private val comforTextView: TextView by lazy { findViewById(R.id.comfort_text_view) as TextView }
    private val carwashTextView: TextView by lazy { findViewById(R.id.car_wash_text_view) as TextView }
    private val sportTextView: TextView by lazy { findViewById(R.id.sport_text_view) as TextView }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity@ this)
        val weatherString = prefs.getString(PREFS_WEATHER, null)
        weatherString?.let { showWeatherInfo(HttpUtil.handleWeatherResponse(weatherString)) }
                ?: let {
            val weatherId = intent.getStringExtra(EXTRA_WEATHER_ID)
            weatherLayout.visibility = View.VISIBLE
            requestWeatherId(weatherId)
        }


    }

    /* 根据天气 Id 请求城市天气信息 */
    fun requestWeatherId(weatherId: String) {
        HttpUtil.sendOkHttpRequest(Cons.URL_WEATHER(weatherId), object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response!!.body()!!.string()
                val weather = HttpUtil.handleWeatherResponse(responseText)
                runOnUiThread {

                    if ("ok" == weather.HeWeather!![0].status!!) {
                        val editor = PreferenceManager
                                .getDefaultSharedPreferences(this@WeatherActivity).edit()
                        editor.putString(PREFS_WEATHER, responseText)
                        editor.apply()
                        showWeatherInfo(weather)
                    } else {
                        toast("获取天气信息失败")
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                e!!.printStackTrace()
                runOnUiThread {
                    toast("获取天气信息失败")
                }
            }

        })
    }

    private fun showWeatherInfo(weather: Weather) {
        val heWeather = weather.HeWeather!![0]
        val basic = heWeather.basic!!
        val now = heWeather.now!!

        titleCityTextView.text = basic.city
        titleUpdateTimeTextView.text = basic.update!!.loc!!.split(" ")[1]
        degreeTextView.text = "${now.tmp}℃"
        weatherInfoTextView.text = now.cond!!.txt

        forecastLayout.removeAllViews()
        heWeather.daily_forecast!!.forEach {
            val view = LayoutInflater.from(this@WeatherActivity)
                    .inflate(R.layout.item_forecast, forecastLayout, false)
            val dateTextView = view.findViewById(R.id.date_text_view) as TextView
            val infoTextView = view.findViewById(R.id.info_text_view) as TextView
            val maxTextView = view.findViewById(R.id.max_text_view) as TextView
            val minTextView = view.findViewById(R.id.min_text_view) as TextView

            dateTextView.text = it.date
            infoTextView.text = it.cond!!.txt_d
            maxTextView.text = it.tmp!!.max
            minTextView.text = it.tmp!!.min
            forecastLayout.addView(view)
        }

        heWeather.aqi?.let {
            aqiTextView.text = heWeather.aqi!!.city!!.aqi
            pm25TextView.text = heWeather.aqi!!.city!!.pm25
        }

        comforTextView.text = "舒适度：${heWeather.suggestion!!.comf!!.txt}"
        carwashTextView.text = "汽车指数：${heWeather.suggestion!!.cw!!.txt}"
        sportTextView.text = "舒适度：${heWeather.suggestion!!.sport!!.txt}"

        weatherLayout.visibility = View.VISIBLE
    }

    fun Context.toast(text: String = "", time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, time).show()
    }
}
