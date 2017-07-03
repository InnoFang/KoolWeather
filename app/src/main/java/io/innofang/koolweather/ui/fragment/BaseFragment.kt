package io.innofang.koolweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Author: Inno Fang
 * Time: 2017/6/26 21:15
 * Description:
 */

abstract class BaseFragment : Fragment() {

    internal var view: View? = null

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun createView(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(getLayoutResId(), container, false)
        createView(savedInstanceState)
        return view
    }

    protected fun find(@IdRes id: Int): View = view!!.findViewById(id)

    fun Context.toast(text: String = "", time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, time).show()
    }
}