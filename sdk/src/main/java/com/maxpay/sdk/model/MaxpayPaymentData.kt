package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.TransactionType
import java.io.Serializable
import java.util.*

data class MaxpayPaymentData(
    var transactionType: TransactionType = TransactionType.AUTH,
    var transactionId: String,
    var amount: Float,
    var firstName: String? = null,
    var lastName: String? = null,
    var address: String? = null,
    var city: String? = null,
    val state: String? = null,
    var zip: String? = null,
    var country: String = "",
    var userPhone: String? = null,
    var userEmail: String = "",
    var callBackUrl: String = "",
    var redirectUrl: String = "",
    var currency: Currency = Currency.getInstance(Locale.getDefault())
): Serializable


data class MaxpaySignatureData(
    val api_version: Int? = null,
    val auth_type: String = "bysignature",
    val transaction_unique_id: String? = null,
    val transaction_type: String? = null,
    var amount: String? = null,
    val currency: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String? = null,
    val country: String? = null,
    val user_phone: String? = null,
    val user_email: String? = null,
    val user_ip: String? = null,
    val callback_url: String? = null,
    val redirect_url: String? = null
): Serializable

