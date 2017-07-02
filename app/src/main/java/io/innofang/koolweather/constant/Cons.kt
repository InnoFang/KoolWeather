package io.innofang.koolweather.constant

/**
 * Author: Inno Fang
 * Time: 2017/6/26 21:43
 * Description:
 */

class Cons {
    companion object {
        val URL_PROVINCE = "http://guolin.tech/api/china"
        val URL_CITY: (String) -> String = { provinceCode -> "$URL_PROVINCE/$provinceCode"}
        val URL_COUNTY: (String, String) -> String =
                { provinceCode, cityCode -> "$URL_PROVINCE/$provinceCode/$cityCode" }
        val URL_WEATHER: (String) -> String =
                { weatherId -> "http://guolin.tech/api/weather?cityid=$weatherId&key=bc0418b57b2d4918819d3974ac1285d9" }

        val URL_BING_PIC = "http://guolin.tech/api/bing_pic"
    }
}