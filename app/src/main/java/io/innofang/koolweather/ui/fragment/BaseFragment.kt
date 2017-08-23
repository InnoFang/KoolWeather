package io.innofang.koolweather.ui.fragment

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author: Inno Fang
 * Time: 2017/6/26 21:15
 * Description:
 */

abstract class BaseFragment : Fragment() {

    lateinit var mView: View

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun createView(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(getLayoutResId(), container, false)
        createView(savedInstanceState)
        return view
    }

    inline fun <reified T: View> find(@IdRes id: Int): T = mView.findViewById(id) as T
}