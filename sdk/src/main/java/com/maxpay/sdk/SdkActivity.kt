package com.maxpay.sdk

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import com.maxpay.sdk.core.ProgressActivity
import com.maxpay.sdk.model.MaxpaySignatureData
import com.maxpay.sdk.ui.MainViewModel
import com.maxpay.sdk.ui.PaymentFragment
import com.maxpay.sdk.utils.extensions.addFragmentToContainerWithoutBackStack
import com.maxpay.sdk.ui.navigation.ThreeDSNavigation
import com.maxpay.sdk.ui.threeds.ThreeDSFragment
import com.maxpay.sdk.utils.extensions.addFragmentToContainer
import com.maxpay.sdk.utils.extensions.observeCommandSafety
import kotlinx.android.synthetic.main.activity_sdk.*

class SdkActivity : ProgressActivity(R.layout.activity_sdk) {

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
