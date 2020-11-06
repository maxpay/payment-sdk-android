package com.maxpay.sdk.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.R
import com.maxpay.sdk.core.FragmentWithToolbar
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.utils.Constants
import com.maxpay.sdk.utils.DateInterface
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import kotlinx.android.synthetic.main.fragment_payment.*
import org.koin.android.ext.android.inject
import java.util.*

class PaymentFragment: FragmentWithToolbar(R.layout.fragment_payment) {

    private val viewModel: MainViewModel by activityViewModels()
    override fun getCurrentViewModel() = viewModel
    private val dateInterface: DateInterface by inject()
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
//        ipPlaying()
        val adapter = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_dropdown_item_1line, arrayListOf("Belgium", "France", "Italy", "Germany", "Spain", "Belarus"));
//        etAu.setAdapter(adapter);



        viewModel.run {
            observeCommandSafety(viewState.authPaymentResponse) {
//                val i = Intent(context, MaxPayActivity::class.java).apply {
//                    putExtra(Constants.Companion.Extra.RETURN_URL, it.accessUrl)
//                    putExtra(Constants.Companion.Extra.MAXPAY_PARAQ, URLEncoder.encode(it.pareq))
//                    putExtra(Constants.Companion.Extra.MAXPAY_MD, URLEncoder.encode(it.reference))
//                    putExtra(Constants.Companion.Extra.MAXPAY_TERM_URL, URLEncoder.encode("https://google.com"))
//                }
//                startActivity(i)

                viewState.isFromWebView.value?: kotlin.run { findNavController().navigate(R.id.action_firstFragment_to_threeDSFragment) }

            }
        }

        initToolbar()

        val some = Currency.getAvailableCurrencies()
        val s = some.elementAt(0)
        payBtn.setOnClickListener {
//            if (isFormLengthValid(InputFormLength(ilCardNumber, Constants.Companion.RequiredLength.CARD_INPUT_LENGTH) ))
                viewModel.payAuth3D(maxpayPaymentData)
        }
    }

    private fun ipPlaying() {

    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = "Payment"
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
//        toolbar.inflateMenu(R.menu.main_menu)
//        toolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.menuBurger -> basketViewModel.openTrashBasketMenu()
//                R.id.menuSearch -> //todo Add search
//            }
//            return@setOnMenuItemClickListener true
//        }
    }
}

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