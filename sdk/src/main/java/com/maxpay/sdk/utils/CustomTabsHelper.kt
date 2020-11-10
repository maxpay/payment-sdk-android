package com.maxpay.sdk.utils

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class CustomTabsHelper: KoinComponent {

    private var mClient: CustomTabsClient? = null
    private val context: Context by inject()

    init {


    }

    fun getTabs(color: Int): CustomTabsIntent {
//        CustomTabsClient.bindCustomTabsService(
//            context,
//            context.packageName,
//            object : CustomTabsServiceConnection() {
//                override fun onCustomTabsServiceConnected(
//                    name: ComponentName,
//                    client: CustomTabsClient
//                ) {
//                    // mClient is now valid.
//                    mClient = client
//                }
//
//                override fun onServiceDisconnected(name: ComponentName) {
//                    // mClient is no longer valid. This also invalidates sessions.
//                    mClient = null
//                }
//            })
//
//        mClient?.warmup(0)
//
//        val session: CustomTabsSession? = mClient?.newSession(CustomTabsCallback())
//        session?.mayLaunchUrl(Uri.parse(Constants.Companion.Links.MAXPAY_PRIVACY), null, null)

        return CustomTabsIntent.Builder()
            .setToolbarColor(color)
            .setShowTitle(true)
            .build()

    }


}