package com.maxpay.sdk.payment.model

import android.widget.EditText
import com.google.android.material.card.MaterialCardView

internal data class InputFormLength(
    val input: EditText,
    val card: MaterialCardView,
    val requiredLength: Int,
    var isValid: Boolean = false
)
