package com.maxpay.sdk

import android.content.Context
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData

interface SDKFacade {
    fun pay(context: Context?, data: MaxPayInitData, maxpay: MaxpayPaymentData, callback: MaxpayCallback)
}