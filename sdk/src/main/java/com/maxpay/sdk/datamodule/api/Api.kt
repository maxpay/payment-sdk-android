package com.maxpay.sdk.datamodule.api

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.maxpay.sdk.BuildConfig
import com.maxpay.sdk.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

internal class Api(private val pref: SharedPreferences, key: String?) {

    private val baseUrl = BuildConfig.BASE_URL
    private var retrofitClient by Delegates.notNull<Retrofit>()

    private val apisWithUserAccessToken by lazy {
        listOf(
            AuthApi::class.java,
            PayApi::class.java,
            PayApi::class.java
        )
    }

    private val apisWithAccessToken by lazy {
        listOf(
            PayApi::class.java,
            PayApi::class.java,
            AuthApi::class.java
        )
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 20

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        retrofitClient = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

    private val client: Retrofit
        get() {
            return retrofitClient
        }

    fun <T> createApi(classToCreate: Class<T>): T = Api(
        pref,
        key = when (classToCreate) {
            in apisWithAccessToken -> getPref(Constants.Companion.Token.ACCESS_TOKEN_KEY)
            in apisWithUserAccessToken -> getPref(Constants.Companion.Token.USER_ACCESS_TOKEN_KEY)
            else -> null
        }
    ).client.create(classToCreate)

    private fun getPref(findKey: String)= pref.getString(findKey, "")
}