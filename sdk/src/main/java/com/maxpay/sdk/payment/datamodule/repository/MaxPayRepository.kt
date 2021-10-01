package com.maxpay.sdk.payment.datamodule.repository

import com.maxpay.sdk.payment.model.PayGatewayInfo
import com.maxpay.sdk.payment.model.request.SalePayment
import com.maxpay.sdk.payment.model.request.ThreeDPayment
import com.maxpay.sdk.payment.model.response.BaseResponse
import io.reactivex.Single

internal interface MaxPayRepository {
    fun pay3D(salePayment: ThreeDPayment, gatewayInfo: PayGatewayInfo): Single<BaseResponse>
    fun pay(authPayment: SalePayment, gatewayInfo: PayGatewayInfo?): Single<BaseResponse>
}