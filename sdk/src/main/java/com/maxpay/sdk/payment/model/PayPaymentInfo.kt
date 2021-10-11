package com.maxpay.sdk.payment.model

import com.maxpay.sdk.payment.model.request.TransactionType
import java.io.Serializable
import java.util.*

/**
 * Payment information
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
    var birthday: String? = null,
    var merchantInfo: PayMerchantInfo? = null
): Serializable


