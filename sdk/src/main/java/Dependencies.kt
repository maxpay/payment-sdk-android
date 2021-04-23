object Versions {

    const val appCompat = "1.1.0"
    const val coreKtx = "1.0.1"
    const val constraintLayout = "1.1.3"
    const val lifecycleVersion = "2.2.0"
    const val navVersion = "2.3.0"
    const val recyclerview = "1.1.0"
    const val material = "1.0.0"
    const val room = "2.2.3"

    const val koin = "2.2.0"

    const val retrofit = "2.5.0"
    const val okHttpLoggingInterceptor = "3.14.0"
    const val retrofitRxJavaAdapter = "2.5.0"
    const val retrofitCoroutinesAdapter = "0.9.2"

    const val rxJava = "2.2.8"
    const val rxAndroid = "2.0.2"
    const val rxKotlin = "2.3.0"

    const val glide = "4.11.0"
    const val gson = "2.8.5"

    const val customFontPart1 = "2.0.3"
    const val customFontPart2 = "3.1.1"

    const val pageIndicator = "1.0.6"
    const val calendarview = "2.0.1"
    const val threetenabp = "1.2.0"
    const val materialdatetimepicker = "4.2.3"
//    const val googlePay = "16.0.1"
    const val googlePay = "18.0.0"
    const val libvlc = "3.1.12"
    const val portmone = "1.3.4"
    const val uCrop = "2.2.5"

}

object Dependencies {

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"

    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinScopes = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModels = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    const val retrofitRxJavaAdapter =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxJavaAdapter}"
    const val retrofitCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"

    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val material = "com.google.android.material:material:${Versions.material}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomRx = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

}