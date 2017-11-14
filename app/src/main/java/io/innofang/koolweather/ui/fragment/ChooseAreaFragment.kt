package io.innofang.koolweather.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.innofang.koolweather.R
import io.innofang.koolweather.constant.Api
import io.innofang.koolweather.db.City
import io.innofang.koolweather.db.County
import io.innofang.koolweather.db.ForecastDbManager
import io.innofang.koolweather.db.Province
import io.innofang.koolweather.ui.activities.MainActivity
import io.innofang.koolweather.ui.activities.WeatherActivity
import io.innofang.koolweather.ui.adapters.ChooseAreaAdapter
import io.innofang.koolweather.utils.HttpUtil
import io.innofang.koolweather.utils.event.OnRecyclerItemListener
import io.innofang.koolweather.utils.toast
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:57
 * Description:
 */

class ChooseAreaFragment : Fragment() {

    companion object {
        val LEVEL_PROVINCE = 0
        val LEVEL_CITY = 1
        val LEVEL_COUNTY = 2
        val PROVINCE = "province"
        val CITY = "city"
        val COUNTY = "county"
    }

    lateinit var mView: View

    inline fun <reified T: View> find(@IdRes id: Int): T = mView.findViewById(id) as T

    private val titleTextView by lazy { find<TextView>(R.id.title_text_view) }
    private val backImageView by lazy { find<AppCompatImageView>(R.id.back_image_view) }
    private val weatherRecyclerView by lazy { find<RecyclerView>(R.id.weather_recycler_view) }

    private val dataList = ArrayList<String>()
    private val mAdapter by lazy { ChooseAreaAdapter(dataList) }
    private val progressDialog by lazy {
        ProgressDialog(activity).apply {
            setMessage("正在加载中...")
            setCanceledOnTouchOutside(false)
        }
    }

    /* 省列表 */
    private var provinceList: List<Province>? = null

    /* 市列表 */
    private var cityList: List<City>? = null

    /* 县列表 */
    private var countyList: List<County>? = null

    /* 选中的省份 */
    private var selectedProvince: Province? = null

    /* 选中的城市 */
    private var selectedCity: City? = null

    /* 当前选中的级别 */
    private var currentLevel: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater?.inflate(R.layout.fragment_choose_area, container, false) as View
        return mView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(weatherRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addOnItemTouchListener(object : OnRecyclerItemListener(weatherRecyclerView) {
                override fun onItemClick(vh: RecyclerView.ViewHolder) {
                    if (vh is ChooseAreaAdapter.ViewHolder) {

                        if (currentLevel == LEVEL_PROVINCE) {
                            selectedProvince = provinceList!![vh.adapterPosition]
                            queryCities()
                        } else if (currentLevel == LEVEL_CITY) {
                            selectedCity = cityList!![vh.adapterPosition]
                            queryCounties()
                        } else if (currentLevel == LEVEL_COUNTY) {
                            val weatherId = countyList!![vh.adapterPosition].weatherId
                            if (activity is MainActivity) {
                                WeatherActivity.start(activity, weatherId)
                                activity.finish()
                            } else if (activity is WeatherActivity) {
                                val act: WeatherActivity = activity as WeatherActivity
                                act.drawerLayout.closeDrawers()
                                act.swipeRefresh.isRefreshing = true
                                act.requestWeatherId(weatherId)
                            }
                        }
                    }
                }
            })
        }

        with(backImageView) {
            setOnClickListener {
                when (currentLevel) {
                    LEVEL_CITY -> queryProvinces()
                    LEVEL_COUNTY -> queryCities()
                }
            }
            queryProvinces()
        }
    }

    private fun queryProvinces() {
        titleTextView.text = "中国"
        backImageView.visibility = View.GONE
        provinceList = ForecastDbManager.instance().getProvinces()
        if (provinceList!!.isNotEmpty()) {
            dataList.clear()
            provinceList!!.forEach { dataList.add(it.provinceName) }
            mAdapter.notifyDataSetChanged()
            currentLevel = LEVEL_PROVINCE
        } else {
            queryFromServer(Api.URL_PROVINCE, PROVINCE)
        }
    }

    private fun queryCities() {
        titleTextView.text = selectedProvince!!.provinceName
        backImageView.visibility = View.VISIBLE
        cityList = ForecastDbManager.instance().getCities(selectedProvince!!)
        if (cityList!!.isNotEmpty()) {
            dataList.clear()
            cityList!!.forEach { dataList.add(it.cityName) }
            mAdapter.notifyDataSetChanged()
            currentLevel = LEVEL_CITY
        } else {
            val provinceCode = selectedProvince!!.provinceCode.toString()
            queryFromServer(Api.URL_CITY(provinceCode), CITY)
        }
    }

    private fun queryCounties() {
        titleTextView.text = selectedCity!!.cityName
        backImageView.visibility = View.VISIBLE
        countyList = ForecastDbManager.instance().getCounties(selectedCity!!)
        if (countyList!!.isNotEmpty()) {
            dataList.clear()
            countyList!!.forEach { dataList.add(it.countyName) }
            mAdapter.notifyDataSetChanged()
            currentLevel = LEVEL_COUNTY
        } else {
            val provinceCode = selectedProvince!!.provinceCode.toString()
            val cityCode = selectedCity!!.cityCode.toString()
            queryFromServer(Api.URL_COUNTY(provinceCode, cityCode), COUNTY)
        }
    }

    private fun queryFromServer(url: String, type: String) {
        showProgressDialog()
        HttpUtil.sendOkHttpRequest(url, object : okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response!!.body()!!.string()
                var result = false
                when (type) {
                    PROVINCE -> result = HttpUtil.handleProvinceResponse(responseText)
                    CITY -> result = HttpUtil.handleCityResponse(responseText, selectedProvince!!.provinceCode)
                    COUNTY -> result = HttpUtil.handleCountyResponse(responseText, selectedCity!!.cityCode)
                }

                if (result) {
                    activity.runOnUiThread {
                        closeProgressDialog()
                        when (type) {
                            PROVINCE -> queryProvinces()
                            CITY -> queryCities()
                            COUNTY -> queryCounties()
                        }
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                activity.runOnUiThread {
                    closeProgressDialog()
                    toast("加载失败")
                }
            }

        })
    }

    private fun showProgressDialog() {
        progressDialog.show()
    }

    private fun closeProgressDialog() {
        progressDialog.dismiss()
    }
}