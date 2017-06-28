package io.innofang.koolweather.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

/**
 * Author: Inno Fang
 * Time: 2017/6/28 22:22
 * Description:
 */

class ForecastDbManager private constructor() {

    private var database: SQLiteDatabase? = null

    companion object {

        fun getInstance(): ForecastDbManager = Inner.instance
    }

    private object Inner {

        var instance = ForecastDbManager()
    }

    init {
        database = ForecastDbHelper.instance.writableDatabase
    }


    fun addData(any: Any) {
        val values = getContentValues(any)
        when (any) {
            is Province -> database!!.insert(ProvinceTable.NAME, null, values)
            is City -> database!!.insert(CityTable.NAME, null, values)
            is County -> database!!.insert(CountyTable.NAME, null, values)
        }
    }


    private fun getContentValues(any: Any): ContentValues {
        val values = ContentValues()
        when (any) {
            is Province -> {
                with(any) {
                    values.put(ProvinceTable.PROVINCE_NAME, any.provinceName)
                    values.put(ProvinceTable.PROVINCE_CODE, any.provinceCode)
                }
            }
            is City -> {
                with(any) {
                    values.put(CityTable.CITY_NAME, any.cityName)
                    values.put(CityTable.CITY_CODE, any.cityCode)
                    values.put(CityTable.PROVINCE_ID, any.provinceId)
                }
            }
            is County -> {
                with(any) {
                    values.put(CountyTable.COUNTY_NAME, any.countyName)
                    values.put(CountyTable.WEATHER_ID, any.weatherId)
                    values.put(CountyTable.CITY_ID, any.cityId)
                }
            }
        }
        return values
    }
}