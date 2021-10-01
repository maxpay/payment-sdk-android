package com.maxpay.sdk.payment.data
import com.maxpay.sdk.payment.model.PaySignatureInfo

/**
 * Callback
 */
interface PayCallback {
    /**
     * Receive result from payment sdk
     * @param result [PayResult]
     */
    fun onResponseResult(result: PayResult?)

    /**
     * @param dataForSignature Receive data for signature calculation
     * @param signatureCalback After calculating of signature, send result to this callback
     * signatureCalback.invoke("YourCalculatedSignature")
     */
    fun onNeedCalculateSignature(dataForSignature: PaySignatureInfo?, signatureCalback: (String) -> Unit)
}