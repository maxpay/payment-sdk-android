package com.maxpay.sdk.payment.model.request

import com.google.gson.annotations.SerializedName
import com.maxpay.sdk.payment.core.removeZero

internal data class PaymentDto(
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
    val date_of_birth: String? = null,//string (YYYY-MM-DD)
    @SerializedName("merchant_user_id")
    val merchantUserID: String?,
    @SerializedName("merchant_domain_name")
    val merchantDomainName: String?,
    @SerializedName("merchant_affiliate_id")
    val merchantAffiliateID: String?,
    @SerializedName("merchant_product_name")
    val merchantProductName: String?,
    @SerializedName("descriptor_merchant")
    val merchantDescriptor: String?,
    @SerializedName("descriptor_phone")
    val merchantPhone: String?,
    @SerializedName("mid_reference")
    val midReference: String?
) {


    fun generateDataForSignature(): String {
        val fields = mapOf<String, String?>(
            "api_version" to apiVersion?.toString(),
            "transaction_unique_id" to transactionId,
            "transaction_type" to transactionType.name,
            "amount" to amount.removeZero(),
            "currency" to currency,
            "first_name" to firstName,
            "last_name" to lastName,
            "address" to address,
            "city" to city,
            "zip" to zip,
            "country" to country,
            "user_phone" to userPhone,
            "user_email" to userEmail,
            "user_ip" to userIp,
            "callback_url" to callBackUrl,
            "redirect_url" to redirectUrl,
            "auth_type" to auth_type,
            "date_of_birth" to date_of_birth,
            "merchant_user_id" to merchantUserID,
            "merchant_domain_name" to merchantDomainName,
            "merchant_affiliate_id" to merchantAffiliateID,
            "merchant_product_name" to merchantProductName,
            "descriptor_merchant" to merchantDescriptor,
            "descriptor_phone" to merchantPhone,
            "mid_reference" to midReference
        ).toSortedMap()

        return fields.mapNotNull { (k, v) ->
            v?.let {
                "$k=$it"
            }
        }.joinToString("|")
    }
}