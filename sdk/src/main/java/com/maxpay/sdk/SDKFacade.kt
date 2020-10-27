package com.maxpay.sdk

import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData

interface SDKFacade {
    fun init()
    fun pay(maxpay: MaxpayPaymentData)
}