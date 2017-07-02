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
import io.innofang.koolweather.db.City
import io.innofang.koolweather.db.County
import io.innofang.koolweather.db.ForecastDbManager
import io.innofang.koolweather.db.Province
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

    private var progressDialog: ProgressDialog? = null
    private val dataList = ArrayList<String>()
    private val adapter: ChooseAreaAdapter
            by lazy { ChooseAreaAdapter(dataList) }

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

    override fun getLayoutResId(): Int = R.layout.fragment_choose_area

    override fun createView(savedInstanceState: Bundle?) {
        weatherRecyclerView.layoutManager = LinearLayoutManager(context)
        weatherRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherRecyclerView.addOnItemTouchListener(object : OnRecyclerItemListener(weatherRecyclerView) {
            override fun onItemClick(vh: RecyclerView.ViewHolder) {
                if (vh is ChooseAreaAdapter.ViewHolder) {
//                    activity.toast(provinceList!![vh.adapterPosition].provinceName)
                    if (currentLevel == LEVEL_PROVINCE) {
                        selectedProvince = provinceList!![vh.adapterPosition]
                        queryCities()
                    } else if (currentLevel == LEVEL_CITY) {
                        selectedCity = cityList!![vh.adapterPosition]
                        queryCounties()
                    }
                }
            }
        })

        backImageView.setOnClickListener {
            when (currentLevel) {
                LEVEL_CITY -> {
                    queryProvinces()
                }
                LEVEL_COUNTY -> {
                    queryCities()
                }
            }
        }
        queryProvinces()
    }


    private fun queryProvinces() {
        titleTextView.text = "中国"
        backImageView.visibility = View.GONE
        provinceList = ForecastDbManager.instance().getProvinces()
        if (provinceList!!.isNotEmpty()) {
            dataList.clear()
            provinceList!!.forEach { dataList.add(it.provinceName) }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_PROVINCE
        } else {
            queryFromServer(Cons.URL_PROVINCE, PROVINCE)
        }
    }

    private fun queryCities() {
        titleTextView.text = selectedProvince!!.provinceName
        backImageView.visibility = View.VISIBLE
        cityList = ForecastDbManager.instance().getCities(selectedProvince!!)
        if (cityList!!.isNotEmpty()) {
            dataList.clear()
            cityList!!.forEach { dataList.add(it.cityName) }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_CITY
        } else {
            val provinceCode = selectedProvince!!.provinceCode.toString()
            queryFromServer(Cons.URL_CITY(provinceCode), CITY)
        }
    }

    private fun queryCounties() {
        titleTextView.text = selectedCity!!.cityName
        backImageView.visibility = View.VISIBLE
        countyList = ForecastDbManager.instance().getCounties(selectedCity!!)
        if (countyList!!.isNotEmpty()) {
            dataList.clear()
            countyList!!.forEach { dataList.add(it.countyName) }
            adapter.notifyDataSetChanged()
            currentLevel = LEVEL_COUNTY
        } else {
            val provinceCode = selectedProvince!!.provinceCode.toString()
            val cityCode = selectedCity!!.cityCode.toString()
            queryFromServer(Cons.URL_COUNTY(provinceCode, cityCode), COUNTY)
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
                    activity.toast("加载失败")
                }
            }

        })
    }

    private fun showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage("正在加载中...")
            progressDialog!!.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }
}