package com.maxpay.sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.maxpay.sdk.data.PayCallback
import com.maxpay.sdk.data.PayResult
import com.maxpay.sdk.data.PayResultStatus
import com.maxpay.sdk.model.PayInitInfo
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.model.PaySignatureInfo
import com.maxpay.sdk.utils.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SdkHelper: KoinComponent {
    private val context: Context by inject()

    private var checkoutCallBack: PayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (this@SdkHelper.checkoutCallBack != null) {
                when (intent.action) {
                    Constants.PAY_CALLBACK_BROADCAST -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.PAY_BROADCAST_DATA)
                            ?.let {
                                this@SdkHelper.checkoutCallBack?.onResponseResult(it as? PayResult)
                            } ?: kotlin.run {
                            this@SdkHelper.checkoutCallBack?.onResponseResult(
                                PayResult(
                                    status = PayResultStatus.REJECTED,
                                    message = "Unknown error"
                                )
                            )
                        }
                        context.unregisterReceiver(this)
                    }
                    Constants.PAY_CALLBACK_BROADCAST_SIGNATURE -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA)
                            ?.let {
                                this@SdkHelper.checkoutCallBack?.onNeedCalculateSignature(it as? PaySignatureInfo) {
                                    sendBroadcastData(context, it)
                                }
                            } ?: kotlin.run {
                            this@SdkHelper.checkoutCallBack?.onResponseResult(
                                PayResult(
                                    status = PayResultStatus.UNDEF,
                                    message = "Unknown error"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun sendBroadcastData(context: Context?, data: String?) {
        val intent = Intent(Constants.PAY_BROAD_SIGNATURE_RES)
        data?.let {
            intent.setPackage(context?.packageName)
            intent.putExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA, data)
        }
        context?.sendBroadcast(intent)
    }

    fun startSdkActivity(
        payInfo: PayPaymentInfo,
        initData: PayInitInfo,
        callBack: PayCallback
    ) {
        this.checkoutCallBack = callBack
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST_SIGNATURE)
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST)
        try {

            context.unregisterReceiver(mReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        context.registerReceiver(mReceiver, intentFilter)

        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA, initData)
            putExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA, payInfo)
            putExtra(Constants.Companion.Extra.MAXPAY_CUSTOM_THEME_DATA, initData.theme)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
