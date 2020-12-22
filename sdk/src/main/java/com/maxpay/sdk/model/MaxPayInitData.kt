package com.maxpay.sdk.model

import java.io.Serializable

data class MaxPayInitData(
    val apiVersion: Int,
    val publicKey: String,
    val privateKey: String,
    val theme: MaxPayTheme? = null,
    val fieldsToShow: AvailableFields? = null
): Serializable

data class MaxPayTheme(
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
   val  fieldTitleFont: String? = null,
   val  fieldTextFont: String? = null,
   val  fieldBackgroundCornerRadius: Float? = null,
    //  checkbox selected Int = enabledButtonBackgroundInt,
    //  checkbox deselected Int = fieldBackgroundInt,
    //  checkbox border Int = headerSeparatorInt,
//   val  conditionsTextColor: Int? = null,
//   val  checkboxCornerRadius: Float? = null,
//   val  conditionsFont: UIFont? = null,
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
    var showCountryField: Boolean? = null
): Serializable

