package com.maxpay.sdk.data

import java.io.Serializable

data class PayResult(
    val status: PayResultStatus,
    val message: String?,
    val api_version: Int? = null,
    val merchant_account: String? = null,
    val sessionid: String? = null,
    val transaction_unique_id: String? = null,
    val token: String? = null,
    val reference: String? = null,
    val timestamp: Long? = null,
    val authcode: String? = null,
//    val status: String,
    val code: Int? = null
): Serializable

enum class PayResultStatus {
    SUCCESS,
    REJECTED,
    ERROR,
    UNDEF,
    CANCELED
}