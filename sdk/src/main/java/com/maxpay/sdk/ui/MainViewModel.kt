package com.maxpay.sdk.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.maxpay.sdk.SignatureHelper
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.ThreeDPayment
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.model.response.ResponseStatus
import com.maxpay.sdk.ui.navigation.SDKNavigation
import com.maxpay.sdk.ui.navigation.ThreeDSNavigation
import com.maxpay.sdk.ui.state.PaymentViewState
import com.maxpay.sdk.ui.state.PaymentViewStateImpl
import com.maxpay.sdk.utils.*
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

    private val _mainNavigation = SingleLiveEvent<SDKNavigation>()
    val mainNavigation: SingleLiveEvent<SDKNavigation>
        get() = _mainNavigation

//    fun pay3D(paymentData: MaxpayPaymentData) {
//        state.value = StateEnum.LOADING
//        val authPayment = ThreeDPayment(
//            merchantAccount =_viewState.maxpayInitData.value?.accountName,
//            merchantPassword = _viewState.maxpayInitData.value?.accountPassword,
//            apiVersion = _viewState.maxpayInitData.value?.apiVersion,
//            transactionId = "payment${dateInterface.getCurrentTimeStamp()}",
//            transactionType = paymentData.transactionType,
//            amount = paymentData.amount,
//            currency = paymentData.currency.currencyCode,
//            cardNumber = paymentData.cardNumber,
//            cardExpMonth = paymentData.expMonth,
//            cardExpYear = paymentData.expYear,
//            cvv = paymentData.cvv,
//            firstName = paymentData.firstName?.takeIf { !it.isEmpty() }?: " ",
//            lastName = paymentData.lastName?.takeIf { !it.isEmpty() }?: " ",
//            cardHolder = paymentData.cardHolder,
//            address = paymentData.address,
//            city = paymentData.city,
////            state = paymentData.state, //TODO We didn`t have field for state
//            zip = paymentData.zip,
//            country = paymentData.country,
//            userPhone = paymentData.userPhone,
//            userEmail = paymentData.userEmail,
//            userIp = ipHelper.getUserIp(),
//            callBackUrl = paymentData.callBackUrl,
//            redirectUrl = paymentData.redirectUrl
//        )
//        repository.pay3D(authPayment)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {
//                    if (it.status == ResponseStatus.success) {
//                        state.value = StateEnum.COMPLETE
//                        when(authPayment.transactionType){
//                            TransactionType.AUTH3D, TransactionType.SALE3D ->
//                                _viewState.authPaymentResponse.value = it
//                            else -> _viewState.salePaymentResponse.value = it
//                        }
//
//                    } else {
//                        when(authPayment.transactionType) {
//                            TransactionType.AUTH3D, TransactionType.SALE3D ->
//                                _viewState.authPaymentResponse.value = it
//                            else -> _viewState.salePaymentResponse.value = it
//                        }
//                    }
//                },
//                onError = {
//                    errorMessage = "${it.message}"
//                    state.postValue(StateEnum.ERROR)
//                }
//            ).addTo(disposables)
//    }

    fun pay(paymentData: MaxpayPaymentData) {
        state.value = StateEnum.UNITERUPTEDLOADING
        val payment = SalePayment(
            _viewState.maxpayInitData.value?.apiVersion,
            transactionId = "payment${dateInterface.getCurrentTimeStamp()}",
            transactionType = paymentData.transactionType,
            amount = paymentData.amount,
            currency = paymentData.currency.currencyCode,
//            cardNumber = "4012000300001003",//TODO paymentData.cardNumber,
            cardNumber = paymentData.cardNumber,
            cardExpMonth = paymentData.expMonth,
            cardExpYear = paymentData.expYear,
            cvv = paymentData.cvv,
            firstName = paymentData.firstName?.takeIf { !it.isEmpty() }?: " ",
            lastName = paymentData.lastName?.takeIf { !it.isEmpty() }?: " ",
            cardHolder = paymentData.cardHolder,
            address = paymentData.address,
            city = paymentData.city,
//            state = paymentData.state, //TODO We didn`t have field for state
            zip = paymentData.zip,
            country = paymentData.country,
            userPhone = paymentData.userPhone,
            userEmail = paymentData.userEmail,
            userIp = ipHelper.getUserIp(),
            publicKey = _viewState.maxpayInitData.value?.publicKey
        )
        if (!paymentData.redirectUrl.isNullOrEmpty() && !paymentData.callBackUrl.isNullOrEmpty()) {
            payment.redirectUrl = paymentData.redirectUrl
            payment.callBackUrl = paymentData.callBackUrl
        }
        payment.signature = SignatureHelper().getHashOfRequest(payment)
        repository.pay(payment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.status == ResponseStatus.success) {
                        state.value = StateEnum.COMPLETE
                        when(payment.transactionType){
                            TransactionType.AUTH3D, TransactionType.SALE3D ->
                                _viewState.authPaymentResponse.value = it
                            else -> _viewState.salePaymentResponse.value = it
                        }

                    } else {
                        when(payment.transactionType) {
                            TransactionType.AUTH3D, TransactionType.SALE3D ->
                                _viewState.authPaymentResponse.value = it
                            else -> _viewState.salePaymentResponse.value = it
                        }
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

    fun navigateThreeDS() {
        _mainNavigation.value = ThreeDSNavigation
    }


}
