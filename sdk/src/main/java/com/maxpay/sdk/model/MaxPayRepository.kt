package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.ThreeDPayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Single

interface MaxPayRepository {
    fun pay3D(salePayment: ThreeDPayment): Single<BaseResponse>
    fun pay(authPayment: SalePayment): Single<BaseResponse>
}