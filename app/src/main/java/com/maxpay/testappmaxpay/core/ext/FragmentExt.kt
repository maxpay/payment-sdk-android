package com.maxpay.testappmaxpay.core.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.maxpay.sdk.R
import com.maxpay.testappmaxpay.core.AlternativeDialogFactory

fun Fragment.closeKeyboard() = context?.let {
    (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}

fun Fragment.showError(errorText: String) {
    if (activity != null && activity is AppCompatActivity) {
        context?.let {
            AlternativeDialogFactory.showAlertWithOneButton(
                it,
                getString(R.string.global_error),
                errorText,
                getString(R.string.Global_ok)
            ) {}
        }
    }
}


fun Fragment.showInfo(errorText: String, onOk: (() -> Unit)? = null ) {
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

fun Fragment.showDialog(title: String, text: String, onOk: (() -> Unit)? = null ) {
    if (activity != null && activity is AppCompatActivity) {
        context?.let {
            AlternativeDialogFactory.showAlertWithOneButton(
                it,
                getString(R.string.global_info),
                text,
                getString(R.string.Global_ok),
                onOk)
        }
    }
}
