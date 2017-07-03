# KoolWeather

[English](https://github.com/InnoFang/KoolWeather/blob/master/README.md) | 中文

![koolWeather](https://raw.githubusercontent.com/InnoFang/KoolWeather/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?token=AO_t-kx8NfcY3s9ihkWE2m3QLgzHDJN6ks5ZYx9bwA%3D%3D)

使用 Kotlin 构建的天气应用

>感谢[郭霖](http://guolin.tech)提供的 [API](https://github.com/InnoFang/KoolWeather/blob/master/app/src/main/java/io/innofang/koolweather/constant/Api.kt) 

在这个项目中，我只是用了 Kotlin（ 没有使用 Anko ）来重构了 [CookWeather](https://github.com/guolindev/coolweather)，此外我没有使用 [LitePal](https://github.com/LitePalFramework/LitePal) 来对本地数据进行存储，只使用了原生的 SQLite 进行操作，这将使你学习到如何使用 Kotlin 来操作 SQLite。另一方面，我演示了 kotlin 的基本使用和一些小技巧，如果你有更好的建议欢迎向我发起 pull request 或者 issue。

## 第三方库

```gradle
    compile 'com.google.code.gson:gson:2.2.4'
    compile 'com.squareup.okhttp3:okhttp:3.8.1'
    compile 'com.github.bumptech.glide:glide:4.0.0-RC1'
```

## 参考

 + [CoolWeather](https://github.com/guolindev/coolweather)

 + [Kotlin-for-Android-Developers](https://github.com/antoniolg/Kotlin-for-Android-Developers)

## LICENSE

 [Apache License 2.0](https://github.com/InnoFang/KoolWeather/blob/master/LICENSE)