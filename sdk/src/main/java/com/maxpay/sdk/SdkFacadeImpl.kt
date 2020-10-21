package com.maxpay.sdk

import android.content.Context
import android.content.Intent

class SdkFacadeImpl(private val context: Context) : SDKFacade {

    override fun init() {
        //TODO get some data from sample app
        context.startActivity(Intent(context, SdkActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}