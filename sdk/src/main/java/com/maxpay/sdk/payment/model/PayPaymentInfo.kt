package com.maxpay.sdk.payment.model

import com.maxpay.sdk.payment.model.request.TransactionType
import java.io.Serializable
import java.util.*

/**
 * Prepare payment information, and start payment activity
 *
 * @param context context
 * @param data Initialisation data info
 * @param pay Payment information
 * @param callback Here you will get signature calculation callback, and response result
 */
data class PayPaymentInfo(
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
    var sale3dCallBackUrl: String = "",
    var sale3dRedirectUrl: String = "",
    var auth3dRedirectUrl: String? = null,
    var currency: Currency = Currency.getInstance(Locale.getDefault()),
    var birthday: String? = null
): Serializable


data class PaySignatureInfo(
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
    val redirect_url: String? = null,
    val date_of_birth: String? = null
): Serializable

