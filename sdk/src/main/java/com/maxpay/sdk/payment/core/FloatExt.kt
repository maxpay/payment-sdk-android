package com.maxpay.sdk.payment.core

import java.text.DecimalFormat

fun Float.getPriceString() = DecimalFormat("#.##").format(this).replace(",", " ")
fun Float.removeZero(): String {
    val result = this - this.toInt()
    if (result != 0f) {
        return this.toString()
    }
    else
        return Math.round(this).toString()

}