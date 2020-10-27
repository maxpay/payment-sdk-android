package com.maxpay.sdk.model

import com.maxpay.sdk.model.request.TransactionType
import java.io.Serializable

data class MaxpayPaymentData(
    val transactionType: TransactionType,
    val amount: Float,
    val firstName: String,
    val lastName: String,
    val cardHolder: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val country: String,
    val userPhone: String,
    val userEmail: String
): Serializable
//val amount: Float,
//val currency: String,
//@SerializedName("card_number")
//val cardNumber: String,
//@SerializedName("card_exp_month")
//val cardExpMonth: String,
//@SerializedName("card_exp_year")
//val cardExpYear: String,
//val cvv: String,
//@SerializedName("first_name")
//val firstName: String,
//@SerializedName("last_name")
//val lastName: String,
//@SerializedName("card_holder")
//val cardHolder: String,
//val address: String,
//val city: String,
//val state: String,
//val zip: String,
//val country: String,
//@SerializedName("user_phone")
//val userPhone: String,
//@SerializedName("user_email")
//val userEmail: String,
//@SerializedName("user_ip")
//val userIp: String,
//@SerializedName("callback_url")
//val callBackUrl: String? = null,
//@SerializedName("redirect_url")
//val redirectUrl: String? = null