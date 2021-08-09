package com.maxpay.testappmaxpay.core

import java.text.DecimalFormat

fun Float.getPriceString() = DecimalFormat("#.##").format(this).replace(",", ".")
fun Float.removeZero(): Float {
    val result = this - this.toInt()
    if (result != 0f) {
        return this
    }
    else
        return Math.round(result).toFloat()

}