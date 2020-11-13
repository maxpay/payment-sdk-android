package com.maxpay.sdk.model

import android.graphics.Color
import com.maxpay.sdk.model.request.TransactionType
import java.io.Serializable

data class MaxPayInitData(
    val accountName: String,
    val accountPassword: String,
    val apiVersion: Int,
    val theme: MaxPayTheme? = null
): Serializable

data class MaxPayTheme(
   val  backgroundColor: Int? = null,
   val  errorColor: Int? = null,
   val  hyperlinkColor: Int? = null,  //  font size same as main text, weight semibold,
   val  navigationBarColor: Int? = null,
   val  navigationBarTitleColor: Int? = null,
//   val  navigationBarFont: UIFont,
   val  headerTitleColor: Int? = null,
   val  headerAmountColor: Int? = null,
   val  headerSeparatorColor: Int? = null,

//   val  headerStandardTitleFont: UIFont,
//   val  headerLargeTitleFont: UIFont,
//   val  headerAmountFont: UIFont,
   val  fieldBackgroundColor: Int? = null,
   val  fieldTitleColor: Int? = null,
   val  fieldTextColor: Int? = null,
//   val  fieldTitleFont: UIFont,
//   val  fieldTextFont: UIFont,
   val  fieldBackgroundCornerRadius: Float? = null,
    //  checkbox selected Int = enabledButtonBackgroundInt,
    //  checkbox deselected Int = fieldBackgroundInt,
    //  checkbox border Int = headerSeparatorInt,
   val  conditionsTextColor: Int? = null,
   val  checkboxCornerRadius: Float? = null,
//   val  conditionsFont: UIFont? = null,
   val  enabledButtonBackgroundColor: Int? = null,
   val  enabledButtonTitleColor: Int? = null,
   val  disabledButtonBackgroundColor: Int? = null,
   val  disabledButtonTitleColor: Int? = null,
//   val  buttonTitleFont: UIFont? = null,
   val  buttonCornerRadius: Float? = null
): Serializable

