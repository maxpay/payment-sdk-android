package com.maxpay.sdk.datamodule.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface AuthApi {

    @POST("api/auth/login")
    fun login(@Body data: Any): Single<Any>
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