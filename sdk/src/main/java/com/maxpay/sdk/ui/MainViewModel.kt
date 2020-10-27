package com.maxpay.sdk.ui

import android.app.Application
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.text.format.Formatter
import androidx.core.content.ContextCompat.getSystemService
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.ResponseStatus
import com.maxpay.sdk.ui.state.PaymentViewState
import com.maxpay.sdk.ui.state.PaymentViewStateImpl
import com.maxpay.sdk.utils.DateInterface
import com.maxpay.sdk.utils.IpHelper
import com.maxpay.sdk.utils.StateEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

    private val repository: MaxPayRepository by inject()
    private val dateInterface: DateInterface by inject()
    private val ipHelper: IpHelper by inject()

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

    fun payAuth3D(salePayment: SalePayment) {
        state.value = StateEnum.LOADING
        viewState.savedSomething.value = "Something savedPay auth3d ${salePayment.transactionId}"
        repository.payAuth3D(salePayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.status == ResponseStatus.success) {
                        state.value = StateEnum.COMPLETE
                        _viewState.authPaymentResponse.value = it
                    } else {
                        errorMessage = "${it.message}"
                        state.postValue(StateEnum.ERROR)
                    }
                },
                onError = {
                    errorMessage = "${it.message}"
                    state.postValue(StateEnum.ERROR)
                }
            ).addTo(disposables)
    }

    fun payAuth3D(paymentData: MaxpayPaymentData) {

        state.value = StateEnum.LOADING
        val authPayment = SalePayment(
            _viewState.maxpayInitData.value?.apiVersion,
            _viewState.maxpayInitData.value?.accountName,
            _viewState.maxpayInitData.value?.accountPassword,
            "AUTH3В${dateInterface.getCurrentTimeStamp()}",
            paymentData.transactionType,
            paymentData.amount,
            "EUR",
            "4012000300001003", // TODO card data we must get from SDK
            "05",
            "2019",
            "111",
            paymentData.firstName,
            paymentData.lastName,
            paymentData.cardHolder,
            paymentData.address,
            paymentData.city,
            paymentData.state,
            paymentData.zip,
            paymentData.country,
            paymentData.userPhone,
            paymentData.userEmail,
            ipHelper.getUserIp()
        )
        repository.payAuth3D(authPayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.status == ResponseStatus.success) {
                        state.value = StateEnum.COMPLETE
                        _viewState.authPaymentResponse.value = it
                    } else {
                        errorMessage = "${it.message}"
                        state.postValue(StateEnum.ERROR)
                    }
                },
                onError = {
                    errorMessage = "${it.message}"
                    state.postValue(StateEnum.ERROR)
                }
            ).addTo(disposables)
    }


}
