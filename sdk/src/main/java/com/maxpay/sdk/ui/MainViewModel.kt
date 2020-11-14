package com.maxpay.sdk.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.request.SalePayment
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
            firstName = paymentData.firstName?.takeIf { !it.isEmpty() }?: " ",
            lastName = paymentData.lastName?.takeIf { !it.isEmpty() }?: " ",
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
                        when(authPayment.transactionType) {
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

//    $resulting = strtolower('customproduct=[{"productId":"12345","productType":"fixedProduct","productName":"Product name","currency":"usd","amount":100}]|key=pkTest_c4tgzRjQBO4Fa4d7f2OoJkIutFMGiCCC|skTest_aKuKHhan1arfwrYBXSUja46ax4qrA111');
//    $signature = hash("sha256", $resulting);
//    strtolower('customproduct=[{"productId":"12345","productType":"fixedProduct","productName":"Product name","currency":"usd","amount":100}]|
//    key=pkTest_c4tgzRjQBO4Fa4d7f2OoJkIutFMGiCCC|skTest_aKuKHhan1arfwrYBXSUja46ax4qrA111');

//    <form method="post" action="https://hpp.maxpay.com/hpp">
//    <input type='hidden' name="key" value="pkTest_c4tgzRjQBO4Fa4d7f2OoJkIutFMGiCCC">
//    <input type='hidden' name="customproduct" value="[{'productId':'1','productType':'fixedProduct','productName':'Product name','currency':'USD','amount':100}]">
//    <input type='hidden' name="signature" value="put_a_signature_value_here">
//    <input type='submit' name="Pay" value="Pay">
//    </form>

//    ?php

//key - it's Public key from merchant portal (my.maxpay.com -> Payment pages -> General -> API keys).
//The last parameter is Private key from merchant portal (my.maxpay.com -> Payment pages -> General -> API keys).



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
