package com.maxpay.sdk.model.request

import com.google.gson.annotations.SerializedName
data class SalePayment(
    @SerializedName("api_version")
    val apiVersion: Int?,
//    @SerializedName("merchant_account")
//    val merchantAccount: String?,
//    @SerializedName("merchant_password")
//    val merchantPassword: String?,
    @SerializedName("transaction_unique_id")
    val transactionId: String,
    @SerializedName("transaction_type")
    val transactionType: TransactionType,
    val amount: Float,
    val currency: String,
    @SerializedName("card_number")
    val cardNumber: String,
    @SerializedName("card_exp_month")
    val cardExpMonth: String,
    @SerializedName("card_exp_year")
    val cardExpYear: String,
    val cvv: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("card_holder")
    val cardHolder: String,
    val address: String? = null,
    val city: String? = null,
//    val state: String,
    val zip: String? = null,
    val country: String,
    @SerializedName("user_phone")
    val userPhone: String? = null,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("user_ip")
    val userIp: String,
    @SerializedName("callback_url")
    var callBackUrl: String? = null,
    @SerializedName("redirect_url")
    var redirectUrl: String? = null,
    @SerializedName("public_key")
    val publicKey: String?,
    var signature: String? = null,
    val auth_type: String = "bySignature"

)
