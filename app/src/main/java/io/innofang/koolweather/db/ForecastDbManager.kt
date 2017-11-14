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

        cursor.use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                list.add(Province(mutableMapOf(
                        "provinceName" to cursor.getString(cursor.getColumnIndex(ProvinceTable.PROVINCE_NAME)),
                        "provinceCode" to cursor.getInt(cursor.getColumnIndex(ProvinceTable.PROVINCE_CODE))
                )))
                cursor.moveToNext()
            }
        }
        return list
    }

    fun getCities(province: Province): List<City> {
        val list = ArrayList<City>()
        val cursor = CursorWrapper(database!!.query(
                CityTable.NAME,
                null,
                "${CityTable.PROVINCE_ID} = ?",
                Array<String>(1) { province.provinceCode.toString() },
                null,
                null,
                null
        ))

        cursor.use { cursor ->
            cursor.moveToNext()
            while (!cursor.isAfterLast) {
                list.add(City(mutableMapOf(
                        "cityName" to cursor.getString(cursor.getColumnIndex(CityTable.CITY_NAME)),
                        "cityCode" to cursor.getInt(cursor.getColumnIndex(CityTable.CITY_CODE)),
                        "provinceId" to cursor.getInt(cursor.getColumnIndex(CityTable.PROVINCE_ID))
                )))
                cursor.moveToNext()
            }
        }
        return list
    }

    fun getCounties(city: City): List<County> {
        val list = ArrayList<County>()
        val cursor = CursorWrapper(database!!.query(
                CountyTable.NAME,
                null,
                "${CountyTable.CITY_ID} = ?",
                Array<String>(1) { city.cityCode.toString() },
                null,
                null,
                null
        ))

        cursor.use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                list.add(County(mutableMapOf(
                        "countyName" to cursor.getString(cursor.getColumnIndex(CountyTable.COUNTY_NAME)),
                        "weatherId" to cursor.getString(cursor.getColumnIndex(CountyTable.WEATHER_ID)),
                        "cityId" to cursor.getInt(cursor.getColumnIndex(CountyTable.CITY_ID))
                )))
                cursor.moveToNext()
            }
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

}