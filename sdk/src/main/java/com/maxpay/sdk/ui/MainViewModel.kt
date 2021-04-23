package com.maxpay.sdk.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.MaxpaySignatureData
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.model.request.toMaxpaySignatureData
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


internal class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

    private val repository: MaxPayRepository by inject()
    private val ipHelper: IpHelper by inject()

    private val _viewState = PaymentViewStateImpl()
    val viewState: PaymentViewState
        get() = _viewState

    private val _mainNavigation = SingleLiveEvent<SDKNavigation>()
    val mainNavigation: SingleLiveEvent<SDKNavigation>
        get() = _mainNavigation

    fun prepareForPayment(activity: Activity?, paymentData: MaxpayPaymentData, cardNumber: String,
                          expMonth: String, expYear: String, cvv: String, cardHolder: String) {
        state.value = StateEnum.UNITERUPTEDLOADING
        val payment = SalePayment(
            apiVersion = _viewState.maxpayInitData.value?.apiVersion,
            transactionId = paymentData.transactionId,
            transactionType = paymentData.transactionType,
            amount = paymentData.amount,
            currency = paymentData.currency.currencyCode,
            cardNumber = cardNumber,
            cardExpMonth = expMonth,
            cardExpYear = expYear,
            cvv = cvv,
            firstName = paymentData.firstName?.takeIf { !it.isEmpty() } ?: " ",
            lastName = paymentData.lastName?.takeIf { !it.isEmpty() } ?: " ",
            cardHolder = cardHolder,
            address = paymentData.address,
            city = paymentData.city,
            zip = paymentData.zip,
            country = paymentData.country,
            userPhone = paymentData.userPhone,
            userEmail = paymentData.userEmail,
            userIp = ipHelper.getUserIp(),
            publicKey = _viewState.maxpayInitData.value?.publicKey,
            date_of_birth = paymentData.birthday
        )
        if (!paymentData.redirectUrl.isNullOrEmpty() && !paymentData.callBackUrl.isNullOrEmpty()) {
            payment.redirectUrl = paymentData.redirectUrl
            payment.callBackUrl = paymentData.callBackUrl
        }
        _viewState.tmpPaymentData.value = payment
        sendBroadcastData(activity, payment.toMaxpaySignatureData())
    }

    fun pay(signature: String) {
        viewState.tmpPaymentData.value?.let {payment ->
            payment.signature = signature
            repository.pay(payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.status == ResponseStatus.success) {
                            state.value = StateEnum.COMPLETE
                            when (payment.transactionType) {
                                TransactionType.AUTH3D, TransactionType.SALE3D ->
                                    _viewState.authPaymentResponse.value = it
                                else -> _viewState.salePaymentResponse.value = it
                            }

                        } else {
                            when (payment.transactionType) {
                                TransactionType.AUTH3D, TransactionType.SALE3D ->
                                    _viewState.authPaymentResponse.value = it
                                else -> _viewState.salePaymentResponse.value = it
                            }
                        }
                        _viewState.tmpPaymentData.value = null
                    },
                    onError = {
                        _viewState.tmpPaymentData.value = null
                        errorMessage = "${it.message}"
                        state.postValue(StateEnum.ERROR)
                    }
                ).addTo(disposables)
        }
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

    fun sendBroadcastData(activity: Activity?, data: MaxpaySignatureData?) {
        val intent = Intent(Constants.MAXPAY_CALLBACK_BROADCAST_SIGNATURE)
        data?.let {
            intent.setPackage(activity?.packageName)
            intent.putExtra(Constants.Companion.Extra.MAXPAY_BROADCAST_SIGNATURE_DATA, data)
        }
        activity?.sendBroadcast(intent)
    }

    fun navigateThreeDS() {
        _mainNavigation.value = ThreeDSNavigation
    }

}
