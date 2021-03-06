package com.maxpay.sdk.payment.core

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.maxpay.sdk.payment.utils.StateEnum
import com.maxpay.sdk.payment.utils.extensions.dismissDialogs
import com.maxpay.sdk.payment.utils.extensions.showError
import com.maxpay.sdk.payment.utils.extensions.showProgressDialog

internal abstract class FragmentWithToolbar(@LayoutRes layoutId: Int) : Fragment(layoutId) {

   abstract fun getCurrentViewModel(): MyAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowHomeEnabled(true)

        getCurrentViewModel().state.observe(viewLifecycleOwner, Observer {
            when (it) {
                StateEnum.LOADING -> {
                    showProgressDialog()
                }
                StateEnum.COMPLETE -> {
                    dismissDialogs()
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                StateEnum.ERROR -> {
                    dismissDialogs()
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    getCurrentViewModel().errorMessage?.let {errorText ->
                        showError(errorText)
                    }
                }
                StateEnum.UNITERUPTEDLOADING -> {
                    showProgressDialog()
                    activity.window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            }
        })

    }

}