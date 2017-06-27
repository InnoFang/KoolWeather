package io.innofang.koolweather.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.innofang.koolweather.R
import io.innofang.koolweather.base.BaseFragment
import io.innofang.koolweather.constant.Cons
import io.innofang.koolweather.ui.adapter.ChooseAreaAdapter
import io.innofang.koolweather.utils.HttpUtil
import io.innofang.koolweather.utils.event.OnRecyclerItemListener
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:57
 * Description:
 */

class ChooseAreaFragment : BaseFragment() {

    companion object {
        val LEVEL_PROVINCE = 0
        val LEVEL_CITY = 1
        val LEVEL_COUNTY = 2
        val PROVINCE = "province"
        val CITY = "city"
        val COUNTY = "county"
    }

    private val titleTextView: TextView
            by lazy { find(R.id.title_text_view) as TextView }
    private val backImageView: ImageView
            by lazy { find(R.id.back_image_view) as AppCompatImageView }
    private val weatherRecyclerView: RecyclerView
            by lazy { find(R.id.weather_recycler_view) as RecyclerView }

    private val dataList = ArrayList<String>()
    private val adapter: ChooseAreaAdapter
            by lazy { ChooseAreaAdapter(dataList) }
    private var progressDialog: ProgressDialog? = null

    private var currentLevel: Int = 0
    override fun getLayoutResId(): Int = R.layout.fragment_choose_area

    override fun createView(savedInstanceState: Bundle) {

        weatherRecyclerView.layoutManager = LinearLayoutManager(activity)
        weatherRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val action = OnRecyclerItemListener(weatherRecyclerView)
        action.onItemClick = {
            if (it is ChooseAreaAdapter.ViewHolder) {
                activity.toast(it.itemTextView.text.toString())
            }
        }
        weatherRecyclerView.addOnItemTouchListener(action)

        backImageView.setOnClickListener {
            when (currentLevel) {
                LEVEL_CITY -> {
                    queryCity()
                }
                LEVEL_COUNTY -> {
                    queryCounty()
                }
            }
        }
        queryProvinces()
    }


    private fun queryProvinces() {
        titleTextView.text = "中国"
        backImageView.visibility = View.GONE

        queryFromServer(Cons.URL, PROVINCE)
    }

    private fun queryCity() {

    }

    private fun queryCounty() {

    }

    private fun queryFromServer(url: String, type: String) {
        showProgressDialog()
        HttpUtil.sendOkHttpRequest(url, object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response!!.body().toString()
                var result = false
                when (type) {
                    PROVINCE -> result = HttpUtil.handleProvinceResponse(responseText)
                    CITY -> result = HttpUtil.handleCityResponse(responseText)
                    COUNTY -> result = HttpUtil.handleCountyResponse(responseText)
                }

                if (result) {
                    activity.runOnUiThread {
                        closeProgressDialog()
                        when (type) {
                            PROVINCE -> queryProvinces()
                            CITY -> queryCity()
                            COUNTY -> queryCounty()
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                activity.runOnUiThread {
                    closeProgressDialog()
                    activity.toast("加载失败")
                }
            }

        })
    }

    private fun showProgressDialog() {
        progressDialog?.show() ?: with(ProgressDialog(activity)) {
            setMessage("正在加载中...")
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}