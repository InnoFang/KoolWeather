package io.innofang.koolweather.bean

/**
 * Author: Inno Fang
 * Time: 2017/7/2 14:26
 * Description:
 */


class Weather {

    var HeWeather: List<HeWeatherBean>? = null


    class HeWeatherBean {


        /**
         * aqi : {"city":{"aqi":"27","co":"1","no2":"24","o3":"51","pm10":"27","pm25":"14","qlty":"优","so2":"7"}}
         * basic : {"city":"广州","cnty":"中国","id":"CN101280101","lat":"23.12517738","lon":"113.28063965","update":{"loc":"2017-07-02 13:50","utc":"2017-07-02 05:50"}}
         * daily_forecast : [{"astro":{"mr":"13:31","ms":"00:52","sr":"05:45","ss":"19:16"},"cond":{"code_d":"306","code_n":"306","txt_d":"中雨","txt_n":"中雨"},"date":"2017-07-02","hum":"81","pcpn":"34.4","pop":"100","pres":"1007","tmp":{"max":"32","min":"27"},"uv":"11","vis":"14","wind":{"deg":"159","dir":"东南风","sc":"3-4","spd":"12"}},{"astro":{"mr":"14:21","ms":"01:29","sr":"05:45","ss":"19:16"},"cond":{"code_d":"306","code_n":"302","txt_d":"中雨","txt_n":"雷阵雨"},"date":"2017-07-03","hum":"83","pcpn":"33.8","pop":"100","pres":"1007","tmp":{"max":"33","min":"27"},"uv":"10","vis":"15","wind":{"deg":"143","dir":"无持续风向","sc":"微风","spd":"3"}},{"astro":{"mr":"15:12","ms":"02:06","sr":"05:45","ss":"19:16"},"cond":{"code_d":"302","code_n":"101","txt_d":"雷阵雨","txt_n":"多云"},"date":"2017-07-04","hum":"80","pcpn":"8.7","pop":"100","pres":"1008","tmp":{"max":"34","min":"28"},"uv":"12","vis":"17","wind":{"deg":"140","dir":"南风","sc":"3-4","spd":"15"}},{"astro":{"mr":"16:02","ms":"02:45","sr":"05:46","ss":"19:16"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2017-07-05","hum":"81","pcpn":"7.5","pop":"100","pres":"1011","tmp":{"max":"35","min":"27"},"uv":"6","vis":"17","wind":{"deg":"146","dir":"东南风","sc":"3-4","spd":"17"}},{"astro":{"mr":"16:52","ms":"03:26","sr":"05:46","ss":"19:16"},"cond":{"code_d":"302","code_n":"101","txt_d":"雷阵雨","txt_n":"多云"},"date":"2017-07-06","hum":"82","pcpn":"9.2","pop":"100","pres":"1010","tmp":{"max":"34","min":"27"},"uv":"6","vis":"17","wind":{"deg":"152","dir":"无持续风向","sc":"微风","spd":"3"}},{"astro":{"mr":"17:42","ms":"04:08","sr":"05:46","ss":"19:16"},"cond":{"code_d":"302","code_n":"302","txt_d":"雷阵雨","txt_n":"雷阵雨"},"date":"2017-07-07","hum":"83","pcpn":"6.4","pop":"100","pres":"1010","tmp":{"max":"33","min":"27"},"uv":"6","vis":"17","wind":{"deg":"159","dir":"东风","sc":"3-4","spd":"12"}},{"astro":{"mr":"18:31","ms":"04:54","sr":"05:47","ss":"19:16"},"cond":{"code_d":"302","code_n":"306","txt_d":"雷阵雨","txt_n":"中雨"},"date":"2017-07-08","hum":"82","pcpn":"6.3","pop":"100","pres":"1010","tmp":{"max":"33","min":"25"},"uv":"6","vis":"16","wind":{"deg":"157","dir":"无持续风向","sc":"微风","spd":"3"}}]
         * hourly_forecast : [{"cond":{"code":"301","txt":"强阵雨"},"date":"2017-07-02 16:00","hum":"75","pop":"97","pres":"1005","tmp":"29","wind":{"deg":"157","dir":"东南风","sc":"微风","spd":"14"}},{"cond":{"code":"301","txt":"强阵雨"},"date":"2017-07-02 19:00","hum":"79","pop":"97","pres":"1006","tmp":"28","wind":{"deg":"143","dir":"东南风","sc":"微风","spd":"13"}},{"cond":{"code":"305","txt":"小雨"},"date":"2017-07-02 22:00","hum":"84","pop":"94","pres":"1008","tmp":"27","wind":{"deg":"141","dir":"东南风","sc":"微风","spd":"15"}}]
         * now : {"cond":{"code":"300","txt":"阵雨"},"fl":"31","hum":"73","pcpn":"0","pres":"1005","tmp":"29","vis":"10","wind":{"deg":"120","dir":"东风","sc":"微风","spd":"10"}}
         * status : ok
         * suggestion : {"comf":{"brf":"较不舒适","txt":"白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},"sport":{"brf":"较不宜","txt":"有较强降水，建议您选择在室内进行健身休闲运动。"},"trav":{"brf":"一般","txt":"稍热，且风稍大，并且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
         */

