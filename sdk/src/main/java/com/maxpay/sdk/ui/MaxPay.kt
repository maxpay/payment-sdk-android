package com.maxpay.sdk.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.utils.Constants
import java.net.URLEncoder

class MaxPay()  {
    private lateinit var context: Context
    private var checkoutCallBack: MaxpayCallback? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.MAXPAY_CALLBACK_BROADCAST && this@MaxPay.checkoutCallBack != null) {
                intent.getSerializableExtra("data")?.let {
                    this@MaxPay.checkoutCallBack?.onResponseSuccess(it as? MaxpayResult)
                }?: kotlin.run {
                    this@MaxPay.checkoutCallBack?.onResponceError(MaxpayResult.REJECTED)
                }
                context.unregisterReceiver(this)
            }
        }
    }

    fun checkout(context: Context, data: String?, url: String?, callBack: MaxpayCallback) {
        this.context = context
        this.checkoutCallBack = callBack
        context.registerReceiver(mReceiver,
            IntentFilter(Constants.MAXPAY_CALLBACK_BROADCAST))
        if (!data.isNullOrEmpty()) {
            val intent = Intent(context, MaxPayActivity::class.java)
            intent.putExtra(Constants.Companion.Extra.MAXPAY_DATA, URLEncoder.encode(data))
            intent.putExtra(Constants.Companion.Extra.RETURN_URL, url)
            intent.addFlags(268435456)
            context.startActivity(intent)
        } else
            this@MaxPay.checkoutCallBack?.onResponceError(MaxpayResult.UNDEF)

    }

    fun threeDS(context: Context, md: String?, url: String?, paraq: String?, termUrl: String?, callBack: MaxpayCallback) {
        this.context = context
        this.checkoutCallBack = callBack
        context.registerReceiver(mReceiver,
            IntentFilter(Constants.MAXPAY_CALLBACK_BROADCAST))
        if (!paraq.isNullOrEmpty()) {
            val intent = Intent(context, MaxPayActivity::class.java)
//            intent.putExtra(Constants.Companion.Extra.PORTMONE_MD, URLEncoder.encode(md)) //TODO put some extra
            intent.addFlags(268435456)
            context.startActivity(intent)
        } else
            this@MaxPay.checkoutCallBack?.onResponceError(MaxpayResult.UNDEF)

    }
}

