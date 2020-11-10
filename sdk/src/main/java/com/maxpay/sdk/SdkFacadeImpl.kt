package com.maxpay.sdk

import android.content.IntentFilter
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.utils.Constants

class SdkFacadeImpl(
    private val data: MaxPayInitData
) : SDKFacade {

    override fun init() {
        //TODO get some data from sample app

//        SdkHelper().startSdkActivity(data)
//        context.startActivity(Intent(context, SdkActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        })
    }

    override fun pay(maxpay: MaxpayPaymentData, callback: MaxpayCallback) {
        SdkHelper().startSdkActivity(maxpay, data, callback)
    }
}