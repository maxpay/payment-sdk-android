package com.maxpay.sdk.payment.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal fun <T> LifecycleOwner.observeCommand(data: LiveData<T>, action: (T?) -> Unit) {
    data.observe(this, Observer(action))
}

internal fun <T> LifecycleOwner.observeCommandSafety(data: LiveData<T>, action: (T) -> Unit) {
    observeCommand(data) {
        it?.also(action)
    }
}