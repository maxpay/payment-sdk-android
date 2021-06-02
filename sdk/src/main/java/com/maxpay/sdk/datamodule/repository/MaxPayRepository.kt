package com.maxpay.sdk.datamodule.repository

import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.datamodule.api.PayApi
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.PayGatewayInfo
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.ThreeDPayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class MaxPayRepositoryImpl(
    private val api: Api
): MaxPayRepository {
    override fun pay3D(salePayment: ThreeDPayment, gatewayInfo: PayGatewayInfo): Single<BaseResponse> {
        return api.createApi(PayApi::class.java, gatewayInfo)
            .pay3D(salePayment)
            .subscribeOn(Schedulers.io())
    }

    override fun pay(authPayment: SalePayment, gatewayInfo: PayGatewayInfo?): Single<BaseResponse> {
        return api.createApi(PayApi::class.java, gatewayInfo)
            .pay(authPayment)
            .subscribeOn(Schedulers.io())
    }
}