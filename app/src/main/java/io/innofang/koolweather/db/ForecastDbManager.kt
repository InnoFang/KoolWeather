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
            while (!cursor.isAfterLast) {
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

    private fun queryData(any: Any, whereClause: String, whereArgs: Array<String>): CursorWrapper {
        when (any) {
            is Province -> {
                return CursorWrapper(database!!.query(
                        ProvinceTable.NAME,
                        null,
                        whereClause,
                        whereArgs,
                        null,
                        null,
                        null
                ))
            }
            is City -> {
                return CursorWrapper(database!!.query(
                        CityTable.NAME,
                        null,
                        whereClause,
                        whereArgs,
                        null,
                        null,
                        null
                ))
            }
            else -> {
                return CursorWrapper(database!!.query(
                        CountyTable.NAME,
                        null,
                        whereClause,
                        whereArgs,
                        null,
                        null,
                        null
                ))
            }
        }
    }

    private fun queryData(any: Any): CursorWrapper {
        when (any) {
            is Province -> {
                return CursorWrapper(database!!.rawQuery("select * from " + ProvinceTable.NAME, null))
            }
            is City -> {
                return CursorWrapper(database!!.rawQuery("select * from " + CityTable.NAME, null))
            }
            else -> {
                return CursorWrapper(database!!.rawQuery("select * from " + CountyTable.NAME, null))
            }
        }
    }

}