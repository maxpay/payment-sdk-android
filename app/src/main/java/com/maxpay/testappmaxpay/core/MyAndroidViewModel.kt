package com.maxpay.testappmaxpay.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.utils.StateEnum

open class MyAndroidViewModel(application: Application) : AndroidViewModel(application) {

    var errorMessage: String? = null
    open val state = MutableLiveData<StateEnum>(StateEnum.NONE)

    override fun onCleared() {
        super.onCleared()
//        disposables.clear()
    }
}