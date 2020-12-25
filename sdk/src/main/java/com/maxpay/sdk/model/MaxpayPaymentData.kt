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
    var cardHolder: String = "",
    var address: String? = null,
    var city: String? = null,
    val state: String? = null,
    var zip: String? = null,
    var country: String = "",
    var userPhone: String? = null,
    var userEmail: String = "",
    var cardNumber: String = "",
    var expYear: String = "",
    var expMonth: String = "",
    var cvv: String = "",
    var callBackUrl: String = "",
    var redirectUrl: String = "",
    var currency: Currency = Currency.getInstance(Locale.getDefault())
): Serializable