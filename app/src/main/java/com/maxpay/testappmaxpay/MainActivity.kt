package com.maxpay.testappmaxpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sdk: SDKFacade = SdkFacadeImpl(applicationContext)
//        navController = findNavController(R.id.nav_host_fragment_main)
//        fab.setOnClickListener {
            sdk.init()
//        }


    }
}