package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.AuthPayment
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Single

interface MaxPayRepository {
    fun paySale(salePayment: SalePayment): Single<Any>
    fun payAuth3D(authPayment: AuthPayment): Single<BaseResponse>
}