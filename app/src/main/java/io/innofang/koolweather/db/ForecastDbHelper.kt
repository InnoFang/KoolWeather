package io.innofang.koolweather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.innofang.koolweather.App
import io.innofang.koolweather.utils.SQL

/**
 * Author: Inno Fang
 * Time: 2017/6/27 09:51
 * Description:
 */

class ForecastDbHelper(ctx: Context = App.instance)
    : SQLiteOpenHelper(ctx, ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
//        val instance = ForecastDbHelper()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL.createTable(ProvinceTable.NAME)
                .addIntegerColsWithPrimaryKey(ProvinceTable.ID, true)
                .addTextCols(ProvinceTable.PROVINCE_NAME)
                .addIntegerCols(ProvinceTable.PROVINCE_CODE)
                .create())

        db.execSQL(SQL.createTable(CityTable.NAME)
                .addIntegerColsWithPrimaryKey(CityTable.ID, true)
                .addTextCols(CityTable.CITY_NAME)
                .addIntegerCols(CityTable.CITY_CODE)
                .addIntegerCols(CityTable.PROVINCE_ID)
                .create())

        db.execSQL(SQL.createTable(CountyTable.NAME)
                .addIntegerColsWithPrimaryKey(CountyTable.ID, true)
                .addTextCols(CountyTable.COUNTY_NAME)
                .addTextCols(CountyTable.WEATHER_ID)
                .addIntegerCols(CountyTable.CITY_ID)
                .create())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}