package com.maxpay.sdk.data

interface MaxpayCallback {
    fun onResponseSuccess(result: MaxpayResult?)

    fun onResponceError(result: MaxpayResult?)
}