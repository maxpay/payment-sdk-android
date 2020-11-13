package com.maxpay.sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxPayTheme
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.utils.Constants
import org.koin.core.KoinComponent
import org.koin.core.inject

class SdkHelper: KoinComponent {
    private val context: Context by inject()

    private var checkoutCallBack: MaxpayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.MAXPAY_CALLBACK_BROADCAST && this@SdkHelper.checkoutCallBack != null) {
                intent.getSerializableExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_DATA)?.let {
                    this@SdkHelper.checkoutCallBack?.onResponseSuccess(it as? MaxpayResult)
                }?: kotlin.run {
                    this@SdkHelper.checkoutCallBack?.onResponceError(MaxpayResult(MaxpayResultStatus.REJECTED, "Unknown error"))
                }
                context.unregisterReceiver(this)
            }
        }
    }

    fun startSdkActivity(
        payData: MaxpayPaymentData,
        initData: MaxPayInitData,
        callBack: MaxpayCallback
    ) {
        this.checkoutCallBack = callBack
        context.registerReceiver(mReceiver,
            IntentFilter(Constants.MAXPAY_CALLBACK_BROADCAST)
        )
        context.registerReceiver(mReceiver,
            IntentFilter(Constants.MAXPAY_CALLBACK_BROADCAST)
        )
        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA, initData)
            putExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA, payData)
            putExtra(Constants.Companion.Extra.MAXPAY_CUSTOM_THEME_DATA, initData.theme)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

}
