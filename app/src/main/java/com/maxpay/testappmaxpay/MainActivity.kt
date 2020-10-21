package com.maxpay.testappmaxpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sdk: SDKFacade = SdkFacadeImpl(applicationContext)
        sdk.init()

    }
}