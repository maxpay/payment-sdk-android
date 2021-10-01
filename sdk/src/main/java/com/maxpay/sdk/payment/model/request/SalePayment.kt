package com.maxpay.sdk.payment.model.request

import com.google.gson.annotations.SerializedName
import com.maxpay.sdk.payment.core.removeZero
import com.maxpay.sdk.payment.model.PaySignatureInfo

internal data class SalePayment(
    @SerializedName("api_version")
    val apiVersion: Int?,
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
    val auth_type: String = "bySignature",
    val date_of_birth: String? = null  //string (YYYY-MM-DD)
)

internal fun SalePayment.toMaxpaySignatureData() = PaySignatureInfo(
    api_version = apiVersion,
    currency = currency,
    amount = amount.removeZero(),
    transaction_unique_id = transactionId,
    transaction_type = transactionType.toString(),
    country = country,
    zip = zip,
    city = city,
    address = address,
    state = null,
    callback_url = callBackUrl,
    first_name = firstName,
    last_name = lastName,
    redirect_url = redirectUrl,
    user_email = userEmail,
    user_ip = userIp,
    user_phone = userPhone,
    date_of_birth = date_of_birth
)
