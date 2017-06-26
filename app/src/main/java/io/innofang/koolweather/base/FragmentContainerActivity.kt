package io.innofang.koolweather.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:38
 * Description:
 */

abstract class FragmentContainerActivity: AppCompatActivity() {

    var mFragmentManager: FragmentManager? = null
    var mCurrentFragment: Fragment? = null

    protected abstract fun createFragment(): Fragment

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    @IdRes
    protected abstract fun getFragmentContainerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        mFragmentManager = supportFragmentManager
        mCurrentFragment = mFragmentManager!!.findFragmentById(getFragmentContainerId())

        if (null == mCurrentFragment) {
            mCurrentFragment = createFragment()
            mFragmentManager!!.beginTransaction()
                    .add(getFragmentContainerId(), mCurrentFragment)
                    .commit()
        }
    }
}