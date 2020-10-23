package com.maxpay.sdk.utils.extensions

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.maxpay.sdk.R
import com.maxpay.sdk.core.ProgressActivity
import com.maxpay.sdk.utils.AlternativeDialogFactory

var dialogReferal: Dialog? = null

fun Fragment.closeKeyboard() = context?.let {
    (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}

fun Fragment.showProgressDialog() {
    if (activity != null && activity is ProgressActivity)
        (activity as ProgressActivity).getProgressBar().visibility = View.VISIBLE
}

fun Fragment.dismissDialogs() {
    if (activity != null && activity is ProgressActivity)
        (activity as ProgressActivity).getProgressBar().visibility = View.GONE
}

fun Fragment.showError(errorText: String) {
    if (activity != null && activity is AppCompatActivity) {
        context?.let {
            AlternativeDialogFactory.showAlertWithOneButton(
                it,
                getString(R.string.global_error),
                errorText,
                getString(R.string.Global_ok),
                {})
        }
    }
}

fun Fragment.showInfo(errorText: String, onOk: () -> Unit ) {
    if (activity != null && activity is AppCompatActivity) {
        context?.let {
            AlternativeDialogFactory.showAlertWithOneButton(
                it,
                getString(R.string.global_info),
                errorText,
                getString(R.string.Global_ok),
                onOk)
        }
    }
}
