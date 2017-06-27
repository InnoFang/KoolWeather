package io.innofang.koolweather.db

/**
 * Author: Inno Fang
 * Time: 2017/6/26 16:20
 * Description:
 */

class Province(val map: MutableMap<String, Any?>) {
    var provinceName: String by map
    var provinceCode: Int by map

    constructor(id: Int, provinceName: String, provinceCode: Int) : this(HashMap()) {
        this.provinceCode = provinceCode
        this.provinceName = provinceName
    }
}

data class City(val map: MutableMap<String, Any?>) {
    var id: Int by map
    var cityName: String by map
    var cityCode: Int by map
    var provinceId: Int by map

    constructor(id: Int, cityName: String, cityCode: Int, provinceId: Int) : this(HashMap()) {
        this.id = id
        this.cityName = cityName
        this.cityCode = cityCode
        this.provinceId = provinceId
    }
}

data class County(val map: MutableMap<String, Any?>) {
    var id: Int by map
    var countyName: String by map
    var weatherId: String by map
    var cityId: Int by map

    constructor(id: Int, countyName: String, weatherId: String, cityId: Int) : this(HashMap()) {
        this.id = id
        this.countyName = countyName
        this.weatherId = weatherId
        this.cityId = cityId
    }
}