        var aqi: AqiBean? = null
        var basic: BasicBean? = null
        var now: NowBean? = null
        var status: String? = null
        var suggestion: SuggestionBean? = null
        var daily_forecast: List<DailyForecastBean>? = null
        var hourly_forecast: List<HourlyForecastBean>? = null

        class AqiBean {
            /**
             * city : {"aqi":"27","co":"1","no2":"24","o3":"51","pm10":"27","pm25":"14","qlty":"优","so2":"7"}
             */

            var city: CityBean? = null

            class CityBean {
                /**
                 * aqi : 27
                 * co : 1
                 * no2 : 24
                 * o3 : 51
                 * pm10 : 27
                 * pm25 : 14
                 * qlty : 优
                 * so2 : 7
                 */

                var aqi: String? = null
                var co: String? = null
                var no2: String? = null
                var o3: String? = null
                var pm10: String? = null
                var pm25: String? = null
                var qlty: String? = null
                var so2: String? = null
            }
        }

        class BasicBean {
            /**
             * city : 广州
             * cnty : 中国
             * id : CN101280101
             * lat : 23.12517738
             * lon : 113.28063965
             * update : {"loc":"2017-07-02 13:50","utc":"2017-07-02 05:50"}
             */

            var city: String? = null
            var cnty: String? = null
            var id: String? = null
            var lat: String? = null
            var lon: String? = null
            var update: UpdateBean? = null

            class UpdateBean {
                /**
                 * loc : 2017-07-02 13:50
                 * utc : 2017-07-02 05:50
                 */

                var loc: String? = null
                var utc: String? = null
            }
        }

        class NowBean {
            /**
             * cond : {"code":"300","txt":"阵雨"}
             * fl : 31
             * hum : 73
             * pcpn : 0
             * pres : 1005
             * tmp : 29
             * vis : 10
             * wind : {"deg":"120","dir":"东风","sc":"微风","spd":"10"}
             */

            var cond: CondBean? = null
            var fl: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pres: String? = null
            var tmp: String? = null
            var vis: String? = null
            var wind: WindBean? = null

            class CondBean {
                /**
                 * code : 300
                 * txt : 阵雨
                 */

                var code: String? = null
                var txt: String? = null
            }

            class WindBean {
                /**
                 * deg : 120
                 * dir : 东风
                 * sc : 微风
                 * spd : 10
                 */

                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }

