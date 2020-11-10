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
   val  backgroundColor: Color? = null,
   val  errorColor: Color? = null,
   val  hyperlinkColor: Color? = null,  //  font size same as main text, weight semibold,
   val  navigationBarColor: Color? = null,
   val  navigationBarTitleColor: Color? = null,
//   val  navigationBarFont: UIFont,
   val  headerTitleColor: Color? = null,
   val  headerAmountColor: Color,
   val  headerSeparatorColor: Color,

//   val  headerStandardTitleFont: UIFont,
//   val  headerLargeTitleFont: UIFont,
//   val  headerAmountFont: UIFont,
   val  fieldBackgroundColor: Color,
   val  fieldTitleColor: Color,
   val  fieldTextColor: Color,
//   val  fieldTitleFont: UIFont,
//   val  fieldTextFont: UIFont,
   val  fieldBackgroundCornerRadius: Float,
    //  checkbox selected color = enabledButtonBackgroundColor,
    //  checkbox deselected color = fieldBackgroundColor,
    //  checkbox border color = headerSeparatorColor,
   val  conditionsTextColor: Color,
   val  checkboxCornerRadius: Float,
//   val  conditionsFont: UIFont,
   val  enabledButtonBackgroundColor: Color,
   val  enabledButtonTitleColor: Color,
   val  disabledButtonBackgroundColor: Color,
   val  disabledButtonTitleColor: Color,
//   val  buttonTitleFont: UIFont,
   val  buttonCornerRadius: Float
): Serializable

