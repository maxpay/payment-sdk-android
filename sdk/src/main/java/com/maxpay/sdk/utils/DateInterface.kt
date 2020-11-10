package com.maxpay.sdk.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

interface DateInterface {
    fun getSimpleDate(dateMilliseconds: Long): String
    val ukLocale:Locale
    fun getCurrentTimeStamp(): String
    fun getYearTwoSymbols(dateMilliseconds: Long): String
}

class DateFormat : DateInterface {
    override fun getSimpleDate(dateMilliseconds: Long): String {
        return SimpleDateFormat("dd - MMMM.yyyy hh:mm:ss").format(Date(dateMilliseconds))
    }

    override fun getYearTwoSymbols(dateMilliseconds: Long): String {
        val s = SimpleDateFormat("yyyy").format(Date(dateMilliseconds))
        return s.substring(2)
    }

    override val ukLocale = Locale("en", "US")


    override fun getCurrentTimeStamp(): String {
        return SimpleDateFormat("Mdd-mm-ss", ukLocale).format(Date(System.currentTimeMillis()))
    }

}