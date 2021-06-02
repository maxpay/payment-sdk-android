package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.ThreeDPayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Single

internal interface MaxPayRepository {
    fun pay3D(salePayment: ThreeDPayment, gatewayInfo: PayGatewayInfo): Single<BaseResponse>
    fun pay(authPayment: SalePayment, gatewayInfo: PayGatewayInfo?): Single<BaseResponse>
}