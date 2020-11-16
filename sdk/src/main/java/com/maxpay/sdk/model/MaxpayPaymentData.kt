package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.TransactionType
import java.io.Serializable
import java.util.*

data class MaxpayPaymentData(
    var transactionType: TransactionType = TransactionType.AUTH,
    var amount: Float = 0F,
    var firstName: String? = null,
    var lastName: String? = null,
    var cardHolder: String = "",
    var address: String = "",
    var city: String = "",
    val state: String = "",
    var zip: String = "",
    var country: String = "",
    var userPhone: String = "",
    var userEmail: String = "",
    var cardNumber: String = "",
    var expYear: String = "",
    var expMonth: String = "",
    var cvv: String = "",
    var currency: Currency = Currency.getInstance(Locale.getDefault())
): Serializable