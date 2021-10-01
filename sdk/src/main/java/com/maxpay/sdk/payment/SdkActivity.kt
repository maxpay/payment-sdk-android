package com.maxpay.sdk.payment

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import com.maxpay.sdk.payment.core.ProgressActivity
import com.maxpay.sdk.payment.ui.MainViewModel
import com.maxpay.sdk.payment.ui.PaymentFragment
import com.maxpay.sdk.payment.utils.extensions.addFragmentToContainerWithoutBackStack
import com.maxpay.sdk.payment.ui.navigation.ThreeDSNavigation
import com.maxpay.sdk.payment.ui.threeds.ThreeDSFragment
import com.maxpay.sdk.payment.utils.extensions.addFragmentToContainer
import com.maxpay.sdk.payment.utils.extensions.observeCommandSafety
import kotlinx.android.synthetic.main.activity_sdk.*

internal class SdkActivity : ProgressActivity(R.layout.activity_sdk) {

    override fun getProgressBar(): ProgressBar = progressBar
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: addFragmentToContainerWithoutBackStack(
            PaymentFragment.newInstance()
        )

        viewModel.run {
            observeCommandSafety(mainNavigation) {
                when (it) {
                    ThreeDSNavigation -> {
                        addFragmentToContainer(
                            ThreeDSFragment.newInstance()
                        )
                    }
                }

            }
        }
    }
}
