package io.innofang.koolweather

import android.app.Application
import kotlin.properties.Delegates

/**
 * Author: Inno Fang
 * Time: 2017/6/27 09:49
 * Description:
 */

class App: Application() {

    companion object {
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}