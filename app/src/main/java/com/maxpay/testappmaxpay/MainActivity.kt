package com.maxpay.testappmaxpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.request.TransactionType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sdk: SDKFacade = SdkFacadeImpl(
            MaxPayInitData(
                accountName = "Dinarys",
                accountPassword = "h6Zq7dLPYMcve1F2",
                transactionType = TransactionType.AUTH
            )

        )
        navController = findNavController(R.id.nav_host_fragment_main)
        fab.setOnClickListener {
            sdk.init()
        }
    }
}