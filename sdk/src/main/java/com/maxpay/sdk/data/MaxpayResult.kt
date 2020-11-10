package com.maxpay.sdk.data

import java.io.Serializable

data class MaxpayResult(
    val status: MaxpayResultStatus,
    val message: String?
): Serializable

enum class MaxpayResultStatus {
    SUCCESS,
    REJECTED,
    ERROR,
    UNDEF,
    CANCELED
}