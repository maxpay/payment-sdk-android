package com.maxpay.sdk.utils

import android.graphics.Color
import android.text.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.maxpay.sdk.R
import com.maxpay.sdk.model.InputFormLength
import com.maxpay.sdk.model.MaxPayTheme
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.regex.Pattern


class EditTextValidator(val theme: MaxPayTheme?) : KoinComponent {

    private val dateInterface: DateInterface by inject()
    private val isoCountryISOHelper: CountryISOHelper by inject()
    private var errorColor: Int = Color.RED
    internal var isError: Boolean = false
    private val set: MutableSet<InputFormLength> = mutableSetOf()

    init {
        theme?.let {
            errorColor = theme.errorColor?: Color.RED
        }
    }

    internal fun validateET(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
    }

    internal fun validateETWithoutLength(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }
    }

    internal fun validateETEmail(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }
        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b){
                if (!isValidEmailAddress(inputForm.input.text.toString()))
                    setError(inputForm)
            }
        }

    }

    private fun isValidEmailAddress(email: String?): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    internal fun validateETCardHolder(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {

            removeErrorFromField(inputForm)
        }
        inputForm.input.filters = arrayOf(getEditTextFilter())
    }

    fun getEditTextFilter(): InputFilter? {
        return object : InputFilter {
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                var keepOriginal = true
                val sb = StringBuilder(end - start)
                for (i in start until end) {
                    val c = source[i]
                    if (c.isLetter()) // put your condition here
                        sb.append(c) else keepOriginal = false
                }
                if (keepOriginal)
                    return null
                else {
                    if (source is Spanned) {
                        val sp = SpannableString(sb)
                        TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                        return sp
                    } else {
                        return sb
                    }
                }
            }
        }
    }

    internal fun validateETISOCountry(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
        }
        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b){
                if (isoCountryISOHelper.getISOCountries()?.contains(inputForm.input.text.toString()) == false)
                    setError(inputForm)
            }
        }
    }

    internal fun validateExpirationDate(inputForm: InputFormLength) {
        set.add(inputForm)
        val currYear = dateInterface.getYearTwoSymbols(System.currentTimeMillis())
        val currMonth = dateInterface.getMonthTwoSymbols(System.currentTimeMillis())

        inputForm.input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start == 0 && before == 0)
                    when (s?.getOrNull(0)) {
                        '0', '1' -> {
                            removeErrorFromField(inputForm)
                        }
                        else -> setError(inputForm)
                    }

                if (start == 1 && before == 0) {
//                    when (s?.getOrNull(1)) {
//                        '0', '1', '2' -> {
//                            removeErrorFromField(inputForm)
//                        }
//                        else -> setError(inputForm)
//                    }
//                    if (s.toString().toInt() > 12 || s.toString().toInt() < 1)
                    if (s?.subSequence(0, 2).toString().toInt() !in 1..12)
                        setError(inputForm)
//                    if (s?.subSequence(0, 2).toString().toInt() > currMonth)
                }

                if (start == 4 && before == 0)
                    if (s?.subSequence(3, 5).toString().toInt() < currYear.toInt())
                        setError(inputForm)
                    else if (s?.subSequence(3, 5).toString().toInt() == currYear.toInt()
                        && s?.subSequence(0, 2).toString().toInt() !in currMonth.toInt()..12
                    )
                        setError(inputForm)
                    else if (s?.subSequence(0, 2).toString().toInt() in 1..12)
                        removeErrorFromField(inputForm)
            }
        })

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
    }


    internal fun validateCardNumber(inputForm: InputFormLength, imageView: ImageView) {
        set.add(inputForm)
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

//            val luhn = LuhnAlgorithmHelper.checkLuhn(inputForm.input.text.toString().replace(" ", ""))
        }



        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b) {
                isFormLengthValid(inputForm)
                checkLuhn(inputForm)
//                if (!LuhnAlgorithmHelper.checkLuhn(inputForm.input.text.toString().replace(" ", "")))
//                    setError(inputForm)
            }
        }
    }

    internal fun checkLuhn(inputForm: InputFormLength): Boolean {
        if (!LuhnAlgorithmHelper.checkLuhn(inputForm.input.text.toString().replace(" ", ""))) {
            setError(inputForm)
            return false
        }
        return true
    }

    internal fun isErrorInFields(): Boolean {
        set.forEach {
            if (it.input.currentTextColor == errorColor) {
                return true
            }
        }
        return false
    }

    private fun setError(inputForm: InputFormLength) {
        inputForm.input.setTextColor(errorColor)
        inputForm.card.strokeColor = errorColor
        isError = true
    }

    private fun removeErrorFromField(inputForm: InputFormLength) {
        if (inputForm.input.currentTextColor == errorColor) {
            inputForm.input.setTextColor(
                ContextCompat.getColor(inputForm.card.context, R.color.colorDarkText)
            )
            inputForm.card.strokeColor = Color.TRANSPARENT
            isError = false
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