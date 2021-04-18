package com.maxpay.sdk.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.math.BigInteger
import java.net.InetAddress
import java.util.*

internal interface CountryISOHelper {
    fun getISOCountries(): List<String>?
}

internal class CountryISOHelperImpl: CountryISOHelper, KoinComponent {
    var isoCountries: List<String>? = null
    init {
        isoCountries = getIso()
    }
    override fun getISOCountries(): List<String>? {
        return isoCountries
    }

    private fun getIso(): List<String> {
        val lst = arrayListOf<String>()
        Locale.getISOCountries().forEach { countryCode ->
            val locale = Locale("", countryCode)
            lst.add(locale.getISO3Country())
        }
        return lst

    }
}