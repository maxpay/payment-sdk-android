package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.TransactionType
import java.io.Serializable

data class MaxPayInitData(
    val accountName: String,
    val accountPassword: String,
    val transactionType: TransactionType
): Serializable
