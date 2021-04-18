package com.maxpay.sdk.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.R
import com.maxpay.sdk.core.FragmentWithToolbar
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.model.*
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.model.response.ResponseStatus
import com.maxpay.sdk.utils.*
import com.maxpay.sdk.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.layout_billing_address.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class PaymentFragment: FragmentWithToolbar(R.layout.fragment_payment) {

    private val viewModel: MainViewModel by activityViewModels()
    override fun getCurrentViewModel() = viewModel
    private val dateInterface: DateInterface by inject()
    private val customTabsHelper: CustomTabsHelper by inject()
    private val expiryParser: ExpiryParser by inject()
    private val editTextValidator: EditTextValidator by inject() {
        parametersOf((activity?.intent?.getSerializableExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA) as MaxPayInitData).theme)
    }
    private val themeEditor: UIComponentThemeEditor by inject() {
        parametersOf((activity?.intent?.getSerializableExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA) as MaxPayInitData).theme)
    }
    private lateinit var maxPayInitData: MaxPayInitData
    private lateinit var maxpayPaymentData: MaxpayPaymentData
    private var maxpayTheme: MaxPayTheme? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Constants.MAXPAY_BROAD_SIGNATURE_RES -> {
                    intent.getSerializableExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_SIGNATURE_DATA)
                        ?.let {
                            viewModel.pay(it as String)
                        } ?: kotlin.run {
                            viewModel.sendBroadcastResult(activity, MaxpayResult(MaxpayResultStatus.UNDEF, "UNDEF"))
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.intent?.let {
            maxPayInitData = it.getSerializableExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA) as MaxPayInitData
            viewModel.viewState.maxpayInitData.value = maxPayInitData
            maxpayPaymentData = it.getSerializableExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA) as MaxpayPaymentData

            maxpayTheme =
                (it.getSerializableExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA) as MaxPayInitData).theme
        }
        registerReceiver()
        if (maxpayPaymentData.amount <= 0.0)
            viewModel.sendBroadcastResult(
                activity, MaxpayResult(
                    MaxpayResultStatus.ERROR,
                    resources.getString(R.string.error_zero_price)
                )
            )
        initUIElements()
        initThemeIfNeeded()

        viewModel.run {
            observeCommandSafety(viewState.authPaymentResponse) {
                viewState.isFromWebView.value?: kotlin.run { viewModel.navigateThreeDS() }
            }

            observeCommandSafety(viewState.salePaymentResponse) {
                val status = when(it.status) {
                    ResponseStatus.success -> MaxpayResultStatus.SUCCESS
                    ResponseStatus.decline -> MaxpayResultStatus.REJECTED
                    ResponseStatus.error -> MaxpayResultStatus.ERROR
                }

                viewModel.sendBroadcastResult(activity, MaxpayResult(status, it.message))
            }
        }

        initToolbar()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.MAXPAY_BROAD_SIGNATURE_RES)
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

    private fun initUIElements() {
        initVisibiltyBillingLayout()

        tvFullPrice.text = "${maxpayPaymentData.currency.symbol} ${maxpayPaymentData.amount}"
        payBtn.setText("${resources.getString(R.string.payment_pay_button_title)} ${maxpayPaymentData.amount} ${maxpayPaymentData.currency.symbol}")

        val customTabs = customTabsHelper
            .getTabs(ContextCompat.getColor(requireContext(), R.color.primary_green))
        tvTerms.makeLinks(
            Pair(resources.getString(R.string.terms_key), View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_TERMS))
            }),
            Pair(resources.getString(R.string.privacy_key), View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_PRIVACY))
            }),
            Pair(resources.getString(R.string.maxpay_key), View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_CONTACT))
            }),
            theme = maxpayTheme
        )

        editTextValidator.validateETWithoutLength(InputFormLength(etEmail, cvEmail, 0))
        editTextValidator.validateCardNumber(
            InputFormLength(etCardNumber, cvCardNumber, Constants.Companion.RequiredLength.CARD_INPUT_LENGTH), ivCard)
        editTextValidator.validateET(InputFormLength(etCvv, cvCvv, Constants.Companion.RequiredLength.CVV_INPUT_LENGTH))
        editTextValidator.validateExpirationDate(InputFormLength(etExpirationDate, cvExpirDate, Constants.Companion.RequiredLength.EXPIRY_INPUT_LENGTH))
        editTextValidator.validateETISOCountry(InputFormLength(etCountry, cvCountry, 3))
        editTextValidator.validateETCardHolder(InputFormLength(etCardHolderName, cvCardHolderName, 0))

        etCountry.addTextChangedListener{
            if(etCountry.currentTextColor == Color.RED) {
                etCountry.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorDarkText
                    )
                )
                cvCountry.strokeColor = Color.TRANSPARENT
            }

        }
        etCountry?.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(3))
        payBtn.setOnClickListener {
            val isAuthTransaction = (maxpayPaymentData.transactionType == TransactionType.AUTH
                    || maxpayPaymentData.transactionType == TransactionType.AUTH3D)
            val showNameFields = (maxpayPaymentData.firstName.isNullOrEmpty() && isAuthTransaction)
            val showCountryFields = (maxpayPaymentData.country.isNullOrEmpty() && isAuthTransaction)
            if (isFormCompleted(InputFormLength(etEmail, cvEmail, 0),
                    InputFormLength(etCardHolderName, cvCardHolderName, 0))
                and
                isFormLengthValid(InputFormLength(etCardNumber, cvCardNumber, Constants.Companion.RequiredLength.CARD_INPUT_LENGTH),
                    InputFormLength(etExpirationDate, cvExpirDate, Constants.Companion.RequiredLength.EXPIRY_INPUT_LENGTH),
                    InputFormLength(etCvv, cvCvv, Constants.Companion.RequiredLength.CVV_INPUT_LENGTH))
                and
                editTextValidator.checkLuhn(InputFormLength(etCardNumber, cvCardNumber, Constants.Companion.RequiredLength.CARD_INPUT_LENGTH))
//                and
//                (showCountryFields
//                        || isFormLengthValid(InputFormLength(etCountry, cvCountry, Constants.Companion.RequiredLength.COUNTRY_INPUT_LENGTH)))
            )
            if (checkBoxAutoDebt.isChecked && checkBoxTermsOfUse.isChecked) {
                maxpayPaymentData.userEmail = etEmail.text.toString()
                val cardNumber = etCardNumber.text.toString().replace(" ", "")
                val expMonth = expiryParser.getMonth(etExpirationDate.text.toString())
                val expYear = expiryParser.getYear(etExpirationDate.text.toString())
                val cvv = etCvv.text.toString()
                maxpayPaymentData.country = etCountry.text.toString()
                maxpayPaymentData.city = etCity.text.toString().takeIf { !it.isEmpty() }?: null
                maxpayPaymentData.zip = etZip.text.toString().takeIf { !it.isEmpty() }?: null
                val cardHolder = etCardHolderName.text.toString()
                maxpayPaymentData.firstName = etName.text.toString().takeIf { !it.isEmpty() }?: null
                maxpayPaymentData.lastName = etLastName.text.toString().takeIf { !it.isEmpty() }?: null
                viewModel.prepareForPayment(activity,
                    paymentData = maxpayPaymentData,
                    cardHolder = cardHolder,
                    cardNumber = cardNumber,
                    cvv = cvv,
                    expMonth = expMonth,
                    expYear = expYear)
            }
        }

        etCountry?.setText(maxpayPaymentData.country)
        etCity?.setText(maxpayPaymentData.city)
        etZip?.setText(maxpayPaymentData.zip)
        etAddr?.setText(maxpayPaymentData.address)
        if (!maxpayPaymentData.firstName.isNullOrEmpty())
            etName.setText(maxpayPaymentData.firstName)
        if (!maxpayPaymentData.lastName.isNullOrEmpty())
            etLastName.setText(maxpayPaymentData.lastName)
        checkBoxAutoDebt.setOnClickListener { checkEnableButton() }

        checkBoxTermsOfUse.setOnClickListener { checkEnableButton() }

    }

    private fun initVisibiltyBillingLayout() {
        val isAuthTransaction = (maxpayPaymentData.transactionType == TransactionType.AUTH
                || maxpayPaymentData.transactionType == TransactionType.AUTH3D)
        val showNameFields = (maxpayPaymentData.firstName.isNullOrEmpty() && isAuthTransaction)
        val showCountryFields = (maxpayPaymentData.country.isNullOrEmpty() && isAuthTransaction)
        if (maxPayInitData.fieldsToShow?.showBillingAddressLayout == true || showCountryFields || showCountryFields) layoutBillingAddress.visibility =
            View.VISIBLE
        else {
            layoutBillingAddress.visibility = View.GONE
            return
        }
        if (maxPayInitData.fieldsToShow?.showNameField == true || showNameFields) {
            tvName.visibility = View.VISIBLE
            cvName.visibility = View.VISIBLE
            tvLastName.visibility = View.VISIBLE
            cvLastName.visibility = View.VISIBLE
        }

        if (maxPayInitData.fieldsToShow?.showAddressField == true) {
            tvAddr.visibility = View.VISIBLE
            cvAddress.visibility = View.VISIBLE
        }
        if (maxPayInitData.fieldsToShow?.showCityField == true) {
            tvCity.visibility = View.VISIBLE
            cvCity.visibility = View.VISIBLE
        }
        if (maxPayInitData.fieldsToShow?.showZipField == true) {
            tvZIP.visibility = View.VISIBLE
            cvZip.visibility = View.VISIBLE
            if (maxPayInitData.fieldsToShow?.showCityField == null || maxPayInitData.fieldsToShow?.showCityField == false) {
                tvCity.visibility = View.INVISIBLE
                cvCity.visibility = View.INVISIBLE
            }
        }
        if (maxPayInitData.fieldsToShow?.showCountryField == true || showCountryFields) {
            tvCountry.visibility = View.VISIBLE
            cvCountry.visibility = View.VISIBLE
        }
    }

    private fun checkEnableButton() {
        val isEnabled = checkBoxTermsOfUse.isChecked && checkBoxAutoDebt.isChecked
        payBtn.isEnabled = isEnabled
        themeEditor.changeButtonColorFilter(view, isEnabled)
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.payment_title)
        maxpayTheme?.navigationBarColor?.let {
            toolbar.setBackgroundColor(it)
        }
        maxpayTheme?.navigationBarTitleColor?.let {
            toolbar.setTitleTextColor(it)
        }
        maxpayTheme?.navigationBarTitleColor?.let {
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