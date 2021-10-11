package com.maxpay.sdk.payment.utils
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.*

internal object CheckPermissionsUtils {

    fun checkPermission(activity: Activity, permissions: Array<out String>): Boolean {
        val requestPermissionList = ArrayList<String>()
        for (permission in permissions)
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                requestPermissionList.add(permission)
        val granted = requestPermissionList.size == 0
        if (!granted) {
            val array = arrayOfNulls<String>(requestPermissionList.size)
            ActivityCompat.requestPermissions(activity, requestPermissionList.toTypedArray(),
                Constants.PERMISSION
            )
        }
        return granted
    }

    fun handlePermissions(requestCode: Int, grantResults: IntArray, runnable: Runnable, cancelAction: Runnable?) {
        if (requestCode != Constants.PERMISSION) return
        var handled = grantResults.isNotEmpty()
        if (handled)
            for (permission in grantResults)
                if (permission != PackageManager.PERMISSION_GRANTED)
                    handled = false
        if (handled)
            runnable.run()
        else cancelAction?.run()
    }

}