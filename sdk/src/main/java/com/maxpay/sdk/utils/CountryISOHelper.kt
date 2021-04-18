package com.maxpay.sdk.utils

import org.koin.core.KoinComponent
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