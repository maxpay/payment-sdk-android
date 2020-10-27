package com.maxpay.testappmaxpay.ui

import android.app.Application
import com.maxpay.sdk.core.MyAndroidViewModel
import org.koin.core.KoinComponent

class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

//    private val repository: MaxPayRepository by inject()

//    private val _viewState = PaymentViewStateImpl()
//    val viewState: PaymentViewState
//        get() = _viewState


//    fun pay(salePayment: SalePayment) {
//        repository.paySale(salePayment)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {
//                    _viewState.salePaymentResponse.value = it
//                },
//                onError = {
//                }
//            ).addTo(disposables)
//    }
//
//    fun payAuth3D(salePayment: SalePayment) {
//        state.value = StateEnum.LOADING
//        viewState.savedSomething.value = "Something savedPay auth3d ${salePayment.transactionId}"
//        repository.payAuth3D(salePayment)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {
//                    if (it.status == ResponseStatus.success) {
//                        state.value = StateEnum.COMPLETE
//                        _viewState.authPaymentResponse.value = it
//                    } else {
//                        errorMessage = "${it.message}"
//                        state.postValue(StateEnum.ERROR)
//                    }
//                },
//                onError = {
//                    errorMessage = "${it.message}"
//                    state.postValue(StateEnum.ERROR)
//                }
//            ).addTo(disposables)
//    }


}
