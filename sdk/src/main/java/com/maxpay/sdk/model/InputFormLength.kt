package com.maxpay.sdk.model

import android.widget.EditText
import com.google.android.material.card.MaterialCardView

data class InputFormLength(
    val input: EditText,
    val card: MaterialCardView,
    val requiredLength: Int
)
