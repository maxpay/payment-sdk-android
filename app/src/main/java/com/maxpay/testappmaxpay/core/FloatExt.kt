package com.maxpay.testappmaxpay.core

import java.text.DecimalFormat

fun Float.getPriceString() =  DecimalFormat("#.##").format(this).replace(",", " ")