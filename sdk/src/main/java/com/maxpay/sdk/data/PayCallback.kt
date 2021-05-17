package com.maxpay.sdk.data

import com.maxpay.sdk.model.PaySignatureInfo

interface PayCallback {
    fun onResponseResult(result: PayResult?)

    fun onNeedCalculateSignature(dataForSignature: PaySignatureInfo?, signatureCalback: (String) -> Unit)
}