package com.maxpay.testappmaxpay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.model.CardsPaymentUI
import com.maxpay.testappmaxpay.ui.adapter.ItemsAdapter
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank.toolbar

class BlankFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tt = TransactionType.AUTH3D

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setupRV()
        fillData()
        btnNavigateToBasket.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_basketFragment)
        }
    }
    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.navigationIcon = null
        toolbar.title = "Main screen"
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
//        basketListRecyclerView.addItemDecoration(dividerItemDecoration)
//        rvItems.adapter = ItemsAdapter().apply {
//            selectedItemListener = {
//
//            }
//        }
    }

    private fun fillData() {
//        (rvItems.adapter as? ItemsAdapter)?.setCheckOutItems(
//            arrayListOf(CardsPaymentUI("Ssd"), CardsPaymentUI("1213"))
//        )
    }
}