package com.maxpay.sdk.payment.ui

import android.app.DatePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.payment.R
import com.maxpay.sdk.payment.core.FragmentWithToolbar
import com.maxpay.sdk.payment.data.PayResult
import com.maxpay.sdk.payment.data.PayResultStatus
import com.maxpay.sdk.payment.model.*
import com.maxpay.sdk.payment.model.response.ResponseStatus
import com.maxpay.sdk.payment.utils.*
import com.maxpay.sdk.payment.utils.extensions.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.layout_billing_address.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

internal class PaymentFragment: FragmentWithToolbar(R.layout.fragment_payment) {

    private val viewModel: MainViewModel by activityViewModels()
    override fun getCurrentViewModel() = viewModel
    private val customTabsHelper: CustomTabsHelper by inject()
    private val expiryParser: ExpiryParser by inject()
    private val editTextValidator: EditTextValidator by inject {
        parametersOf((activity?.intent?.getSerializableExtra(Constants.Companion.Extra.PAY_INIT_DATA) as PayInitInfo).theme)
    }
    private val themeEditor: UIComponentThemeEditor by inject {
        parametersOf((activity?.intent?.getSerializableExtra(Constants.Companion.Extra.PAY_INIT_DATA) as PayInitInfo).theme)
    }
    private lateinit var payInitInfo: PayInitInfo
    private lateinit var payPaymentInfo: PayPaymentInfo
    val uiDispose = CompositeDisposable()
    private var payTheme: PayTheme? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Constants.PAY_BROAD_SIGNATURE_RES -> {
                    intent.getStringExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA)
                        ?.let {
                            viewModel.pay(it)
                        } ?: kotlin.run {
                        viewModel.sendBroadcastResult(
                            activity, PayResult(
                                PayResultStatus.UNDEF,
                                "UNDEF"
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.intent?.let {
            payInitInfo = it.getSerializableExtra(Constants.Companion.Extra.PAY_INIT_DATA) as PayInitInfo
            viewModel.viewState.payInitData.value = payInitInfo
            payPaymentInfo = it.getSerializableExtra(Constants.Companion.Extra.PAY_PAYMENT_DATA) as PayPaymentInfo
            viewModel.viewState.payPaymentInfo.value = payPaymentInfo

            payTheme =
                (it.getSerializableExtra(Constants.Companion.Extra.PAY_INIT_DATA) as PayInitInfo).theme
        }
        registerReceiver()

        initUIElements()
        initThemeIfNeeded()

        viewModel.run {
            observeCommandSafety(viewState.authPaymentResponse) {
                viewState.isFromWebView.value?: kotlin.run { viewModel.navigateThreeDS() }
            }

            observeCommandSafety(viewState.salePaymentResponse) {
                val status = when(it.status) {
                    ResponseStatus.success -> PayResultStatus.SUCCESS
                    ResponseStatus.decline -> PayResultStatus.REJECTED
                    ResponseStatus.error -> PayResultStatus.ERROR
                }

                viewModel.sendBroadcastResult(activity, PayResult(status, it.message))
            }
        }

        initToolbar()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.PAY_BROAD_SIGNATURE_RES)
        context?.registerReceiver(mReceiver, intentFilter)
    }

    private fun initThemeIfNeeded() {
        themeEditor.setInputStyle(view)
        themeEditor.setMainPageStyle(view)
        themeEditor.changeButtonColorFilter(view, false)
        themeEditor.changeProgressBar(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(mReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDispose.clear()
    }

    private fun initUIElements() {
        initVisibiltyBillingLayout()

        tvFullPrice.text = "${payPaymentInfo.currency.symbol} ${payPaymentInfo.amount}"
        payBtn.setText("${resources.getString(R.string.payment_pay_button_title)} ${payPaymentInfo.amount} ${payPaymentInfo.currency.symbol}")

        val customTabs = customTabsHelper
            .getTabs(ContextCompat.getColor(requireContext(), R.color.primary_green))
        tvTerms.makeLinks(
            Pair(resources.getString(R.string.terms_key), View.OnClickListener {
                customTabs.launchUrl(
                    requireContext(),
                    Uri.parse(getString(R.string.terms_url))
                )
            }),
            Pair(resources.getString(R.string.privacy_key), View.OnClickListener {
                customTabs.launchUrl(
                    requireContext(),
                    Uri.parse(getString(R.string.privacy_url))
                )
            }),
            Pair(resources.getString(R.string.maxpay_key), View.OnClickListener {
                customTabs.launchUrl(
                    requireContext(),
                    Uri.parse(getString(R.string.contact_url))
                )
            }),
            theme = payTheme
        )

        editTextValidator.validateETEmail(InputFormLength(etEmail, cvEmail, 0))
        editTextValidator.validateCardNumber(
            InputFormLength(
                etCardNumber,
                cvCardNumber,
                Constants.Companion.RequiredLength.CARD_INPUT_LENGTH
            ), ivCard
        )
        editTextValidator.validateET(
            InputFormLength(
                etCvv,
                cvCvv,
                Constants.Companion.RequiredLength.CVV_INPUT_LENGTH
            )
        )
        editTextValidator.validateExpirationDate(
            InputFormLength(
                etExpirationDate,
                cvExpirDate,
                Constants.Companion.RequiredLength.EXPIRY_INPUT_LENGTH
            )
        )
        if (cvCountry.visibility == View.VISIBLE)
            editTextValidator.validateETISOCountry(InputFormLength(etCountry, cvCountry, 3))
        if (cvName.visibility == View.VISIBLE)
            editTextValidator.validateET(InputFormLength(etName, cvName, 1))
        if (cvLastName.visibility == View.VISIBLE)
            editTextValidator.validateET(InputFormLength(etLastName, cvLastName, 1))

        editTextValidator.validateETCardHolder(
            InputFormLength(
                etCardHolderName,
                cvCardHolderName,
                Constants.Companion.RequiredLength.CARDHOLDER_INPUT_LENGTH
            )
        )
        editTextValidator.errorObservable
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe {
                             val isEnabled = !it && checkBoxTermsOfUse.isChecked
                             payBtn?.isEnabled = isEnabled
                             themeEditor.changeButtonColorFilter(view, isEnabled)
                        }.addTo(uiDispose)
        etCountry?.setText(payPaymentInfo.country)
        etCity?.setText(payPaymentInfo.city)
        etZip?.setText(payPaymentInfo.zip)
        etAddr?.setText(payPaymentInfo.address)
        if (!payPaymentInfo.firstName.isNullOrEmpty())
            etName.setText(payPaymentInfo.firstName)
        if (!payPaymentInfo.lastName.isNullOrEmpty())
            etLastName.setText(payPaymentInfo.lastName)

        etCountry?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(3))
        payBtn.setOnClickListener {
            if (isFormCompleted(
                    InputFormLength(etEmail, cvEmail, 0),
                    InputFormLength(etCardHolderName, cvCardHolderName, 0)
                )
                and
                isFormLengthValid(
                    InputFormLength(
                        etCardNumber,
                        cvCardNumber,
                        Constants.Companion.RequiredLength.CARD_INPUT_LENGTH
                    ),
                    InputFormLength(
                        etExpirationDate,
                        cvExpirDate,
                        Constants.Companion.RequiredLength.EXPIRY_INPUT_LENGTH
                    ),
                    InputFormLength(
                        etCvv,
                        cvCvv,
                        Constants.Companion.RequiredLength.CVV_INPUT_LENGTH
                    )
                )
                and
                editTextValidator.checkLuhn(
                    InputFormLength(
                        etCardNumber,
                        cvCardNumber,
                        Constants.Companion.RequiredLength.CARD_INPUT_LENGTH
                    )
                )
//                and //TODO validation for country
//                (showCountryFields
//                        || isFormLengthValid(InputFormLength(etCountry, cvCountry, Constants.Companion.RequiredLength.COUNTRY_INPUT_LENGTH)))
            )
            if (checkBoxTermsOfUse.isChecked) {
                payPaymentInfo.userEmail = etEmail.text.toString()
                val cardNumber = etCardNumber.text.toString().replace(" ", "")
                val expMonth = expiryParser.getMonth(etExpirationDate.text.toString())
                val expYear = expiryParser.getYear(etExpirationDate.text.toString())
                val cvv = etCvv.text.toString()
                payPaymentInfo.country = etCountry.text.toString()
                payPaymentInfo.city = etCity.text.toString().takeIf { !it.isEmpty() }?: null
                payPaymentInfo.zip = etZip.text.toString().takeIf { !it.isEmpty() }?: null
                val cardHolder = etCardHolderName.text.toString()
                payPaymentInfo.firstName = etName.text.toString().takeIf { !it.isEmpty() }?: null
                payPaymentInfo.lastName = etLastName.text.toString().takeIf { !it.isEmpty() }?: null
                if (!etBirthday.text.toString().isNullOrEmpty())
                    payPaymentInfo.birthday = etBirthday.text.toString()
                viewModel.prepareForPayment(
                    activity,
                    paymentInfo = payPaymentInfo,
                    cardHolder = cardHolder,
                    cardNumber = cardNumber,
                    cvv = cvv,
                    expMonth = expMonth,
                    expYear = expYear
                )
            }
        }

        checkBoxTermsOfUse.setOnClickListener { checkEnableButton() }
        etBirthday.setOnClickListener {
            openDatePicker(DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val c = Calendar.getInstance()
                c[Calendar.YEAR] = year
                c[Calendar.MONTH] = monthOfYear
                c[Calendar.DAY_OF_MONTH] = dayOfMonth
                etBirthday.setText( SimpleDateFormat("YYYY-MM-dd").format(c.time))
            })
        }
    }

    private fun openDatePicker(callback: DatePickerDialog.OnDateSetListener) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireContext(),
            callback,
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = c.timeInMillis
        dpd.show()
    }

