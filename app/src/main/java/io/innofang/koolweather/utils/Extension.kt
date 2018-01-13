package io.innofang.koolweather.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * Author: Inno Fang
 * Time: 2017/8/10 12:02
 * Description:
 */

inline fun <reified T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resId, duration).show()

fun Context.toast(text: String = "", time: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, time).show()

fun Fragment.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(activity, resId, duration).show()

fun Fragment.toast(text: String = "", time: Int = Toast.LENGTH_SHORT) = Toast.makeText(activity, text, time).show()