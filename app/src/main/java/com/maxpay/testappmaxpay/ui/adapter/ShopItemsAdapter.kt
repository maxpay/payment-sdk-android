package com.maxpay.testappmaxpay.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.core.getPriceString
import com.maxpay.testappmaxpay.model.ProductItemtUI
import kotlinx.android.synthetic.main.item_basket.view.*
import kotlinx.android.synthetic.main.item_shop.view.*
import kotlinx.android.synthetic.main.item_shop.view.ivProduct
import kotlinx.android.synthetic.main.item_shop.view.tvPrice
import kotlinx.android.synthetic.main.item_shop.view.tvTitle

class ShopItemsAdapter :
    RecyclerView.Adapter<ShopItemsAdapter.CheckoutItemViewHolder>() {
    private val thingsList: MutableList<ProductItemtUI> = mutableListOf()
    var selectedItemListener: ((ProductItemtUI) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return CheckoutItemViewHolder(
            view
        )
    }

    fun setItems(topics: List<ProductItemtUI>) {
        thingsList.clear()
        thingsList.addAll(topics)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = thingsList.size

    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        holder.bind(thingsList[position], selectedItemListener)
    }

    class CheckoutItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ProductItemtUI, selectedItemListener: ((ProductItemtUI) -> Unit)?) {
            view.run {
                tvTitle.text = item.title
                tvPrice.text = "${item.price.getPriceString()} ${item.currency.currencyCode}"
                btnBuy.setOnClickListener { selectedItemListener?.invoke(item) }
                btnRemove.setOnClickListener {

                }
                Glide.with(view)
                    .load(ContextCompat.getDrawable(view.context, item.pictureDrawable))
                    .into(ivProduct);
            }
        }
    }


}