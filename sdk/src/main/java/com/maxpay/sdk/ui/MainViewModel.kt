package com.maxpay.sdk.ui

import android.app.Application
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.request.AuthPayment
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.ui.state.PaymentViewState
import com.maxpay.sdk.ui.state.PaymentViewStateImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

        private val repository: MaxPayRepository by inject()

    private val _viewState = PaymentViewStateImpl()
    val viewState: PaymentViewState
        get() = _viewState


    fun pay(salePayment: SalePayment) {
        repository.paySale(salePayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _viewState.salePaymentResponse.value = it
                },
                onError = {
                }
            ).addTo(disposables)
    }

    fun payAuth3D(salePayment: AuthPayment) {
        repository.payAuth3D(salePayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _viewState.authPaymentResponse.value = it
                },
                onError = {
                }
            ).addTo(disposables)
    }


}
