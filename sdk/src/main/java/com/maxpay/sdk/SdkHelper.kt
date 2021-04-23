package com.maxpay.sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.MaxpaySignatureData
import com.maxpay.sdk.utils.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SdkHelper: KoinComponent {
    private val context: Context by inject()

    private var checkoutCallBack: MaxpayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (this@SdkHelper.checkoutCallBack != null) {
                when (intent.action) {
                    Constants.MAXPAY_CALLBACK_BROADCAST -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_DATA)
                            ?.let {
                                this@SdkHelper.checkoutCallBack?.onResponseSuccess(it as? MaxpayResult)
                            } ?: kotlin.run {
                            this@SdkHelper.checkoutCallBack?.onResponceError(
                                MaxpayResult(
                                    MaxpayResultStatus.REJECTED,
                                    "Unknown error"
                                )
                            )
                        }
                        context.unregisterReceiver(this)
                    }
                    Constants.MAXPAY_CALLBACK_BROADCAST_SIGNATURE -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_SIGNATURE_DATA)
                            ?.let {
                                this@SdkHelper.checkoutCallBack?.onNeedCalculateSignature(it as? MaxpaySignatureData) {
                                    sendBroadcastData(context, it)
                                }
                            } ?: kotlin.run {
                            this@SdkHelper.checkoutCallBack?.onResponceError(
                                MaxpayResult(
                                    MaxpayResultStatus.UNDEF,
                                    "Unknown error"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun sendBroadcastData(context: Context?, data: String?) {
        val intent = Intent(Constants.MAXPAY_BROAD_SIGNATURE_RES)
        data?.let {
            intent.setPackage(context?.packageName)
            intent.putExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_SIGNATURE_DATA, data)
        }
        context?.sendBroadcast(intent)
    }

    fun startSdkActivity(
        payData: MaxpayPaymentData,
        initData: MaxPayInitData,
        callBack: MaxpayCallback
    ) {
        this.checkoutCallBack = callBack
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.MAXPAY_CALLBACK_BROADCAST_SIGNATURE)
        intentFilter.addAction(Constants.MAXPAY_CALLBACK_BROADCAST)
        try {

            context.unregisterReceiver(mReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        context.registerReceiver(mReceiver, intentFilter)

        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA, initData)
            putExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA, payData)
            putExtra(Constants.Companion.Extra.MAXPAY_CUSTOM_THEME_DATA, initData.theme)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
