package com.maxpay.sdk.utils

class ExpiryParser {
    fun getMonth(expiry: String) =
        expiry.substring(0, expiry.indexOf("/"))


    fun getYear(expiry: String) =
        "20${expiry.substring(expiry.indexOf("/") + 1)}"
}