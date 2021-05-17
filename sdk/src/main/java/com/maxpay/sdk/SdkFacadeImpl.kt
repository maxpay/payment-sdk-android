package com.maxpay.sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.model.PayInitData
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.model.PaySignatureInfo
import com.maxpay.sdk.utils.Constants

class SdkFacadeImpl private constructor() : SDKFacade {

    private var checkoutCallBack: MaxpayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (this@SdkFacadeImpl.checkoutCallBack != null) {
                when (intent.action) {
                    Constants.PAY_CALLBACK_BROADCAST -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.PAY_BROADCAST_DATA)
                            ?.let {
                                this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(it as? MaxpayResult)
                            } ?: kotlin.run {
                            this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(
                                MaxpayResult(
                                    status = MaxpayResultStatus.REJECTED,
                                    message = "Unknown error"
                                )
                            )
                        }
                        context.unregisterReceiver(this)
                    }
                    Constants.PAY_CALLBACK_BROADCAST_SIGNATURE -> {
                        intent.getSerializableExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA)
                            ?.let {
                                this@SdkFacadeImpl.checkoutCallBack?.onNeedCalculateSignature(it as? PaySignatureInfo) {
                                    sendBroadcastData(context, it)
                                }
                            } ?: kotlin.run {
                            this@SdkFacadeImpl.checkoutCallBack?.onResponseResult(
                                MaxpayResult(
                                    status = MaxpayResultStatus.UNDEF,
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
    private object HOLDER {
        val INSTANCE = SdkFacadeImpl()
    }

    companion object {
            val instance: SdkFacadeImpl by lazy { HOLDER.INSTANCE }
    }

    override fun pay(context: Context?, data: PayInitData, pay: PayPaymentInfo, callback: MaxpayCallback) {
//        SdkHelper().startSdkActivity()
//        SdkHelper().startSdkActivity(maxpay, data, callback)
        this.checkoutCallBack = callback
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST_SIGNATURE)
        intentFilter.addAction(Constants.PAY_CALLBACK_BROADCAST)
        try {
            context!!.unregisterReceiver(mReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        context!!.registerReceiver(mReceiver, intentFilter)

        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            putExtra(Constants.Companion.Extra.MAXPAY_INIT_DATA, data)
            putExtra(Constants.Companion.Extra.MAXPAY_PAYMENT_DATA, pay)
            putExtra(Constants.Companion.Extra.MAXPAY_CUSTOM_THEME_DATA, data.theme)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}