# KoolWeather

[English](https://github.com/InnoFang/KoolWeather/blob/master/README.md) | 中文

![koolWeather](https://raw.githubusercontent.com/InnoFang/KoolWeather/master/app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

使用 Kotlin 构建的天气应用

>感谢[郭霖](http://guolin.tech)提供的 [API](https://github.com/InnoFang/KoolWeather/blob/master/app/src/main/java/io/innofang/koolweather/constant/Api.kt) 

在这个项目中，我只是用了 Kotlin（ 没有使用 Anko ）来重构了 [CoolWeather](https://github.com/guolindev/coolweather)，此外我没有使用 [LitePal](https://github.com/LitePalFramework/LitePal) 来对本地数据进行存储，只使用了原生的 SQLite 进行操作，这将使你学习到如何使用 Kotlin 来操作 SQLite。另一方面，我演示了 kotlin 的基本使用和一些小技巧，如果你有更好的建议欢迎向我发起 pull request 或者 issue。

# 第三方库

```gradle
compile 'com.google.code.gson:gson:2.2.4'
compile 'com.squareup.okhttp3:okhttp:3.8.1'
compile 'com.github.bumptech.glide:glide:4.0.0-RC1'
```

# 参考

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