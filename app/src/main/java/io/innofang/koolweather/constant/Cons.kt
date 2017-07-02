package io.innofang.koolweather.constant

/**
 * Author: Inno Fang
 * Time: 2017/6/26 21:43
 * Description:
 */

class Cons {
    companion object {
        val URL_PROVINCE = "http://guolin.tech/api/china"
        val URL_CITY : (String) -> String = { provinceCode -> "$URL_PROVINCE/$provinceCode"}
        val URL_COUNTY: (String, String) -> String =
                { provinceCode, cityCode -> "$URL_PROVINCE/$provinceCode/$cityCode" }
    }
}