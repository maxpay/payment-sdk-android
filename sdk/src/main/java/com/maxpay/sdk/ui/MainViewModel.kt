package com.maxpay.sdk.ui

import android.app.Activity
import android.app.Application
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.wifi.WifiManager
import android.text.format.Formatter
import androidx.core.content.ContextCompat.getSystemService
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.model.response.ResponseStatus
import com.maxpay.sdk.ui.state.PaymentViewState
import com.maxpay.sdk.ui.state.PaymentViewStateImpl
import com.maxpay.sdk.utils.Constants
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
            paymentData.currency.currencyCode,
            paymentData.cardNumber,
            "05",
            "2019",
            "111",
            firstName = paymentData.firstName,
            lastName = paymentData.lastName,
            cardHolder = paymentData.cardHolder,
            address = paymentData.address,
            city = paymentData.city,
            state = paymentData.state,
            zip = paymentData.zip,
            country = paymentData.country,
            userPhone = paymentData.userPhone,
            userEmail = paymentData.userEmail,
            userIp = ipHelper.getUserIp()
        )
        repository.payAuth3D(authPayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.status == ResponseStatus.success) {
                        state.value = StateEnum.COMPLETE
                        when(authPayment.transactionType){
                            TransactionType.AUTH3D, TransactionType.SALE3D ->
                                _viewState.authPaymentResponse.value = it
                            else -> _viewState.salePaymentResponse.value = it
                        }

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

    fun sendBroadcastResult(activity: Activity?, data: MaxpayResult?) {
        activity?.finish()
        val intent = Intent(Constants.MAXPAY_CALLBACK_BROADCAST)
        data?.let {
            intent.setPackage(activity?.packageName)
            intent.putExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_DATA, data)
        }
        activity?.sendBroadcast(intent)
    }


}
