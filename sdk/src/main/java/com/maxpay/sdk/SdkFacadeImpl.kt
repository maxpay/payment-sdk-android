package com.maxpay.sdk

import com.maxpay.sdk.model.MaxPayInitData

class SdkFacadeImpl(
    private val data: MaxPayInitData
) : SDKFacade {

    override fun init() {
        //TODO get some data from sample app

        SdkHelper().startSdkActivity(data)
//        context.startActivity(Intent(context, SdkActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        })
    }
}