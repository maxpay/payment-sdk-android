package com.maxpay.testappmaxpay.model

import java.util.*

data class ProductItemtUI(
    val id: Int,
    val title: String,
    val price: Float,
    var count: Int,
    val pictureDrawable: Int,
    val currency: Currency
)