    private fun createDatePickerDialog(callback: MaterialPickerOnPositiveButtonClickListener<Long>) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
        datePicker.addOnPositiveButtonClickListener(callback)
        datePicker.show(requireFragmentManager(), null)
    }

    private fun initVisibiltyBillingLayout() {
        val showNameFields = (payPaymentInfo.firstName.isNullOrEmpty())
        val showCountryFields = (payPaymentInfo.country.isNullOrEmpty())

        if (payInitInfo.fieldsToShow?.showBirthdayField == true) {
            tvBDAY.visibility = View.VISIBLE
            cvBirthday.visibility = View.VISIBLE
        }
        if (payInitInfo.fieldsToShow?.showBillingAddressLayout == true || showCountryFields || showNameFields)
            layoutBillingAddress.visibility = View.VISIBLE
        else {
            layoutBillingAddress.visibility = View.GONE
            return
        }
        if (payInitInfo.fieldsToShow?.showNameField == true || showNameFields) {
            tvName.visibility = View.VISIBLE
            cvName.visibility = View.VISIBLE
            tvLastName.visibility = View.VISIBLE
            cvLastName.visibility = View.VISIBLE
        }

        if (payInitInfo.fieldsToShow?.showAddressField == true) {
            tvAddr.visibility = View.VISIBLE
            cvAddress.visibility = View.VISIBLE
        }
        if (payInitInfo.fieldsToShow?.showCityField == true) {
            tvCity.visibility = View.VISIBLE
            cvCity.visibility = View.VISIBLE
        }
        if (payInitInfo.fieldsToShow?.showZipField == true) {
            tvZIP.visibility = View.VISIBLE
            cvZip.visibility = View.VISIBLE
            if (payInitInfo.fieldsToShow?.showCityField == null || payInitInfo.fieldsToShow?.showCityField == false) {
                tvCity.visibility = View.INVISIBLE
                cvCity.visibility = View.INVISIBLE
            }
        }
        if (payInitInfo.fieldsToShow?.showCountryField == true || showCountryFields) {
            tvCountry.visibility = View.VISIBLE
            cvCountry.visibility = View.VISIBLE
        }
    }

    private fun checkEnableButton() {
        val isEnabled = checkBoxTermsOfUse.isChecked
        if (isEnabled)
            editTextValidator.checkEnableButton()
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.payment_title)
        payTheme?.navigationBarColor?.let {
            toolbar.setBackgroundColor(it)
        }
        payTheme?.navigationBarTitleColor?.let {
            toolbar.setTitleTextColor(it)
        }
        payTheme?.navigationBarTitleColor?.let {
            toolbar.navigationIcon?.setColorFilter(it, PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance() = PaymentFragment()
    }
}