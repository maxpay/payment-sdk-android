package com.maxpay.testappmaxpay.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxpay.testappmaxpay.R
import com.maxpay.testappmaxpay.model.CardsPaymentUI
import kotlinx.android.synthetic.main.item_checkout.view.*

class ItemsAdapter :
    RecyclerView.Adapter<ItemsAdapter.CheckoutItemViewHolder>() {
    private val thingsList: MutableList<CardsPaymentUI> = mutableListOf()
    var selectedItemListener: ((CardsPaymentUI) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return CheckoutItemViewHolder(
            view
        )
    }

    fun setCheckOutItems(topics: List<CardsPaymentUI>) {
        thingsList.clear()
        thingsList.addAll(topics)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = thingsList.size

    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        holder.bind(thingsList[position], selectedItemListener)
    }

    class CheckoutItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: CardsPaymentUI, selectedItemListener: ((CardsPaymentUI) -> Unit)?) {
            view.run {
                tvTitle.text = item.something
//                ivRemove.setOnClickListener {
//                    selectedItemListener?.invoke(item)
//                }
//                Glide.with(this)
//                    .load(item.imageUrls[0])
//                    .into(ivMainItemImage)
            }
        }
    }


}