package com.maxpay.sdk.utils.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.maxpay.sdk.R
import com.maxpay.sdk.core.ProgressActivity
import com.maxpay.sdk.model.InputFormLength
import com.maxpay.sdk.utils.AlternativeDialogFactory

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
                getString(R.string.Global_ok)
            ) {}
        }
    }
}

fun Fragment.isFormCompleted(vararg input: TextInputLayout): Boolean {
    var isValidate: Boolean? = null
    for (item in input) {
        if (item.editText?.text.isNullOrEmpty()) {
            item.error = getString(R.string.Global_field_empty)
            isValidate = false
        } else {
            if (isValidate != false)
                isValidate = true
            item.error = null
        }
    }
    return isValidate ?: true
}

fun Fragment.isFormLengthValid(vararg inputLengthForm: InputFormLength): Boolean {
    var isValidate: Boolean? = null
    for (item in inputLengthForm) {
        item.input.text?.let {
            val validText = it.toString().replace(" ", "")
                                         .replace("_", "")
                                         .replace("/", "")
            if (validText.length < item.requiredLength) {
                item.input.setTextColor(Color.RED)
                item.card.strokeColor = Color.RED
//                item.input.error = getString(R.string.Global_field_not_vaild)
                isValidate = false
            } else {
                if (isValidate != false)
                    isValidate = true
                item.input.error = null
            }
        } ?: kotlin.run {
            item.input.error = getString(R.string.Global_field_empty)
            isValidate = false
        }
    }
    return isValidate ?: true
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
