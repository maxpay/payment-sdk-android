package com.maxpay.testappmaxpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.model.MaxPayInitData
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cur = Currency.getInstance(Locale.getDefault())
        val sdk: SDKFacade = SdkFacadeImpl(
            MaxPayInitData(
                accountName = "Dinarys",
                accountPassword = "h6Zq7dLPYMcve1F2",
                apiVersion = 1,
                showBillingAddr = false
            )
        )
        navController = findNavController(R.id.nav_host_fragment_main)

    }
}