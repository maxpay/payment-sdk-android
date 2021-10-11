package com.maxpay.sdk.payment.model

import java.io.Serializable

data class PayMerchantInfo(
    /** User Id on merchant's side */
    val merchantUserID: String?,

    /** Merchant's domain name */
    val merchantDomainName: String?,

    /** Affiliate identifier */
    val merchantAffiliateID: String?,

    /** Name of the product */
    val merchantProductName: String?,

    /** Descriptor of the merchant */
    val merchantDescriptor: String?,

    /** Descriptor phone of the merchant */
    val merchantPhone: String?,

    /** Reference of the MID in Pay system */
    val midReference: String?
) : Serializable
