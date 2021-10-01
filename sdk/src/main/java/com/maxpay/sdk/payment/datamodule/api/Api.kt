package com.maxpay.sdk.payment.datamodule.api

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.maxpay.sdk.payment.model.PayGatewayInfo
import com.maxpay.sdk.payment.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

internal class Api(private val pref: SharedPreferences, gatewayInfo: PayGatewayInfo?) {

    private var retrofitClient by Delegates.notNull<Retrofit>()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 20

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        val url = when(gatewayInfo){
            PayGatewayInfo.PRODUCTION -> Constants.PROD_URL
            PayGatewayInfo.SANDBOX -> Constants.SANDBOX_URL
            else -> Constants.SANDBOX_URL
        }
        retrofitClient = Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

    private val client: Retrofit
        get() {
            return retrofitClient
        }

    fun <T> createApi(classToCreate: Class<T>, gatewayInfo: PayGatewayInfo?): T = Api(
        pref,
        gatewayInfo = gatewayInfo
    ).client.create(classToCreate)

    private fun getPref(findKey: String)= pref.getString(findKey, "")
}