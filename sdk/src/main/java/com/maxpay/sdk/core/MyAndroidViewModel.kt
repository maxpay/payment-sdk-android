package com.maxpay.sdk.core

import android.app.Application
//import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.utils.StateEnum
import io.reactivex.disposables.CompositeDisposable

open class MyAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val disposables = CompositeDisposable()
//    val errorMessage = ObservableField<String>("")
    open val state = MutableLiveData<StateEnum>(StateEnum.NONE)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}