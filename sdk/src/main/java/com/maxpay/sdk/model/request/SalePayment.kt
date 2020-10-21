package com.maxpay.sdk.model.request

import com.google.gson.annotations.SerializedName
data class SalePayment(
    @SerializedName("api_version")
    val apiVersion: Int,
    @SerializedName("merchant_account")
    val merchantAccount: String,
    @SerializedName("merchant_password")
    val merchantPassword: String,
    @SerializedName("transaction_unique_id")
    val transactionId: String,
    @SerializedName("transaction_type")
    val transactionType: TransactionType,
    val amount: Int,
    val currency: String,
    @SerializedName("card_number")
    val cardNumber: String,
    @SerializedName("card_exp_month")
    val cardExpMonth: String,
    @SerializedName("card_exp_year")
    val cardExpYear: String,
    val cvv: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("card_holder")
    val cardHolder: String,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val country: String,
    @SerializedName("user_phone")
    val userPhone: String,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("user_ip")
    val userIp: String
)

enum class TransactionType {
    SALE, AUTH, SALE3D, REFUND, AUTH3D,
    VOID, SETTLE, TOKENIZE, CHECK
}
//: 1,
//"": "{{account}}",
//"": "{{password}}",
//"transaction_unique_id": "sale_request{{$timestamp}}",
//"transaction_type": "SALE",
//"amount": 11,
//"currency": "USD",
//"": "4012001038443335",
//"": "04",
//"": "2021",
//}