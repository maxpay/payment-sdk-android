package com.maxpay.sdk.datamodule.repository

import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.datamodule.api.PayApi
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.request.AuthPayment
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MaxPayRepositoryImpl(
    private val api: Api
): MaxPayRepository {
    override fun paySale(salePayment: SalePayment): Single<Any> {
        return api.createApi(PayApi::class.java)
            .paySale(salePayment)
            .subscribeOn(Schedulers.io())
    }

    override fun payAuth3D(authPayment: AuthPayment): Single<BaseResponse> {
        return api.createApi(PayApi::class.java)
            .payAuth(authPayment)
            .subscribeOn(Schedulers.io())
    }
}