package com.maxpay.testappmaxpay.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.model.ProductItemtUI
import com.maxpay.testappmaxpay.ui.MainViewModel
import com.maxpay.testappmaxpay.ui.adapter.ShopItemsAdapter
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_blank.toolbar
import java.util.*

class ShopFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tt = TransactionType.AUTH3D

        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setupRV()
        fillData()

        viewModel.run {
            observeCommandSafety(viewState.countProducts) {
                val menu = ((toolbar as Toolbar).menu as MenuBuilder).getItem(0)
                menu.title = resources.getString(R.string.cart_menu_with_number, it)
            }

        }
    }

    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.title = resources.getString(R.string.shop)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_cart -> findNavController().navigate(R.id.action_shopFragment_to_basketFragment)
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupRV() {
        rvItems.adapter = ShopItemsAdapter().apply {
            selectedItemListener = {
                viewModel.addToBasket(it)

            }
        }
    }

    private fun fillData() {
        (rvItems.adapter as? ShopItemsAdapter)?.setItems(
            arrayListOf(
                ProductItemtUI(
                    1,
                    "Pixel 2",
                    500.99F,
                    1,
                    R.drawable.pxl2,
                    Currency.getInstance(Locale.getDefault())
                ),
                ProductItemtUI(
                    2,
                    "Pixel 3",
                    620.90F,
                    1,
                    R.drawable.pxl3,
                    Currency.getInstance(Locale.getDefault())
                ),
                ProductItemtUI(3,
                    "Pixel 4",
                    700F,
                    1,
                    R.drawable.pixel4,
                    Currency.getInstance(Locale.getDefault())
                )
            )
        )
    }
}