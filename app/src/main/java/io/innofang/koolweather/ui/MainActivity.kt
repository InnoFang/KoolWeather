package io.innofang.koolweather.ui

import android.support.v4.app.Fragment
import io.innofang.koolweather.R
import io.innofang.koolweather.base.FragmentContainerActivity
import io.innofang.koolweather.ui.fragment.ChooseAreaFragment

class MainActivity : FragmentContainerActivity() {
    override fun createFragment(): Fragment = ChooseAreaFragment()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getFragmentContainerId(): Int = R.id.fragment_container

}
/*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
*/
