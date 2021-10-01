package com.maxpay.sdk.payment.datamodule.api

import io.reactivex.Single
import retrofit2.http.*

internal interface AuthApi {

    @POST("api/auth/login")
    fun login(@Body data: Any): Single<Any>
}