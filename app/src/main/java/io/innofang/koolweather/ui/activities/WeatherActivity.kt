package io.innofang.koolweather.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import io.innofang.koolweather.R
import io.innofang.koolweather.bean.Weather
import io.innofang.koolweather.constant.Api
import io.innofang.koolweather.service.AutoUpdateService
import io.innofang.koolweather.utils.HttpUtil
import io.innofang.koolweather.utils.find
import io.innofang.koolweather.utils.toast
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class WeatherActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_WEATHER_ID = "weather_id"
        const val PREFS_WEATHER = "weather"
        const val PREFS_BING_PIC = "bing_pic"
        fun start(context: Context, weatherId: String) {
            Intent(context, WeatherActivity::class.java).apply {
                putExtra(EXTRA_WEATHER_ID, weatherId)
            }.let {
                context.startActivity(it)
            }
        }
    }

    private val weatherLayout by lazy { find<ScrollView>(R.id.weather_scroll_view) }
    private val titleCityTextView by lazy { find<TextView>(R.id.title_city_text_view) }
    private val titleUpdateTimeTextView by lazy { find<TextView>(R.id.title_update_time_text_view) }
    private val degreeTextView by lazy { find<TextView>(R.id.now_degree_text_view) }
    private val weatherInfoTextView by lazy { find<TextView>(R.id.now_weather_info_text_view) }
    private val forecastLayout by lazy { find<LinearLayout>(R.id.forecast_layout) }
    private val aqiTextView by lazy { find<TextView>(R.id.aqi_text_view) }
    private val pm25TextView by lazy { find<TextView>(R.id.pm25_text_view) }
    private val comforTextView by lazy { find<TextView>(R.id.comfort_text_view) }
    private val carWashTextView by lazy { find<TextView>(R.id.car_wash_text_view) }
    private val sportTextView by lazy { find<TextView>(R.id.sport_text_view) }
    private val bingPicImageView by lazy { find<ImageView>(R.id.bing_pic_image_view) }
    private val navButton by lazy { find<Button>(R.id.nav_button) }
    val drawerLayout by lazy { find<DrawerLayout>(R.id.drawer_layout) }
    val swipeRefresh by lazy { find<SwipeRefreshLayout>(R.id.swipe_refresh_layout) }

    var weatherId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        setContentView(R.layout.activity_weather)

        val prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity@ this)
        val weatherString = prefs.getString(PREFS_WEATHER, null)


        weatherString
                ?.let {
                    // 有缓存时直接解析天气数据
                    val weather = HttpUtil.handleWeatherResponse(weatherString)
                    weatherId = weather.HeWeather!![0].basic!!.id
                    showWeatherInfo(weather)
                }
                ?: let {
            // 无缓存时去服务器查询天气
            weatherId = intent.getStringExtra(EXTRA_WEATHER_ID)
            weatherLayout.visibility = View.VISIBLE
            requestWeatherId(weatherId!!)
        }

        val bingPic = prefs.getString(PREFS_BING_PIC, null)
        bingPic?.let { Glide.with(this@WeatherActivity).load(bingPic).into(bingPicImageView) }
                ?: let { loadingBingPic() }

        navButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener { weatherId?.let { requestWeatherId(it) } }
    }

    private fun loadingBingPic() {
        HttpUtil.sendOkHttpRequest(Api.URL_BING_PIC, object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val bingPic = response!!.body()!!.string()
                val editor = PreferenceManager
                        .getDefaultSharedPreferences(this@WeatherActivity).edit()
                editor.putString(PREFS_BING_PIC, bingPic)
                editor.apply()
                runOnUiThread {
                    Glide.with(this@WeatherActivity).load(bingPic).into(bingPicImageView)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("tag", "loading bing picture ", e)
            }

        })
    }

    /* 根据天气 Id 请求城市天气信息 */
    fun requestWeatherId(id: String) {

        HttpUtil.sendOkHttpRequest(Api.URL_WEATHER(id), object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response!!.body()!!.string()
                val weather = HttpUtil.handleWeatherResponse(responseText)
                runOnUiThread {

                    if ("ok" == weather.HeWeather!![0].status!!) {
                        val editor = PreferenceManager
                                .getDefaultSharedPreferences(this@WeatherActivity).edit()
                        editor.putString(PREFS_WEATHER, responseText)
                        editor.apply()
                        weatherId = id
                        showWeatherInfo(weather)
                    } else {
                        toast("获取天气信息失败")
                    }
                    swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("tag", "loading weather ", e)
                runOnUiThread {
                    toast("获取天气信息失败")
                    swipeRefresh.isRefreshing = false
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
            maxTextView.text = "${it.tmp!!.max}℃"
            minTextView.text = "${it.tmp!!.min}℃"
            forecastLayout.addView(view)
        }

        heWeather.aqi?.let {
            aqiTextView.text = heWeather.aqi!!.city!!.aqi
            pm25TextView.text = heWeather.aqi!!.city!!.pm25
        }

        comforTextView.text = "舒适度：${heWeather.suggestion!!.comf!!.txt}"
        carWashTextView.text = "汽车指数：${heWeather.suggestion!!.cw!!.txt}"
        sportTextView.text = "舒适度：${heWeather.suggestion!!.sport!!.txt}"

        weatherLayout.visibility = View.VISIBLE

        startService(Intent(this@WeatherActivity, AutoUpdateService::class.java))
    }
}
