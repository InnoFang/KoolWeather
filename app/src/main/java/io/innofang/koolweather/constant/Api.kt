package io.innofang.koolweather.constant

/**
 * Author: Inno Fang
 * Time: 2017/6/26 21:43
 * Description:
 */

class Api {
    companion object {

        val URL_BING_PIC = "http://guolin.tech/api/bing_pic"

        val URL_PROVINCE = "http://guolin.tech/api/china"

        val URL_CITY: (String) -> String
                = { provinceCode -> "$URL_PROVINCE/$provinceCode" }

        val URL_COUNTY: (String, String) -> String
                = { provinceCode, cityCode -> "$URL_PROVINCE/$provinceCode/$cityCode" }

        val URL_WEATHER: (String) -> String
                = { weatherId -> "http://guolin.tech/api/weather?cityid=$weatherId&key=0ad89bb629b043f3879a6868d0508f11" }

    }
}