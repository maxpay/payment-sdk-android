package com.maxpay.sdk

import android.os.Bundle
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.maxpay.sdk.core.ProgressActivity
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.utils.Constants
import kotlinx.android.synthetic.main.activity_sdk.*

class SdkActivity : ProgressActivity(R.layout.activity_sdk) {

    private lateinit var navController: NavController
    override fun getProgressBar(): ProgressBar = progressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.nav_host_fragment)
        savedInstanceState?.apply {

        }

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            navController.navigate(R.id.action_firstFragment_to_secondFragment)
//        }
    }

}

//        findNavController().navigate(R.id.action_areaListFragment_to_reservationCalendarFragment, bundleOf(
//            RESERVATION_OBJECT to model)