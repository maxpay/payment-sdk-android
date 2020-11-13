package com.maxpay.sdk.utils

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.maxpay.sdk.R
import com.maxpay.sdk.model.InputFormLength
import com.maxpay.sdk.model.MaxPayTheme
import org.koin.core.KoinComponent
import org.koin.core.inject

class EditTextValidator(val theme: MaxPayTheme?) : KoinComponent {

    private val dateInterface: DateInterface by inject()
    private var errorColor: Int = Color.RED

    init {
        theme?.let {
            errorColor = theme.errorColor?: Color.RED
        }
    }

    fun validateET(inputForm: InputFormLength) {
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
    }

    fun validateETWithoutLength(inputForm: InputFormLength) {
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }

//        inputForm.input.setOnFocusChangeListener { _, b ->
//            if (!b)
//                isFormLengthValid(inputForm)
//        }
    }

    fun validateExpirationDate(inputForm: InputFormLength) {
        val currYear = dateInterface.getYearTwoSymbols(System.currentTimeMillis())

        inputForm.input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start == 0 && before == 0)
                    when(s?.getOrNull(0)) {
                        '0', '1' -> {
                            removeErrorFromField(inputForm)
//                            return@let
                        }
                        else -> setError(inputForm)
                    }

                if (start == 1 && before == 0) {
                    when (s?.getOrNull(1)) {
                        '0', '1', '2' -> {
                            removeErrorFromField(inputForm)
                        }
                        else -> setError(inputForm)
                    }
                    if (s.toString().toInt() > 12)
                        setError(inputForm)
                }

                if (start == 4 && before == 0)
                    if (s?.subSequence(3, 5).toString().toInt() < currYear.toInt())
                        setError(inputForm)
                    else if (s?.subSequence(0, 2).toString().toInt() <= 12)
                        removeErrorFromField(inputForm)
//
//                it.getOrNull(3)?.let {
//                    if (it.toInt() < currYear[0].toInt())
//                        setError(inputForm)
//                    else
//                        removeErrorFromField(inputForm)
//                }

//                it.getOrNull(4)?.let { c ->
//                    if (it.contains("y"))
//                        return@let
//                    if (it.subSequence(3, 4).toString().toInt() < currYear.toInt())
//                        setError(inputForm)
//                    else
//                        removeErrorFromField(inputForm)
//                }
            }
        })
//        inputForm.input.addTextChangedListener {
//            removeErrorFromField(inputForm)
//            setError(inputForm)
//            it?.let {
//
//                when(it.getOrNull(0)) {
//                    '0', '1' -> {
//                        removeErrorFromField(inputForm)
//                        return@let
//                    }
//                    else -> setError(inputForm)
//                }
//
//                when(it.getOrNull(1)) {
//                    '1', '2' -> { removeErrorFromField(inputForm) }
//                    else -> setError(inputForm)
//                }
//
//                it.getOrNull(3)?.let {
//                    if (it.toInt() < currYear[0].toInt())
//                        setError(inputForm)
//                    else
//                        removeErrorFromField(inputForm)
//                }
//
//                it.getOrNull(4)?.let { c ->
//                    if (it.contains("y"))
//                        return@let
//                    if (it.subSequence(3, 4).toString().toInt() < currYear.toInt())
//                        setError(inputForm)
//                    else
//                        removeErrorFromField(inputForm)
//                }
//
//
////                when(it.getOrNull(3)) {
////                    '0', '1', '2' -> { removeErrorFromField(inputForm) }
////                    else -> setError(inputForm)
////                }
//
////                if (it.length > 0)
////                    when (it.get(0)) {
////                    '0', '1' -> {}
////                    else -> {
////                        inputForm.input.setTextColor(Color.RED)
////                        inputForm.card.strokeColor = Color.RED
////                    }
////                }
////                else if ( it.length == ) {
//
////            }
//            }
//        }

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
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
        inputForm.input.setTextColor(errorColor)
        inputForm.card.strokeColor = errorColor
    }

    private fun removeErrorFromField(inputForm: InputFormLength) {
        if (inputForm.input.currentTextColor == errorColor) {
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
                    item.input.setTextColor(errorColor)
                    item.card.strokeColor = errorColor
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