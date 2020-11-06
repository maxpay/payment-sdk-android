package com.maxpay.testappmaxpay.ui.payment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.toolbar
import java.util.*


class PayFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initUIelements()

    }

    private fun initUIelements() {
        btnPay.setOnClickListener {
            ilFirstName.editText?.let {
                viewModel.viewState.settings.value?.firstName = it.text.toString()
            }

            ilLastName.editText?.let {
                viewModel.viewState.settings.value?.lastName = it.text.toString()
            }
            ilPhone.editText?.let {
                viewModel.viewState.settings.value?.userPhone = it.text.toString()
            }
            ilCountry.editText?.let {
                viewModel.viewState.settings.value?.country = it.text.toString()
            }
            ilCity.editText?.let {
                viewModel.viewState.settings.value?.city = it.text.toString()
            }
            ilAddr.editText?.let {
                viewModel.viewState.settings.value?.address = it.text.toString()
            }
            ilZip.editText?.let {
                viewModel.viewState.settings.value?.zip = it.text.toString()
            }

            val sdk: SDKFacade = SdkFacadeImpl(
                MaxPayInitData(
                    accountName = "Dinarys",
                    accountPassword = "h6Zq7dLPYMcve1F2",
                    apiVersion = 1
                )
//            object: onSuccess() {
//
//            }
            )
            viewModel.viewState.settings.value?.let {
                sdk.pay(it)
            }
        }
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.pay)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}