        class SuggestionBean {
            /**
             * comf : {"brf":"较不舒适","txt":"白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。"}
             * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
             * drsg : {"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"}
             * flu : {"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"}
             * sport : {"brf":"较不宜","txt":"有较强降水，建议您选择在室内进行健身休闲运动。"}
             * trav : {"brf":"一般","txt":"稍热，且风稍大，并且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。"}
             * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
             */

            var comf: ComfBean? = null
            var cw: CwBean? = null
            var drsg: DrsgBean? = null
            var flu: FluBean? = null
            var sport: SportBean? = null
            var trav: TravBean? = null
            var uv: UvBean? = null

            class ComfBean {
                /**
                 * brf : 较不舒适
                 * txt : 白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class CwBean {
                /**
                 * brf : 不宜
                 * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class DrsgBean {
                /**
                 * brf : 热
                 * txt : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class FluBean {
                /**
                 * brf : 少发
                 * txt : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class SportBean {
                /**
                 * brf : 较不宜
                 * txt : 有较强降水，建议您选择在室内进行健身休闲运动。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class TravBean {
                /**
                 * brf : 一般
                 * txt : 稍热，且风稍大，并且较强降雨的天气将给您的出行带来很多的不便，若坚持旅行建议带上雨具。
                 */

                var brf: String? = null
                var txt: String? = null
            }

            class UvBean {
                /**
                 * brf : 弱
                 * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
                 */

                var brf: String? = null
                var txt: String? = null
            }
        }

        class DailyForecastBean {
            /**
             * astro : {"mr":"13:31","ms":"00:52","sr":"05:45","ss":"19:16"}
             * cond : {"code_d":"306","code_n":"306","txt_d":"中雨","txt_n":"中雨"}
             * date : 2017-07-02
             * hum : 81
             * pcpn : 34.4
             * pop : 100
             * pres : 1007
             * tmp : {"max":"32","min":"27"}
             * uv : 11
             * vis : 14
             * wind : {"deg":"159","dir":"东南风","sc":"3-4","spd":"12"}
             */

            var astro: AstroBean? = null
            var cond: CondBeanX? = null
            var date: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pop: String? = null
            var pres: String? = null
            var tmp: TmpBean? = null
            var uv: String? = null
            var vis: String? = null
            var wind: WindBeanX? = null

            class AstroBean {
                /**
                 * mr : 13:31
                 * ms : 00:52
                 * sr : 05:45
                 * ss : 19:16
                 */

                var mr: String? = null
                var ms: String? = null
                var sr: String? = null
                var ss: String? = null
            }

            class CondBeanX {
                /**
                 * code_d : 306
                 * code_n : 306
                 * txt_d : 中雨
                 * txt_n : 中雨
                 */

                var code_d: String? = null
                var code_n: String? = null
                var txt_d: String? = null
                var txt_n: String? = null
            }

            class TmpBean {
                /**
                 * max : 32
                 * min : 27
                 */

                var max: String? = null
                var min: String? = null
            }

            class WindBeanX {
                /**
                 * deg : 159
                 * dir : 东南风
                 * sc : 3-4
                 * spd : 12
                 */

                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }

        class HourlyForecastBean {
            /**
             * cond : {"code":"301","txt":"强阵雨"}
             * date : 2017-07-02 16:00
             * hum : 75
             * pop : 97
             * pres : 1005
             * tmp : 29
             * wind : {"deg":"157","dir":"东南风","sc":"微风","spd":"14"}
             */

            var cond: CondBeanXX? = null
            var date: String? = null
            var hum: String? = null
            var pop: String? = null
            var pres: String? = null
            var tmp: String? = null
            var wind: WindBeanXX? = null

            class CondBeanXX {
                /**
                 * code : 301
                 * txt : 强阵雨
                 */

                var code: String? = null
                var txt: String? = null
            }

            class WindBeanXX {
                /**
                 * deg : 157
                 * dir : 东南风
                 * sc : 微风
                 * spd : 14
                 */

                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }
    }
}
