package com.maxpay.sdk.utils

import android.graphics.Color
import android.text.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.maxpay.sdk.R
import com.maxpay.sdk.model.InputFormLength
import com.maxpay.sdk.model.PayTheme
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.regex.Pattern

class EditTextValidator(val theme: PayTheme?) : KoinComponent {

    private val dateInterface: DateInterface by inject()
    private val isoCountryISOHelper: CountryISOHelper by inject()
    private var errorColor: Int = Color.RED
    private var isError: Boolean = false
    private val set: MutableSet<InputFormLength> = mutableSetOf()

    private val subject: ReplaySubject<Boolean> = ReplaySubject.create(1)

    internal val errorObservable: Observable<Boolean>
        get() = subject.subscribeOn(Schedulers.io())

    private fun postIsError(msg: Boolean) = subject.onNext(msg)

    init {
        theme?.let {
            errorColor = theme.errorColor?: Color.RED
        }
    }

    internal fun validateET(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
            it?.let {
                val validText = it.toString().replace(" ", "")
                    .replace("_", "")
                    .replace("/", "")
                if (validText.length < inputForm.requiredLength) {
                    if (set.elementAt(set.indexOf(inputForm)).isValid) {
                        set.elementAt(set.indexOf(inputForm)).isValid = false
                        checkEnableButton()
                    }
                } else {
                    set.elementAt(set.indexOf(inputForm)).isValid = true
                    checkEnableButton()
                }
            } ?: kotlin.run {
                set.elementAt(set.indexOf(inputForm)).isValid = false
            }
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
        inputForm.input.addTextChangedListener(
            beforeTextChanged = { _, _, _, _ -> },
            onTextChanged = { text, start, before, count ->
                removeErrorFromField(inputForm)
                if (isValidEmailAddress(text.toString())) {
                    inputForm.isValid = true
                    checkEnableButton()
                } else {
                    inputForm.isValid = false
                    checkEnableButton()
                }
            },
            afterTextChanged = { text ->
                if (isValidEmailAddress(text.toString())) {
                    inputForm.isValid = true
                    checkEnableButton()
                } else {
                    inputForm.isValid = false
                    checkEnableButton()
                }
            }
        )
//        inputForm.input.addTextChangedListener {
//            removeErrorFromField(inputForm)
//            if (isValidEmailAddress(it.toString())) {
//                inputForm.isValid = true
//                checkEnableButton()
//            } else {
//                inputForm.isValid = false
//                checkEnableButton()
//            }
//        }
        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b){
                if (!isValidEmailAddress(inputForm.input.text.toString()))
                    setError(inputForm)
            }
        }

    }

    private fun isValidEmailAddress(email: String?): Boolean {
        val ePattern =
            "^[a-zA-Z0-9ÁÀȦÂÄǞǍĂĀÃÅǺǼǢĆĊĈČĎḌḐḒÉÈĖÊËĚĔĒẼE̊ẸǴĠĜǦĞG̃ĢĤḤáàȧâäǟǎăāãåǻǽǣćċĉčďḍḑḓéèėêëěĕēẽe̊ẹǵġĝǧğg̃ģĥḥÍÌİÎÏǏĬĪĨỊĴĶǨĹĻĽĿḼM̂M̄ʼNŃN̂ṄN̈ŇN̄ÑŅṊÓÒȮȰÔÖȪǑŎŌÕȬŐỌǾƠíìiîïǐĭīĩịĵķǩĺļľŀḽm̂m̄ŉńn̂ṅn̈ňn̄ñņṋóòôȯȱöȫǒŏōõȭőọǿơP̄ŔŘŖŚŜṠŠȘṢŤȚṬṰÚÙÛÜǓŬŪŨŰŮỤẂẀŴẄÝỲŶŸȲỸŹŻŽẒǮp̄ŕřŗśŝṡšşṣťțṭṱúùûüǔŭūũűůụẃẁŵẅýỳŷÿȳỹźżžẓǯßœŒçÇ.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"

        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    internal fun validateETCardHolder(inputForm: InputFormLength) {
        set.add(inputForm)
        inputForm.isValid = true
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
            if (inputForm.input.text.length < inputForm.requiredLength ) {
                set.elementAt(set.indexOf(inputForm)).isValid = false
                checkEnableButton()
            }
            else {
                set.elementAt(set.indexOf(inputForm)).isValid = true
                checkEnableButton()
            }
        }
        inputForm.input.filters = arrayOf(getEditTextFilter())

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (inputForm.input.length() > 0)
                    inputForm.input.setText(inputForm.input.text.trimEnd().toString())


            if (!b)
                isFormLengthValid(inputForm)
        }
    }

    fun getEditTextFilter(): InputFilter {
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
                    if (c.isLetter() || c == ' ' ) // put your condition here
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
        val lstOfCountries = isoCountryISOHelper.getISOCountries()
        if (inputForm.input.text.length > 0 && lstOfCountries?.contains(inputForm.input.text.toString()) == false)
            setError(inputForm)
        inputForm.input.addTextChangedListener {
            removeErrorFromField(inputForm)
            if (lstOfCountries?.contains(it.toString()) == false) {
                set.elementAt(set.indexOf(inputForm)).isValid = false
                checkEnableButton()
            }
            else {
                set.elementAt(set.indexOf(inputForm)).isValid = true
                checkEnableButton()
            }
        }
        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b){
                if (lstOfCountries?.contains(inputForm.input.text.toString()) == false)
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

                if (inputForm.input.currentTextColor == errorColor || inputForm.input.text.toString()
                        .replace(" ", "")
                        .replace("_", "")
                        .replace("/", "").length != inputForm.requiredLength
                ) {
                    set.elementAt(set.indexOf(inputForm)).isValid = false
                    checkEnableButton()
                } else {
                    set.elementAt(set.indexOf(inputForm)).isValid = true
                    checkEnableButton()
                }
            }
        })

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b)
                isFormLengthValid(inputForm)
        }
    }

    internal fun validateCardNumber(inputForm: InputFormLength, imageView: ImageView) {
        set.add(inputForm)
        val SUPPORTED_CARDS : MutableList<CardType> =  mutableListOf(
            CardType.Visa,
            CardType.MasterCard,
            CardType.AmericanExpress,
            CardType.DinersClub,
            CardType.Discover,
            CardType.Jcb,
            CardType.Maestro)



        inputForm.input.addTextChangedListener(
            beforeTextChanged = { _, _, _, _ -> },
            onTextChanged = { text, start, before, count ->
                removeErrorFromField(inputForm)

                text?.let {
                    if (it.length == 0)
                        imageView.setImageDrawable(ContextCompat.getDrawable(
                            inputForm.card.context,
                            R.drawable.ic_credit_card
                        ))
                    if (it.length == 1) {
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
                    if (it.length > 1) {
                        val str = it.toString().replace(" ", "")
                        var img = ContextCompat.getDrawable(inputForm.card.context, R.drawable.ic_credit_card )
//                        SUPPORTED_CARDS.forEach {  supportedType ->
                        for (supportedType in SUPPORTED_CARDS) {
                                if (str.matches(supportedType.pattern.toRegex())) {
                                    img = when (supportedType) {
                                        CardType.Visa -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_visa_logo
                                        )
                                        CardType.MasterCard -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_logo_mastercard
                                        )
                                        CardType.AmericanExpress -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_american_express
                                        )
                                        CardType.Discover -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_discover
                                        )
                                        CardType.DinersClub -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_dinners_club
                                        )
                                        CardType.Maestro -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_maestro_logo
                                        )
                                        else -> ContextCompat.getDrawable(
                                            inputForm.card.context,
                                            R.drawable.ic_credit_card
                                        )
                                    }
                                    break
                                }  else {
                                    img = ContextCompat.getDrawable(inputForm.card.context, R.drawable.ic_credit_card )
                                }
                            }
                        imageView.setImageDrawable(img)
                    }
                }
            },
            afterTextChanged = { text ->
                if (!LuhnAlgorithmHelper.checkLuhn(
                        inputForm.input.text.toString().replace(" ", "")
                    )
                ) {
                    set.elementAt(set.indexOf(inputForm)).isValid = false
                    checkEnableButton()
                } else {
                    set.elementAt(set.indexOf(inputForm)).isValid = true
                    checkEnableButton()
                }
            }


        )

        inputForm.input.setOnFocusChangeListener { _, b ->
            if (!b) {
                isFormLengthValid(inputForm)
                checkLuhn(inputForm)
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

    private fun isErrorInFields(): Boolean {
        set.forEach {
            if (it.input.currentTextColor == errorColor) {
                return true
            }
        }
        return false
    }


    internal fun checkEnableButton() {
        set.forEach {
            if (!it.isValid) {
                postIsError(true)
                return
            }
        }
        postIsError(false)
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
                    isValidate = false
                } else {
                    if (isValidate != false)
                        isValidate = true
                    item.input.error = null
                }
            } ?: kotlin.run {
                isValidate = false
            }
        }
        return isValidate ?: true
    }
}

sealed class CardType(val pattern: String) {
    object Visa : CardType("^4[0-9]{1,}\$")
    object MasterCard : CardType("^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}\$")
    object AmericanExpress : CardType("^3[47][0-9]{5,}\$")
    object DinersClub : CardType("^3(?:0[0-5]|[68][0-9])[0-9]{4,}\$")
    object Discover : CardType("^65[4-9][0-9]{13}|64[4-9][0-9]{3,}|6011[0-9]{3,}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{3,})$")
//    object Discover : CardType("^6(?:011|5[0-9]{2}|644||645|646|647|648|649)[0-9]{3,}\$")
    object Jcb : CardType("^(?:2131|1800|35[0-9]{3})[0-9]{3,}\$")
    object Maestro : CardType("^(5893|5018|5081|5044|5020|5038|603845|6304|6759|676|6771|6799|6220|504834|504817|504645)[0-9]{1,}\$")
//    object Maestro : CardType1( "^(5018|5020|5038|5893|6304|6759|6761|6762|6763|6799)[0-9]{4,15}$")

}