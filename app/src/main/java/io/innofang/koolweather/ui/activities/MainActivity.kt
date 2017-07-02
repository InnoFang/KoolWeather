package io.innofang.koolweather.ui.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import io.innofang.koolweather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        prefs.getString(WeatherActivity.PREFS_WEATHER, null)?.let {
            startActivity(Intent(this@MainActivity, WeatherActivity::class.java))
            finish()
        }
    }
}
