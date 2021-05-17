package com.maxpay.sdk.data

import com.maxpay.sdk.model.PaySignatureInfo

interface MaxpayCallback {
    fun onResponseResult(result: MaxpayResult?)

    fun onNeedCalculateSignature(dataForSignature: PaySignatureInfo?, signatureCalback: (String) -> Unit)
}