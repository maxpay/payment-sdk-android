package com.maxpay.sdk.data

interface MaxpayCallback {
    fun onResponseSuccess(msg: MaxpayResult?)

    fun onResponceError(result: MaxpayResult?)
}