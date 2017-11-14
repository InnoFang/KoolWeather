# KoolWeather

English | [中文](https://github.com/InnoFang/KoolWeather/blob/master/README_zh.md)

![koolWeather](https://raw.githubusercontent.com/InnoFang/KoolWeather/master/app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

Using Kotlin to build weather app.

>Thanks for the [API](https://github.com/InnoFang/KoolWeather/blob/master/app/src/main/java/io/innofang/koolweather/constant/Api.kt) provided by [guolindev](http://guolin.tech)

In this porject, I just use the Kotlin (without using Anko) to rebuild the [CoolWeather](https://github.com/guolindev/coolweather), moreover I don't use the [LitePal](https://github.com/LitePalFramework/LitePal) (a third-party Database library) but use the SQLite to store my local data , which will allow you to learn how to use kotlin to access SQLite. On the other hand, I demonstrated the basic usage of Kotlin and some tips. If you have a better suggestion welcome to pull request or issur for me.

# Third-party library

```gradle
compile 'com.google.code.gson:gson:2.2.4'
compile 'com.squareup.okhttp3:okhttp:3.8.1'
compile 'com.github.bumptech.glide:glide:4.0.0-RC1'
```

# Reference

 + [CoolWeather](https://github.com/guolindev/coolweather)

 + [Kotlin-for-Android-Developers](https://github.com/antoniolg/Kotlin-for-Android-Developers)

# [License](https://github.com/InnoFang/KoolWeather/blob/master/LICENSE)

 ```

   Copyright 2017 InnoFang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ```