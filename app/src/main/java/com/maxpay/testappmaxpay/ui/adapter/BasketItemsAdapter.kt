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

class BasketItemsAdapter :
    RecyclerView.Adapter<BasketItemsAdapter.CheckoutItemViewHolder>() {
    private val productsList: MutableList<ProductItemtUI> = mutableListOf()
    var selectedItemListener: ((ProductItemtUI) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        return CheckoutItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = productsList.size

    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        holder.bind(productsList[position], selectedItemListener)
    }

    fun setItems(list: MutableList<ProductItemtUI>) {
        productsList.clear()
        productsList.addAll(list)
        notifyDataSetChanged()
    }

    class CheckoutItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ProductItemtUI, selectedItemListener: ((ProductItemtUI) -> Unit)?) {
            view.run {
                tvTitle.text = item.title
                tvPrice.text = "${item.price.getPriceString()} ${item.currency.currencyCode}"
                tvCount.text = item.count.toString()
                btnPlus.setOnClickListener {
                    item.count += 1
                    tvCount.text = item.count.toString()
                    selectedItemListener?.invoke(item)
                }
                btnMinus.setOnClickListener {
                    if (item.count != 1) {
                        item.count -= 1
                        tvCount.text = item.count.toString()
                        selectedItemListener?.invoke(item)
                    }
                }
                btnRemove.setOnClickListener { selectedItemListener?.invoke(item) }
                Glide.with(view)
                    .load(ContextCompat.getDrawable(view.context, item.pictureDrawable))
                    .into(ivProduct);
            }
        }
    }


}