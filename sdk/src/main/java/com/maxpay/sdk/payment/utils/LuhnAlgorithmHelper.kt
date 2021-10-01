package com.maxpay.sdk.payment.utils

class LuhnAlgorithmHelper {
    companion object{
        // Returns true if given
        // card number is valid
        fun checkLuhn(cardNo: String): Boolean {
            val nDigits = cardNo.length
            var nSum = 0
            var isSecond = false
            for (i in nDigits - 1 downTo 0) {
                var d = cardNo[i] - '0'
                if (isSecond == true) d = d * 2

                // We add two digits to handle
                // cases that make two digits
                // after doubling
                nSum += d / 10
                nSum += d % 10
                isSecond = !isSecond
            }
            return nSum % 10 == 0
        }
    }
}