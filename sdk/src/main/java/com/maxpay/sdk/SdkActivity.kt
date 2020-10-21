package com.maxpay.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

class SdkActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk)

        navController = findNavController(R.id.nav_host_fragment)

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            navController.navigate(R.id.action_firstFragment_to_secondFragment)
//        }
    }

}

//        findNavController().navigate(R.id.action_areaListFragment_to_reservationCalendarFragment, bundleOf(
//            RESERVATION_OBJECT to model)