package io.innofang.koolweather.db

/**
 * Author: Inno Fang
 * Time: 2017/6/27 09:44
 * Description:
 */

object ProvinceTable {
    val NAME = "province"
    val ID = "_id"
    val PROVINCE_NAME = "provinceName"
    val PROVINCE_CODE = "provinceCode"
}

object CityTable {
    val NAME = "city"
    val ID = "_id"
    val CITY_NAME = "cityName"
    val CITY_CODE = "cityCode"
    val PROVINCE_ID = "provinceId"
}

object CountyTable {
    val NAME = "county"
    val ID = "_id"
    val COUNTY_NAME = "countyName"
    val WEATHER_ID = "weatherId"
    val CITY_ID = "cityId"
}