package com.maxpay.sdk

import android.content.Context
import com.maxpay.sdk.data.PayCallback
import com.maxpay.sdk.model.PayInitInfo
import com.maxpay.sdk.model.PayPaymentInfo

interface SDKFacade {
    fun pay(context: Context?, data: PayInitInfo, pay: PayPaymentInfo, callback: PayCallback)
}