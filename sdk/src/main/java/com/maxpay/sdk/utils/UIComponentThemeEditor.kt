package com.maxpay.sdk.utils

import android.view.View
import com.maxpay.sdk.model.MaxPayTheme
import kotlinx.android.synthetic.main.fragment_payment.view.*
import kotlinx.android.synthetic.main.layout_billing_address.view.*

class UIComponentThemeEditor(val theme: MaxPayTheme?) {

    fun setInputStyle(view: View?) {
        view?.let { it.run {
            theme?.fieldTextColor?.let { it ->
                etEmail?.setTextColor(it)
                etCardNumber?.setTextColor(it)
                etExpirationDate?.setTextColor(it)
                etCvv?.setTextColor(it)
                etCardHolderName?.setTextColor(it)
                etName?.setTextColor(it)
                etAddr?.setTextColor(it)
                etCity?.setTextColor(it)
                etZip?.setTextColor(it)
                etCountry?.setTextColor(it)
                etCardHolderName?.setTextColor(it)
            }
            theme?.fieldBackgroundColor?.let {
                cvEmail?.setBackgroundColor(it)
                cvCardNumber?.setBackgroundColor(it)
                cvExpirDate?.setBackgroundColor(it)
                cvCvv?.setBackgroundColor(it)
                cvCardHolderName?.setBackgroundColor(it)
                cvName?.setBackgroundColor(it)
                cvAddress?.setBackgroundColor(it)
                cvCity?.setBackgroundColor(it)
                cvZip?.setBackgroundColor(it)
                cvCountry?.setBackgroundColor(it)
                cvCardHolderName?.setBackgroundColor(it)
            }
            theme?.fieldTitleColor?.let {
                tvEA?.setTextColor(it)
                tvCN?.setTextColor(it)
                tvED?.setTextColor(it)
                tvCVV?.setTextColor(it)
                tvCHN?.setTextColor(it)
                tvName?.setTextColor(it)
                tvAddr?.setTextColor(it)
                tvCity?.setTextColor(it)
                tvZIP?.setTextColor(it)
                tvCountry?.setTextColor(it)
            }
        } }
    }

    fun setMainPageStyle(view: View?) {
        view?.let {
            it.run {
                theme?.headerAmountColor?.let { it ->
                    tvFullPrice?.setTextColor(it)
                }
                theme?.headerTitleColor?.let {
                    tvPA?.setTextColor(it)
                }
                theme?.backgroundColor?.let {
                    clFullView.setBackgroundColor(it)
                    layoutBillingAddress.setBackgroundColor(it)
                }
            }
        }

    }
}

