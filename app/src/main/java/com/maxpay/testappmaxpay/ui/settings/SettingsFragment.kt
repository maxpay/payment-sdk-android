package com.maxpay.testappmaxpay.ui.settings

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.model.MaxPayTheme
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.ui.MainViewModel
import com.maxpay.testappmaxpay.utils.Utils
import kotlinx.android.synthetic.main.fragment_settings.*


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

        viewModel.viewState.maxPayTheme.value?.let {
            segmentedControlTheme.setSelectedSegment(1)
        }?: kotlin.run { segmentedControlTheme.setSelectedSegment(0) }
        if (viewModel.viewState.maxPayAvailableFields.value?.showBillingAddressLayout == true)
            setSwitchesState(true)
        else
            setSwitchesState(false)
        segmentedControlTheme.addOnSegmentClickListener {
            val  font = "fonts/GVB.otf"
            when(it.column) {
                0 -> viewModel.viewState.maxPayTheme.value = null
                1 -> viewModel.viewState.maxPayTheme.value =
                MaxPayTheme(
                    fieldTitleColor = Color.RED,
                    fieldBackgroundColor = Color.YELLOW,
                    fieldTextColor = Color.CYAN,
                    errorColor = Color.YELLOW,
                    backgroundColor = Color.CYAN,
                    navigationBarColor = Color.GREEN,
                    navigationBarTitleColor = Color.BLACK,
                    hyperlinkColor = Color.RED,
                    headerAmountColor = Color.RED,
                    headerTitleColor = Color.GREEN,
                    headerAmountFont = font,
                    headerStandardTitleFont = font,
                    headerLargeTitleFont = font,
                    headerSeparatorColor = Color.RED,
                    disabledButtonBackgroundColor = Color.BLACK,
                    disabledButtonTitleColor = Color.WHITE,
                    enabledButtonBackgroundColor =  Color.RED,
                    enabledButtonTitleColor =  Color.BLACK,
                    progressBarColor = Color.RED
                )

            }
        }
        tvChange.setOnClickListener {
            chooseCurrency()
        }
        initSwitches()
    }

    private fun setSwitchesState(b: Boolean) {
        switcherShowAddress.isEnabled = b
        switcherShowCity.isEnabled = b
        switcherShowNameField.isEnabled = b
        switcherShowZIP.isEnabled = b
        switcherShowCountry.isEnabled = b

        val availableFields = viewModel.viewState.maxPayAvailableFields.value
        switcherShowBilling.isChecked = availableFields?.showBillingAddressLayout ?: false
        switcherShowAddress.isChecked = availableFields?.showAddressField ?: false
        switcherShowCity.isChecked = availableFields?.showCityField ?: false
        switcherShowNameField.isChecked = availableFields?.showNameField ?: false
        switcherShowZIP.isChecked = availableFields?.showZipField ?: false
        switcherShowCountry.isChecked = availableFields?.showCountryField ?: false


    }

    private fun initSwitches() {
        switcherShowBilling.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showBillingAddressLayout = isChecked
            setSwitchesState(isChecked)
        }
        switcherShowAddress.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showAddressField = isChecked
        }
        switcherShowCity.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showCityField = isChecked
        }
        switcherShowNameField.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showNameField = isChecked
        }
        switcherShowZIP.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showZipField = isChecked
        }
        switcherShowCountry.setOnCheckedChangeListener { _, isChecked ->
            viewModel.viewState.maxPayAvailableFields.value?.showCountryField = isChecked
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
        toolbar.title = resources.getString(R.string.settings)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}