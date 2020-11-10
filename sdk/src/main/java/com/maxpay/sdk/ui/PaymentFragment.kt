package com.maxpay.sdk.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.R
import com.maxpay.sdk.core.FragmentWithToolbar
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.model.InputFormLength
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.response.ResponseStatus
import com.maxpay.sdk.utils.*
import com.maxpay.sdk.utils.extensions.isFormLengthValid
import com.maxpay.sdk.utils.extensions.makeLinks
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import com.maxpay.sdk.utils.extensions.showInfo
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.layout_billing_address.*
import org.koin.android.ext.android.inject


class PaymentFragment: FragmentWithToolbar(R.layout.fragment_payment) {

    private val viewModel: MainViewModel by activityViewModels()
    override fun getCurrentViewModel() = viewModel
    private val dateInterface: DateInterface by inject()
    private val customTabsHelper: CustomTabsHelper by inject()
    private val expiryParser: ExpiryParser by inject()
    private val editTextValidator: EditTextValidator by inject()
    private lateinit var maxPayInitData: MaxPayInitData
    private lateinit var maxpayPaymentData: MaxpayPaymentData
//    private val args: PaymentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.intent?.let {
            maxPayInitData = it.getSerializableExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA) as MaxPayInitData
            viewModel.viewState.maxpayInitData.value = maxPayInitData
            maxpayPaymentData = it.getSerializableExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA) as MaxpayPaymentData
        }
        initUIElements()

        viewModel.run {
            observeCommandSafety(viewState.authPaymentResponse) {
                viewState.isFromWebView.value?: kotlin.run { findNavController().navigate(R.id.action_firstFragment_to_threeDSFragment) }
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

    private fun initUIElements() {
        tvFullPrice.text = "${maxpayPaymentData.currency.symbol} ${maxpayPaymentData.amount}"

        val customTabs = customTabsHelper
            .getTabs(ContextCompat.getColor(requireContext(), R.color.primary_green))
        tvTerms.makeLinks(
            Pair("Terms of Use", View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_TERMS))
            }),
            Pair("Privacy Policy", View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_PRIVACY))
            }),
            Pair("Maxpay", View.OnClickListener {
                customTabs.launchUrl(requireContext(), Uri.parse(Constants.Companion.Links.MAXPAY_CONTACT))
            })
        )
        maxpayPaymentData.currency.currencyCode


        editTextValidator.validateCardNumber(
            InputFormLength(etCardNumber, cvCardNumber, Constants.Companion.RequiredLength.CARD_INPUT_LENGTH), ivCard)

        editTextValidator.validateET(InputFormLength(etCvv, cvCvv, Constants.Companion.RequiredLength.CVV_INPUT_LENGTH))

        editTextValidator.validateExpirationDate(InputFormLength(etExpirationDate, cvExpirDate, Constants.Companion.RequiredLength.EXPIRY_INPUT_LENGTH))

//        etCardNumber.addTextChangedListener {
//            val img: Drawable?
//            when (it?.get(0)) {
//                '4' -> img = ContextCompat.getDrawable(requireContext(), R.drawable.ic_visa_logo)
//                '5' -> img = ContextCompat.getDrawable(
//                    requireContext(),
//                    R.drawable.ic_logo_mastercard
//                )
//                else -> img = ContextCompat.getDrawable(requireContext(), R.drawable.ic_credit_card)
//            }
//            img?.let { ivCard.setImageDrawable(it) }
//        }
//
//        etCardNumber.setOnFocusChangeListener{_, b ->
//            if (!b && etCardNumber.text!!.length > 0)
//                cvCardNumber.strokeColor = Color.RED
//        }


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
        etCountry.filters = arrayOf(InputFilter.AllCaps(), InputFilter.LengthFilter(3))
        payBtn.setOnClickListener {
//            viewModel.sendBroadcastResult(activity, null)
            if (isFormLengthValid(InputFormLength(etCountry, cvCountry, Constants.Companion.RequiredLength.COUNTRY_INPUT_LENGTH) ))

            if (checkBoxAutoDebt.isChecked && checkBoxTermsOfUse.isChecked) {
                maxpayPaymentData.userEmail = etEmail.text.toString()
                maxpayPaymentData.cardNumber = etCardNumber.text.toString().replace(" ", "")
                maxpayPaymentData.expMonth = expiryParser.getMonth(etExpirationDate.text.toString())
                maxpayPaymentData.expYear = expiryParser.getYear(etExpirationDate.text.toString())
                maxpayPaymentData.country = etCountry.text.toString()
                maxpayPaymentData.city = etCity.text.toString()
                maxpayPaymentData.zip = etZip.text.toString()
                maxpayPaymentData.cardHolder = etCardHolderName.text.toString()
                viewModel.payAuth3D(maxpayPaymentData)
            }
        }
        etCountry.setText(maxpayPaymentData.country)
        etCity.setText(maxpayPaymentData.city)
        etZip.setText(maxpayPaymentData.zip)
        etAddr.setText(maxpayPaymentData.address)
        if (!maxpayPaymentData.firstName.isNullOrEmpty() || !maxpayPaymentData.lastName.isNullOrEmpty())
            etName.setText("${maxpayPaymentData.firstName} ${maxpayPaymentData.lastName}")
        checkBoxAutoDebt.setOnClickListener {
            payBtn.isEnabled = checkBoxTermsOfUse.isChecked && checkBoxAutoDebt.isChecked
        }

        checkBoxTermsOfUse.setOnClickListener {
            payBtn.isEnabled = checkBoxTermsOfUse.isChecked && checkBoxAutoDebt.isChecked
        }

    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.payment_title)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}

//val adapter = ArrayAdapter<String>(requireContext(),
//    android.R.layout.simple_dropdown_item_1line, arrayListOf("Belgium", "France", "Italy", "Germany", "Spain", "Belarus"));
//        etAu.setAdapter(adapter);

//val authPayment = SalePayment(
//    1,
//    maxPayInitData.accountName,
//    maxPayInitData.accountPassword,
//    "AUTH3В${dateInterface.getCurrentTimeStamp()}",
//    TransactionType.AUTH3D,
//    9F,
//    "EUR",
//    "4012000300001003",
//    "05",
//    "2019",
//    "111",
//    "John",
//    "Doe",
//    "John Doe",
//    "123 Street name.",
//    "City Name",
//    "NY",
//    "12100",
//    "USA",
//    "+12025550000",
//    "johndoe@test.com",
//    "193.34.96.96"
//)

//val salePayment = SalePayment(
//    1,
//    "Dinarys",
//    "h6Zq7dLPYMcve1F2",
//    "sale_requesttimestamp",
////                "sale_request{{$timestamp}}",// TODO
//    TransactionType.SALE,
//    11F,
//    "USD",
//    "4012001038443335",
//    "04",
//    "2021",
//    "548",
//    "John",
//    "Doe",
//    "John Doe",
//    "123 Street name.",
//    "New York",
//    "NY",
//    "12100",
//    "USA",
//    "+12025550000",
//    "johndoe@test.com",
//    "193.34.96.96"
//)