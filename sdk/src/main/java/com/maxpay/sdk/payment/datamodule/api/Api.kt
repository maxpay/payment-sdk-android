package com.maxpay.sdk.payment.datamodule.api

import android.content.res.Resources
import com.google.gson.GsonBuilder
import com.maxpay.sdk.payment.R
import com.maxpay.sdk.payment.model.PayGatewayInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class Api(private val resources: Resources) {

    private var retrofitClient: Retrofit? = null

    private var config: PayGatewayInfo = PayGatewayInfo.PRODUCTION

    fun <T> createApi(classToCreate: Class<T>, gatewayInfo: PayGatewayInfo?): T =
        getRetrofit(gatewayInfo ?: PayGatewayInfo.PRODUCTION).create(classToCreate)


    private fun getRetrofit(gatewayInfo: PayGatewayInfo): Retrofit {
        val currentClient = retrofitClient
        val currentConfig = config
        return if (currentClient == null || currentConfig != gatewayInfo) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            okHttpClient.dispatcher().maxRequestsPerHost = 20

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

            val url = when (gatewayInfo) {
                PayGatewayInfo.PRODUCTION -> resources.getString(R.string.prod_url)
                PayGatewayInfo.SANDBOX -> resources.getString(R.string.sandbox_url)
            }

            Retrofit.Builder().baseUrl(url).client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        } else {
            currentClient
        }
    }

}