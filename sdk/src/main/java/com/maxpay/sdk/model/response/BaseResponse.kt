package com.maxpay.sdk.model.response

import com.google.gson.annotations.SerializedName

internal data class BaseResponse(
    val pareq: String,
    @SerializedName("acs_url")
    val accessUrl: String,
    val reference: String,
    val status: ResponseStatus,
    val code: Int?,
    val message: String?
)

internal enum class ResponseStatus {
    success, decline, error
}