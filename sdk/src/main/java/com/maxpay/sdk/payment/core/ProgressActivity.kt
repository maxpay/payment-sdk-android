package com.maxpay.sdk.payment.core

import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

internal abstract class ProgressActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    abstract fun getProgressBar(): ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    internal fun postResume() {
        super.onPostResume()
    }

}