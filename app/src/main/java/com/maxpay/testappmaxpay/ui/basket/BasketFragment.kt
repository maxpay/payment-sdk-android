package com.maxpay.testappmaxpay.ui.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.core.getPriceString
import com.maxpay.testappmaxpay.model.ProductItemtUI
import com.maxpay.testappmaxpay.ui.MainViewModel
import com.maxpay.testappmaxpay.ui.adapter.BasketItemsAdapter
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_basket.toolbar
import kotlinx.android.synthetic.main.layout_bottom_bar_basket_screen_confirm_order.*

class BasketFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setupRV()

        btnContinueShopping.setOnClickListener {
            activity?.onBackPressed()
        }
        btnCheckout.setOnClickListener {
            viewModel.viewState.fullPrice.value?.let {
                viewModel.viewState.settings.value?.amount = it
            }
            findNavController().navigate(R.id.action_basketFragment_to_payFragment)
        }

        viewModel.run {
            observeCommandSafety(viewState.listOfProducts) {
                tvEmptyBasket.visibility = View.GONE
                fillData(it)
            }
            observeCommandSafety(viewState.fullPrice) {
                tvFullPrice.text ="Total: ${it.getPriceString()} ${viewModel.viewState.settings.value?.currency}"
            }
        }
    }


    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.basket)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRV() {
        rvItems.adapter = BasketItemsAdapter().apply {
            selectedItemListener = {
                viewModel.changeBasketItemCount(it)
            }
            removeItemListener = {
                viewModel.removeFromBasket(it)
                viewModel.viewState.listOfProducts.value?.let {
                    (rvItems.adapter as? BasketItemsAdapter)?.setItems(it)
                    (rvItems.adapter as? BasketItemsAdapter)?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun fillData(list: MutableList<ProductItemtUI>) {
        (rvItems.adapter as? BasketItemsAdapter)?.setItems(list)
    }
}