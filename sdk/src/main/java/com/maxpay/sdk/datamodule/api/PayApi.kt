package com.maxpay.sdk.datamodule.api

import com.maxpay.sdk.model.request.AuthPayment
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface PayApi {

    @POST("api/auth/login")
    fun pay(@Body data: Any): Single<Any>


    @POST("cc")
    fun paySale(@Body payment: SalePayment): Single<Any>

    @POST("cc")
    fun payAuth(@Body payment: AuthPayment): Single<BaseResponse>
//
//    @POST("api/auth/register")
//    fun registration(@Body data: RegistrationModel): Single<LoginResponse>
//
//    @POST("api/auth/password/email")
//    fun sendResetPasswordEmail(@Body data: ResetPasswordModel): Completable
//
//    @POST("api/auth/password/reset")
//    fun sendNewPasswordAfterReset(@Query("expires") expiresDate: Long,
//                                  @Query("signature") signature: String,
//                                  @Body newPasswordModel: NewPasswordModel) : Single<LoginResponse>

}