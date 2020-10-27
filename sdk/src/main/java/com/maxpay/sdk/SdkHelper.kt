package com.maxpay.sdk

import android.content.Context
import android.content.Intent
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.utils.Constants
import org.koin.core.KoinComponent
import org.koin.core.inject

class SdkHelper: KoinComponent {
    private val context: Context by inject()

    fun startSdkActivity(
        payData: MaxpayPaymentData,
        initData: MaxPayInitData
    ) {
        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA, initData)
            putExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA, payData)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

}
