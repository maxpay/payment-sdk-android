package com.maxpay.sdk.payment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.maxpay.sdk.payment.data.PayCallback
import com.maxpay.sdk.payment.data.PayResult
import com.maxpay.sdk.payment.data.PayResultStatus
import com.maxpay.sdk.payment.model.PayInitInfo
import com.maxpay.sdk.payment.model.PayPaymentInfo
import com.maxpay.sdk.payment.utils.Constants

internal class SdkFacadeImpl private constructor() : SDKFacade {

    private var checkoutCallBack: PayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (this@SdkFacadeImpl.checkoutCallBack != null) {
                when (intent.action) {
                    Constants.PAY_CALLBACK_BROADCAST -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.PAY_BROADCAST_DATA)
                            ?.let {
                                this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(it as? PayResult)
                            } ?: kotlin.run {
                            this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(
                                PayResult(
                                    status = PayResultStatus.REJECTED,
                                    message = "Unknown error"
                                )
                            )
                        }
                        context.unregisterReceiver(this)
                    }
                    Constants.PAY_CALLBACK_BROADCAST_SIGNATURE -> {
                        intent.getStringExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA)
                            ?.let {
                                this@SdkFacadeImpl.checkoutCallBack?.onNeedCalculateSignature(it) { signature ->
                                    sendBroadcastData(context, signature)
                                }
                            } ?: kotlin.run {
                            this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(
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

    internal object HOLDER {
        val INSTANCE = SdkFacadeImpl()
    }

    override fun pay(context: Context?, data: PayInitInfo, pay: PayPaymentInfo, callback: PayCallback) {
        this.checkoutCallBack = callback
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST_SIGNATURE)
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST)
        try {
            context?.unregisterReceiver(mReceiver)
        } catch (e: IllegalArgumentException) {}
        context?.registerReceiver(mReceiver, intentFilter)

        context?.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.PAY_INIT_DATA, data)
            putExtra(Constants.Companion.Extra.PAY_PAYMENT_DATA, pay)
            putExtra(Constants.Companion.Extra.PAY_CUSTOM_THEME_DATA, data.theme)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}