package com.maxpay.sdk.payment.model

import java.io.Serializable
/**
 * Prepare payment information, and start payment activity
 *
 * @param apiVersion version of api
 * @param publicKey merchant public key
 * @param paymentGateway Production or sandbox gateway
 * @param theme Customize payment activity by this class
 * @param fieldsToShow [AvailableFields] choose fields that must be on payment screen
 */
data class PayInitInfo(
    val apiVersion: Int,
    val publicKey: String,
    val paymentGateway: PayGatewayInfo,
    val theme: PayTheme? = null,
    val fieldsToShow: AvailableFields? = null
): Serializable

enum class PayGatewayInfo{
    PRODUCTION, SANDBOX
}

data class PayTheme(
   val  backgroundColor: Int? = null,
   val  errorColor: Int? = null,
   val  hyperlinkColor: Int? = null,  //  font size same as main text, weight semibold,
   val  navigationBarColor: Int? = null,
   val  navigationBarTitleColor: Int? = null,
   val  headerTitleColor: Int? = null,
   val  headerAmountColor: Int? = null,
   val  headerSeparatorColor: Int? = null,
   val  headerStandardTitleFont: String? = null,
   val  headerLargeTitleFont: String? = null,
   val  headerAmountFont: String? = null,
   val  fieldBackgroundColor: Int? = null,
   val  fieldTitleColor: Int? = null,
   val  fieldTextColor: Int? = null,
   val  progressBarColor: Int? = null,
   val  fieldTitleFont: String? = null,
   val  fieldTextFont: String? = null,
   val  fieldBackgroundCornerRadius: Float? = null,
   val  enabledButtonBackgroundColor: Int? = null,
   val  enabledButtonTitleColor: Int? = null,
   val  disabledButtonBackgroundColor: Int? = null,
   val  disabledButtonTitleColor: Int? = null,
   val  buttonCornerRadius: Float? = null

): Serializable

data class AvailableFields(
    var showBillingAddressLayout: Boolean? = null,
    var showNameField: Boolean? = null,
    var showAddressField: Boolean? = null,
    var showCityField: Boolean? = null,
    var showZipField: Boolean? = null,
    var showCountryField: Boolean? = null,
    var showBirthdayField: Boolean? = null
): Serializable

