package com.maxpay.sdk.utils

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.maxpay.sdk.R

internal object AlternativeDialogFactory {
    fun showAlert(
        context: Context,
        title: String,
        message: String,
        btnOkText: String,
        btnCancelText: String,
        onOk: () -> Unit,
        onCancel: (() -> Unit)?
    ) {
        AlertDialog.Builder(context, R.style.DialogTheme)
            .setCustomTitle(TextView(context).apply {
                textSize = 20f
                typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setPadding(60, 40, 0, 0)
                }
                text = title
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
            })
            .setMessage(message)
            .setPositiveButton(btnOkText) { _, _ ->
                onOk.invoke()
            }
            .setNegativeButton(btnCancelText) { _, _ -> onCancel?.invoke() }
            .setCancelable(false)
            .show()
    }

    fun showAlertWithOneButton(
        context: Context,
        title: String,
        message: String,
        btnOkText: String,
        onOk: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(btnOkText) { _, _ ->
                onOk?.invoke()
            }
            .setCancelable(false)
            .show()
    }

    fun Fragment.showSnackBar(
        message: String,
        duration: Int,
        action: String? = null,
        listener: View.OnClickListener? = null
    ) = view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).setAction(action, listener).show()
    }
}