package io.innofang.koolweather

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:00
 * Description: bean classes
 */

data class Province(var id: Int, var provinceName: String, var provinceCode: Int)

data class City(var id: Int, var cityName: String, var cityCode: Int, var provinceId: Int)

data class County(var id: Int, var countyName: String, var weatherId: String, var cityId: Int)
