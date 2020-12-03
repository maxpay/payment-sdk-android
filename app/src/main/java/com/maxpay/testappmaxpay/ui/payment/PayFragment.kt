package com.maxpay.testappmaxpay.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import com.maxpay.sdk.utils.extensions.showDialog
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.core.getPriceString
import com.maxpay.testappmaxpay.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.android.synthetic.main.fragment_settings.toolbar
import java.lang.RuntimeException


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
        viewModel.run {
            observeCommandSafety(viewState.maxpayResult) {
                showDialog(it.status.toString(), it.message?: "Undefined error")
            }
        }

    }

    private fun initUIelements() {
        tvTotalAmount.text ="${viewModel.viewState.fullPrice.value?.getPriceString()} ${viewModel.viewState.settings.value?.currency}"
        viewModel.viewState.maxPayAvailableFields.value?.showBillingAddressLayout?.let {
            switcherShowBillingP.isChecked = it
        }
        switcherShowBillingP.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showBillingAddressLayout = isChecked
        }
        btnPay.setOnClickListener {
            ilFirstName.editText?.let {
                viewModel.viewState.settings.value?.firstName = it.text.toString()
            }

            ilLastName.editText?.let {
                viewModel.viewState.settings.value?.lastName = it.text.toString()
            }
            ilPhone.editText?.let {
                viewModel.viewState.settings.value?.userPhone = it.text.toString().takeIf { !it.isEmpty() }?: null
            }
            ilCountry.editText?.let {
                viewModel.viewState.settings.value?.country = it.text.toString()
            }
            ilCity.editText?.let {
                viewModel.viewState.settings.value?.city = it.text.toString().takeIf { !it.isEmpty() }?: null
            }
            ilAddr.editText?.let {
                viewModel.viewState.settings.value?.address = it.text.toString().takeIf { !it.isEmpty() }?: null
            }
            ilZip.editText?.let {
                viewModel.viewState.settings.value?.zip = it.text.toString().takeIf { !it.isEmpty() }?: null
            }
            ilPK.editText?.let {
                viewModel.viewState.pk.value = it.text.toString().takeIf { !it.isEmpty() }?: null
            }

            viewModel.payWithSDK()
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