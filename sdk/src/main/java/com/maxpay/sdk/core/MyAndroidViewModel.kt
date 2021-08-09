package com.maxpay.sdk.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.utils.StateEnum
import io.reactivex.disposables.CompositeDisposable

internal open class MyAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val disposables = CompositeDisposable()
    var errorMessage: String? = null
    open val state = MutableLiveData<StateEnum>(StateEnum.NONE)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}