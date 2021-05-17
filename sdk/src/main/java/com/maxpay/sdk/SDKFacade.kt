package com.maxpay.sdk

import android.content.Context
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.model.PayInitData
import com.maxpay.sdk.model.PayPaymentInfo

interface SDKFacade {
    fun pay(context: Context?, data: PayInitData, pay: PayPaymentInfo, callback: MaxpayCallback)
}