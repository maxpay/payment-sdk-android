package com.maxpay.sdk.data

import com.maxpay.sdk.model.MaxpaySignatureData

interface MaxpayCallback {
    fun onResponseSuccess(result: MaxpayResult?)

    fun onResponceError(result: MaxpayResult?)

    fun onNeedCalculateSignature(dataForSignature: MaxpaySignatureData?, signatureCalback: (String) -> Unit)
}