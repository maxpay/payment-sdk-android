package com.maxpay.sdk.utils

import androidx.browser.customtabs.*
import org.koin.core.component.KoinComponent

internal class CustomTabsHelper: KoinComponent {

    fun getTabs(color: Int): CustomTabsIntent =
        CustomTabsIntent.Builder()
            .setToolbarColor(color)
            .setShowTitle(true)
            .build()

}