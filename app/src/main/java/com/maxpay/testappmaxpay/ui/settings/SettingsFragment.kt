package com.maxpay.testappmaxpay.ui.settings

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
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class SettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
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
            it.sectionData?.let {
                viewModel.viewState.settings.value?.transactionType = TransactionType.valueOf(it.segmentData as String)
            }
        }

        segmentedControlTheme.addOnSegmentClickListener {
            when(it.column) {
//                0 -> viewModel.viewState.settings.value.
            }
            it.sectionData?.let {
                viewModel.viewState.settings.value?.transactionType = TransactionType.valueOf(it.segmentData as String)
            }
        }
        tvChange.setOnClickListener {
            chooseCurrency()
        }
    }

    private fun chooseCurrency() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.choose_currency))

        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item
        )
        Currency.getAvailableCurrencies().forEach {
            arrayAdapter.add(it.currencyCode)
        }
        builder.setAdapter(arrayAdapter
        ) { _, l ->
//            val currCode = Currency.getAvailableCurrencies().elementAt(l).currencyCode
//            viewModel.viewState.settings.value?.currency = currCode
//            tvCurrency.text = currCode
            val curr = Currency.getAvailableCurrencies().elementAt(l)
            viewModel.viewState.settings.value?.currency = curr
            tvCurrency.text = curr.currencyCode
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.settings)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}