package com.maxpay.sdk.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.R
import com.maxpay.sdk.model.request.AuthPayment
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.utils.Constants
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import kotlinx.android.synthetic.main.fragment_payment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PaymentFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val viewModel1: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {
            observeCommandSafety(viewState.authPaymentResponse) {
                val i = Intent(context, MaxPayActivity::class.java).apply {
                    putExtra(Constants.Companion.Extra.RETURN_URL, it.accessUrl)
                    putExtra(Constants.Companion.Extra.MAXPAY_PARAQ, URLEncoder.encode(it.pareq))
                    putExtra(Constants.Companion.Extra.MAXPAY_MD, URLEncoder.encode(it.reference))
                    putExtra(Constants.Companion.Extra.MAXPAY_TERM_URL, URLEncoder.encode("https://google.com"))
                }
                startActivity(i)
            }
        }

        payBtn.setOnClickListener {
            val salePayment = SalePayment(
                1,
                "Dinarys",
                "h6Zq7dLPYMcve1F2",
                "sale_requesttimestamp",
//                "sale_request{{$timestamp}}",// TODO
                TransactionType.SALE,
                11,
                "USD",
                "4012001038443335",
                "04",
                "2021",
                "548",
                "John",
                "Doe",
                "John Doe",
                "123 Street name.",
                "New York",
                "NY",
                "12100",
                "USA",
                "+12025550000",
                "johndoe@test.com",
                "193.34.96.96"
            )


            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)
            val authPayment = AuthPayment(
                1,
                "Dinarys",
                "h6Zq7dLPYMcve1F2",
                "AUTH3В$formatted",
                TransactionType.AUTH3D,
                9F,
                "EUR",
                "4012000300001003",
                "05",
                "2019",
                "111",
                "John",
                "Doe",
                "John Doe",
                "123 Street name.",
                "City Name",
                "NY",
                "12100",
                "USA",
                "+12025550000",
                "johndoe@test.com",
                "193.34.96.96"
            )

            viewModel.payAuth3D(authPayment)
//            viewModel.pay(salePayment)
        }
    }
}

