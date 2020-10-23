package com.maxpay.sdk.core

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.utils.StateEnum
import com.maxpay.sdk.utils.extensions.dismissDialogs
import com.maxpay.sdk.utils.extensions.showError
import com.maxpay.sdk.utils.extensions.showProgressDialog

abstract class FragmentWithToolbar(@LayoutRes layoutId: Int) : Fragment(layoutId) {

   abstract fun getCurrentViewModel(): MyAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().popBackStack()
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