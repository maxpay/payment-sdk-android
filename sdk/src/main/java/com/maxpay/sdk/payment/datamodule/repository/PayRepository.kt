package com.maxpay.sdk.payment.datamodule.repository

import com.maxpay.sdk.payment.model.PayGatewayInfo
import com.maxpay.sdk.payment.model.request.PaymentDto
import com.maxpay.sdk.payment.model.response.BaseResponse
import io.reactivex.Single

internal interface PayRepository {

    fun pay(authPaymentDto: PaymentDto, gatewayInfo: PayGatewayInfo?): Single<BaseResponse>
}