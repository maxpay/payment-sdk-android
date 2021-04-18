package com.maxpay.testappmaxpay.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observeCommand(data: LiveData<T>, action: (T?) -> Unit) {
    data.observe(this, Observer(action))
}

fun <T> LifecycleOwner.observeCommandSafety(data: LiveData<T>, action: (T) -> Unit) {
    observeCommand(data) {
        it?.also(action)
    }
}