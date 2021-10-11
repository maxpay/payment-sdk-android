package com.maxpay.sdk.payment.datamodule.repository

import com.maxpay.sdk.payment.datamodule.api.Api
import com.maxpay.sdk.payment.datamodule.api.PayApi
import com.maxpay.sdk.payment.model.PayGatewayInfo
import com.maxpay.sdk.payment.model.request.PaymentDto
import com.maxpay.sdk.payment.model.response.BaseResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class PayRepositoryImpl(
    private val api: Api
) : PayRepository {

    override fun pay(authPaymentDto: PaymentDto, gatewayInfo: PayGatewayInfo?): Single<BaseResponse> {
        return api.createApi(PayApi::class.java, gatewayInfo)
            .pay(authPaymentDto)
            .subscribeOn(Schedulers.io())
    }
}