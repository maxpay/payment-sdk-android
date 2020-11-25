package com.maxpay.testappmaxpay.utils

import java.util.*

class Utils {
    companion object {
        fun getCurrenciesList(): Set<Currency> {
            val currencyList = Currency.getAvailableCurrencies().sortedBy { it.currencyCode }.toMutableList()
            val usd = Currency.getInstance("USD")
            val eur = Currency.getInstance("EUR")
            val gbp = Currency.getInstance("GBP")
            currencyList.add(0, usd)
            currencyList.add(1, eur)
            currencyList.add(2, gbp)

            return currencyList.toSet()
        }
    }
}