package com.maxpay.testappmaxpay.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.testappmaxpay.core.ext.observeCommandSafety
import com.maxpay.testappmaxpay.core.ext.showDialog
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.utils.Utils
import kotlinx.android.synthetic.main.fragment_settings.segmentControlTransaction
import kotlinx.android.synthetic.main.fragment_settings.toolbar
import kotlinx.android.synthetic.main.fragment_settings.tvChange
import kotlinx.android.synthetic.main.fragment_settings.tvCurrency
import kotlinx.android.synthetic.main.fragment_simple_form.*
import kotlinx.android.synthetic.main.fragment_simple_form.btnPay


class SimpleFormFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initUIelements()

    }

    private fun initUIelements() {
        tvCurrency.text = viewModel.viewState.settings.value?.currency?.currencyCode
        when(viewModel.viewState.settings.value?.transactionType) {
            TransactionType.AUTH -> segmentControlTransaction.setSelectedSegment(0)
            TransactionType.AUTH3D -> segmentControlTransaction.setSelectedSegment(1)
            TransactionType.SALE -> segmentControlTransaction.setSelectedSegment(2)
            TransactionType.SALE3D -> segmentControlTransaction.setSelectedSegment(3)
        }
        segmentControlTransaction.addOnSegmentClickListener {
            it.column
            it.sectionData?.let {
                viewModel.viewState.settings.value?.transactionType = TransactionType.valueOf(it.segmentData as String)
            }
        }

        btnPay.setOnClickListener {
            ilTotalAmount?.let {
                if (!it.text.isNullOrEmpty()) {
                    viewModel.viewState.settings.value?.amount = it.text.toString().toFloat()
                    viewModel.viewState.fullPrice.value = it.text.toString().toFloat()
                }
            }

            if (switcherEditCustomerData.isChecked)
                findNavController().navigate(R.id.action_simpleFormFragment_to_payFragment)
            else
                viewModel.payWithSDK(requireContext())

        }
        tvChange.setOnClickListener {
            chooseCurrency()
        }
        viewModel.run {
            observeCommandSafety(viewState.maxpayResult) {
//                it.status
                showDialog(it.status.toString(), it.message?: "Undefined error")
//                showInfo(it.message)
            }
        }

    }

    private fun chooseCurrency() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.choose_currency))

        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item
        )
        val currencyList = Utils.getCurrenciesList()
        Utils.getCurrenciesList()
        currencyList.forEach {
            arrayAdapter.add(it.currencyCode)
        }
        builder.setAdapter(
            arrayAdapter
        ) { _, l ->
            val curr = currencyList.elementAt(l)
            viewModel.viewState.settings.value?.currency = curr
            tvCurrency.text = curr.currencyCode
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.simple_form)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}