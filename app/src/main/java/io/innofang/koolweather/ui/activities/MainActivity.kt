package io.innofang.koolweather.ui.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import io.innofang.koolweather.R
import io.innofang.koolweather.ui.fragment.ChooseAreaFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(supportFragmentManager) {
            beginTransaction()
                    .add(R.id.fragment_container, ChooseAreaFragment())
                    .commit()
        }

        PreferenceManager.getDefaultSharedPreferences(this@MainActivity).let {
            it.getString(WeatherActivity.PREFS_WEATHER, null)?.let {
                startActivity(Intent(this@MainActivity, WeatherActivity::class.java))
                finish()
            }
        }

    }
}
