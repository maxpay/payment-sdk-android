package com.maxpay.testappmaxpay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.model.CardsPaymentUI
import com.maxpay.testappmaxpay.ui.adapter.ItemsAdapter
import kotlinx.android.synthetic.main.fragment_basket.*
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
        fillData()
        tvOldPrice.text = "1200"
        tvDiscountValue.text = "120"
        tvFullPrice.text = "1080"
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.basket)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRV() {
//        val dividerItemDecoration = SimpleDividerItemDecoration(
//            ContextCompat.getDrawable(
//                requireContext(),
//                R.drawable.item_divider_grey
//            ), SimpleDividerItemDecoration.VERTICAL
//        )
//        rvItems.addItemDecoration(dividerItemDecoration)
        rvItems.adapter = ItemsAdapter().apply {
            selectedItemListener = {

            }
        }
    }

    private fun fillData() {
        (rvItems.adapter as? ItemsAdapter)?.setCheckOutItems(
            arrayListOf(CardsPaymentUI("First cool item"),
                CardsPaymentUI("Second cool item"))
        )
    }
}