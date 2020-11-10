package com.maxpay.sdk.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.card.MaterialCardView
import com.maxpay.sdk.R
import com.maxpay.sdk.model.InputFormLength
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.layout_billing_address.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class EditTextValidator : KoinComponent {

    private val dateInterface: DateInterface by inject()

    fun validateET(inputForm: InputFormLength) {
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
    }

    fun validateExpirationDate(inputForm: InputFormLength) {
        val currYear = dateInterface.getYearTwoSymbols(System.currentTimeMillis())
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
            setError(inputForm)
            it?.let {

                when(it.getOrNull(0)) {
                    '0', '1' -> {
                        removeErrorFromField(inputForm)
                    }
                    else -> setError(inputForm)
                }

                when(it.getOrNull(1)) {
                    '0', '1', '2' -> { removeErrorFromField(inputForm) }
                    else -> setError(inputForm)
                }

                it.getOrNull(3)?.let {
                    if (it.toInt() < currYear[0].toInt())
                        setError(inputForm)
                    else
                        removeErrorFromField(inputForm)
                }

                it.getOrNull(4)?.let { c ->
                    if (it.contains("y"))
                        return@let
                    if (it.subSequence(3, 4).toString().toInt() < currYear.toInt())
                        setError(inputForm)
                    else
                        removeErrorFromField(inputForm)
                }


//                when(it.getOrNull(3)) {
//                    '0', '1', '2' -> { removeErrorFromField(inputForm) }
//                    else -> setError(inputForm)
//                }

//                if (it.length > 0)
//                    when (it.get(0)) {
//                    '0', '1' -> {}
//                    else -> {
//                        inputForm.input.setTextColor(Color.RED)
//                        inputForm.card.strokeColor = Color.RED
//                    }
//                }
//                else if ( it.length == ) {

//            }
            }
        }

//        inputForm.input.setOnFocusChangeListener { _, b ->
//            if (!b)
//                isFormLengthValid(inputForm)
//        }
    }


    fun validateCardNumber(inputForm: InputFormLength, imageView: ImageView) {
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
            it?.let {
                val img = when (if (it.length > 0) it.get(0) else '0') {
                    '4' -> ContextCompat.getDrawable(
                        inputForm.card.context,
                        R.drawable.ic_visa_logo
                    )
                    '5' -> ContextCompat.getDrawable(
                        inputForm.card.context,
                        R.drawable.ic_logo_mastercard
                    )
                    else -> ContextCompat.getDrawable(
                        inputForm.card.context,
                        R.drawable.ic_credit_card
                    )
                }
                img?.let { imageView.setImageDrawable(it) }
            }
        }

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)

//            if (!b && editText.text!!.length > 0)
//                cardView.strokeColor = Color.RED
        }

    }

    private fun setError(inputForm: InputFormLength) {
        inputForm.input.setTextColor(Color.RED)
        inputForm.card.strokeColor = Color.RED
    }

    private fun removeErrorFromField(inputForm: InputFormLength) {
        if (inputForm.input.currentTextColor == Color.RED) {
            inputForm.input.setTextColor(
                ContextCompat.getColor(inputForm.card.context, R.color.colorDarkText)
            )
            inputForm.card.strokeColor = Color.TRANSPARENT
        }
    }

    private fun isFormLengthValid(vararg inputLengthForm: InputFormLength): Boolean {
        var isValidate: Boolean? = null
        for (item in inputLengthForm) {
            item.input.text?.let {
                val validText = it.toString().replace(" ", "")
                    .replace("_", "")
                    .replace("/", "")
                if (validText.length < item.requiredLength) {
                    item.input.setTextColor(Color.RED)
                    item.card.strokeColor = Color.RED
//                item.input.error = getString(R.string.Global_field_not_vaild)
                    isValidate = false
                } else {
                    if (isValidate != false)
                        isValidate = true
                    item.input.error = null
                }
            } ?: kotlin.run {
//                item.input.error = getString(R.string.Global_field_empty)
                isValidate = false
            }
        }
        return isValidate ?: true
    }
}