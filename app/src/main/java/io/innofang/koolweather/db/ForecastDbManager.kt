package io.innofang.koolweather.db

import android.content.ContentValues
import android.database.CursorWrapper
import android.database.sqlite.SQLiteDatabase

/**
 * Author: Inno Fang
 * Time: 2017/6/28 22:22
 * Description:
 */

class ForecastDbManager private constructor() {

    private var database: SQLiteDatabase? = null

    companion object {
        fun instance(): ForecastDbManager = Inner.instance
        private object Inner {
            val instance = ForecastDbManager()
        }
    }

    init {
        database = ForecastDbHelper().writableDatabase
    }


    fun addData(any: Any) {
        val values = getContentValues(any)
        when (any) {
            is Province -> database!!.insert(ProvinceTable.NAME, null, values)
            is City -> database!!.insert(CityTable.NAME, null, values)
            is County -> database!!.insert(CountyTable.NAME, null, values)
        }
    }

    fun getProvinces(): List<Province> {
        val list = ArrayList<Province>()
        val cursor = CursorWrapper(database!!.rawQuery("select * from " + ProvinceTable.NAME, null))

        try {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                list.add(Province(mutableMapOf(
                        "provinceName" to cursor.getString(cursor.getColumnIndex(ProvinceTable.PROVINCE_NAME)),
                        "provinceCode" to cursor.getInt(cursor.getColumnIndex(ProvinceTable.PROVINCE_CODE))
                )))
                cursor.moveToNext()
            }
        } finally {
            cursor.close()
        }
        return list
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


    private fun queryData(any: Any): CursorWrapper {
        when (any) {
            is Province -> return CursorWrapper(database!!.rawQuery("select * from " + ProvinceTable.NAME, null))
            is City -> return CursorWrapper(database!!.rawQuery("select * from " + CityTable.NAME, null))
            else -> return CursorWrapper(database!!.rawQuery("select * from " + CountyTable.NAME, null))
        }
    }

}