package com.maxpay.sdk.payment.datamodule.api

import com.maxpay.sdk.payment.model.request.PaymentDto
import com.maxpay.sdk.payment.model.response.BaseResponse
import io.reactivex.Single
import retrofit2.http.*

internal interface PayApi {

    @POST("cc")
    fun pay(@Body paymentDto: PaymentDto): Single<BaseResponse>
}