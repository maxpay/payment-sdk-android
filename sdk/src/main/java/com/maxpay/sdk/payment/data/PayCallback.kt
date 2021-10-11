package com.maxpay.sdk.payment.data


/**
 * Payment callback
 */
interface PayCallback {

    /**
     * Receive result from payment sdk
     * @param result [PayResult]
     */
    fun onResponseResult(result: PayResult?)

    /**
     *
     *
     * On mobile application you must:
     * 1) Send [dataForSignature] to backend
     * 2) Receive calculated signature
     * 3) Invoke [signatureCallback] and pass signature as the argument
     *
     * On your backend you must:
     * 1) Add a private key for this data using "|" as a separator
     * 2) Convert into the lowercase
     * 3) Take sha256 hash
     * 4) Return the resulting value to the mobile application
     *
     * **Don't use a private key on mobile application**
     *
     * @param dataForSignature Receive data for signature calculation
     * @param signatureCallback After calculating of signature, send result to this callback
     */
    fun onNeedCalculateSignature(dataForSignature: String, signatureCallback: (String) -> Unit)
}