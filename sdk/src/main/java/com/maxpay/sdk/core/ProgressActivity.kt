package com.maxpay.sdk.core

import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.maxpay.sdk.utils.CheckPermissionsUtils

internal abstract class ProgressActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    abstract fun getProgressBar(): ProgressBar

    private var runnableAction: Runnable? = null
    private var cancelAction: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    internal fun checkPermission(
        permitAction: Runnable,
        cancelAction: Runnable?,
        vararg permissions: String
    ) {
        this.runnableAction = permitAction
        this.cancelAction = cancelAction
        if (CheckPermissionsUtils.checkPermission(this, permissions))
            permitAction.run()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        CheckPermissionsUtils.handlePermissions(
            requestCode,
            grantResults,
            runnableAction!!,
            cancelAction
        )
    }

    internal fun postResume() {
        super.onPostResume()
    }

}