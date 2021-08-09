package com.maxpay.sdk.utils

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.maxpay.sdk.core.ProgressActivity
import com.maxpay.sdk.model.PayTheme
import kotlinx.android.synthetic.main.fragment_payment.view.*
import kotlinx.android.synthetic.main.layout_billing_address.view.*

class UIComponentThemeEditor(val theme: PayTheme?) {

    fun setInputStyle(view: View?) {
        view?.let { it.run {
            theme?.fieldTextColor?.let { it ->
                etEmail?.setTextColor(it)
                etCardNumber?.setTextColor(it)
                etExpirationDate?.setTextColor(it)
                etCvv?.setTextColor(it)
                etCardHolderName?.setTextColor(it)
                etName?.setTextColor(it)
                etLastName?.setTextColor(it)
                etAddr?.setTextColor(it)
                etCity?.setTextColor(it)
                etZip?.setTextColor(it)
                etCountry?.setTextColor(it)
                etBirthday?.setTextColor(it)
            }
            theme?.fieldBackgroundColor?.let {
                cvEmail?.setCardBackgroundColor(it)
                cvCardNumber?.setCardBackgroundColor(it)
                cvExpirDate?.setCardBackgroundColor(it)
                cvCvv?.setCardBackgroundColor(it)
                cvCardHolderName?.setCardBackgroundColor(it)
                cvName?.setCardBackgroundColor(it)
                cvLastName?.setCardBackgroundColor(it)
                cvAddress?.setCardBackgroundColor(it)
                cvCity?.setCardBackgroundColor(it)
                cvZip?.setCardBackgroundColor(it)
                cvCountry?.setCardBackgroundColor(it)
                cvCardHolderName?.setCardBackgroundColor(it)
                cvBirthday?.setCardBackgroundColor(it)
            }
            theme?.fieldTitleColor?.let {
                tvEA?.setTextColor(it)
                tvCN?.setTextColor(it)
                tvED?.setTextColor(it)
                tvCVV?.setTextColor(it)
                tvCHN?.setTextColor(it)
                tvName?.setTextColor(it)
                tvLastName?.setTextColor(it)
                tvAddr?.setTextColor(it)
                tvCity?.setTextColor(it)
                tvZIP?.setTextColor(it)
                tvCountry?.setTextColor(it)
                tvBDAY?.setTextColor(it)
            }
        } }
    }

    fun setMainPageStyle(view: View?) {
        view?.let {
            it.run {
                theme?.let {theme ->
                    theme.headerAmountColor?.let { it ->
                        tvFullPrice?.setTextColor(it)
                    }
                    theme.headerTitleColor?.let {
                        tvPA?.setTextColor(it)
                    }
                    theme.backgroundColor?.let {
                        clFullView?.setBackgroundColor(it)
                        layoutBillingAddress?.setBackgroundColor(it)
                    }
                    theme.headerAmountFont?.let {
                        tvFullPrice?.typeface = Typeface.createFromAsset(resources.assets, it)
                    }
                    theme.headerLargeTitleFont?.let {
                        val typeface = Typeface.createFromAsset(resources.assets, it)
                        tvPA?.typeface = typeface
//                        tvBilling?.typeface = typeface // TODO Removed by customer`s purpose
                    }
                    theme.headerStandardTitleFont?.let {
                        val typeface = Typeface.createFromAsset(resources.assets, it)
                        tvEA?.typeface = typeface
                        tvCN?.typeface = typeface
                        tvED?.typeface = typeface
                        tvCVV?.typeface = typeface
                        tvCHN?.typeface = typeface
                        tvName?.typeface = typeface
                        tvLastName?.typeface = typeface
                        tvAddr?.typeface = typeface
                        tvCity?.typeface = typeface
                        tvZIP?.typeface = typeface
                        tvCountry?.typeface = typeface
                        tvBDAY?.typeface = typeface
                    }
                    theme.headerAmountFont?.let {
                        tvFullPrice?.typeface = Typeface.createFromAsset(resources.assets, it)
                    }

                    theme.headerSeparatorColor?.let {
//                        billingSeparator.setBackgroundColor(it)  // TODO Removed by customer`s purpose
                        paymentSeparator.setBackgroundColor(it)
                    }


                }
            }
        }
    }

    fun changeButtonColorFilter(view: View?, enabled: Boolean) {
        view?.let {
            it.run {
                if (enabled) {
                    theme?.enabledButtonBackgroundColor?.let {
                        payBtn.background.setColorFilter(it, PorterDuff.Mode.SRC_ATOP)
                    }
                    theme?.enabledButtonTitleColor?.let {
                        payBtn.setTextColor(it)
                    }
                } else {
                    theme?.disabledButtonBackgroundColor?.let {
                        payBtn.background.setColorFilter(it, PorterDuff.Mode.SRC_ATOP)
                    }
                    theme?.disabledButtonTitleColor?.let {
                        payBtn.setTextColor(it)
                    }
                }

            }
        }
    }

    fun changeProgressBar(activity: FragmentActivity?) {
        theme?.progressBarColor?.let {
            (activity as? ProgressActivity)?.getProgressBar()?.getIndeterminateDrawable()
                ?.setColorFilter(it, PorterDuff.Mode.MULTIPLY )
        }
    }
}

