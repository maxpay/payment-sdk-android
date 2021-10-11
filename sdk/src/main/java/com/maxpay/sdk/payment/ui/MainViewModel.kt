package com.maxpay.sdk.payment.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.maxpay.sdk.payment.core.MyAndroidViewModel
import com.maxpay.sdk.payment.data.PayResult
import com.maxpay.sdk.payment.datamodule.repository.PayRepository
import com.maxpay.sdk.payment.model.PayPaymentInfo
import com.maxpay.sdk.payment.model.request.PaymentDto
import com.maxpay.sdk.payment.model.request.TransactionType
import com.maxpay.sdk.payment.model.response.ResponseStatus
import com.maxpay.sdk.payment.ui.navigation.SDKNavigation
import com.maxpay.sdk.payment.ui.navigation.ThreeDSNavigation
import com.maxpay.sdk.payment.ui.state.PaymentViewState
import com.maxpay.sdk.payment.ui.state.PaymentViewStateImpl
import com.maxpay.sdk.payment.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


internal class MainViewModel(
    application: Application
) : MyAndroidViewModel(application), KoinComponent {

    private val repository: PayRepository by inject()
    private val ipHelper: IpHelper by inject()

    private val _viewState = PaymentViewStateImpl()
    val viewState: PaymentViewState
        get() = _viewState

    private val _mainNavigation = SingleLiveEvent<SDKNavigation>()
    val mainNavigation: SingleLiveEvent<SDKNavigation>
        get() = _mainNavigation

    fun prepareForPayment(
        activity: Activity?, paymentInfo: PayPaymentInfo, cardNumber: String,
        expMonth: String, expYear: String, cvv: String, cardHolder: String
    ) {
        state.value = StateEnum.UNITERUPTEDLOADING
        val payment = PaymentDto(
            apiVersion = _viewState.payInitData.value?.apiVersion,
            transactionId = paymentInfo.transactionId,
            transactionType = paymentInfo.transactionType,
            amount = paymentInfo.amount,
            currency = paymentInfo.currency.currencyCode,
            cardNumber = cardNumber,
            cardExpMonth = expMonth,
            cardExpYear = expYear,
            cvv = cvv,
            firstName = paymentInfo.firstName?.takeIf { !it.isEmpty() } ?: " ",
            lastName = paymentInfo.lastName?.takeIf { !it.isEmpty() } ?: " ",
            cardHolder = cardHolder,
            address = paymentInfo.address,
            city = paymentInfo.city,
            zip = paymentInfo.zip,
            country = paymentInfo.country,
            userPhone = paymentInfo.userPhone,
            userEmail = paymentInfo.userEmail,
            userIp = ipHelper.getUserIp(),
            publicKey = _viewState.payInitData.value?.publicKey,
            date_of_birth = paymentInfo.birthday,
            merchantUserID = paymentInfo.merchantInfo?.merchantUserID,
            merchantDomainName = paymentInfo.merchantInfo?.merchantDomainName,
            merchantAffiliateID = paymentInfo.merchantInfo?.merchantAffiliateID,
            merchantProductName = paymentInfo.merchantInfo?.merchantProductName,
            merchantDescriptor = paymentInfo.merchantInfo?.merchantDescriptor,
            merchantPhone = paymentInfo.merchantInfo?.merchantPhone,
            midReference = paymentInfo.merchantInfo?.midReference
        )
        if (!paymentInfo.sale3dRedirectUrl.isNullOrEmpty() && !paymentInfo.sale3dCallBackUrl.isNullOrEmpty()) {
            payment.redirectUrl = paymentInfo.sale3dRedirectUrl
            payment.callBackUrl = paymentInfo.sale3dCallBackUrl
        }
        _viewState.tmpPaymentDtoData.value = payment
        sendSignatureRequestData(activity, payment.generateDataForSignature())
    }

    fun pay(signature: String) {
        viewState.tmpPaymentDtoData.value?.let { payment ->
            payment.signature = signature

            repository.pay(payment, viewState.payInitData.value?.paymentGateway)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        if (it.status == ResponseStatus.success) {
                            state.value = StateEnum.COMPLETE
                        }

                        when (payment.transactionType) {

                            TransactionType.AUTH3D, TransactionType.SALE3D -> {
                                _viewState.authPaymentResponse.value = it
                            }

                            else -> {
                                _viewState.salePaymentResponse.value = it
                            }
                        }
                        _viewState.tmpPaymentDtoData.value = null
                    },
                    onError = {
                        _viewState.tmpPaymentDtoData.value = null
                        errorMessage = "${it.message}"
                        state.postValue(StateEnum.ERROR)
                    }
                ).addTo(disposables)
        }
    }

    fun sendBroadcastResult(activity: Activity?, data: PayResult?) {
        activity?.finish()
        val intent = Intent(Constants.PAY_CALLBACK_BROADCAST)
        data?.let {
            intent.setPackage(activity?.packageName)
            intent.putExtra(Constants.Companion.Extra.PAY_BROADCAST_DATA, data)
        }
        activity?.sendBroadcast(intent)
    }

    private fun sendSignatureRequestData(activity: Activity?, data: String) {
        val intent = Intent(Constants.PAY_CALLBACK_BROADCAST_SIGNATURE)
        data.let {
            intent.setPackage(activity?.packageName)
            intent.putExtra(Constants.Companion.Extra.PAY_BROADCAST_SIGNATURE_DATA, data)
        }
        activity?.sendBroadcast(intent)
    }

    fun navigateThreeDS() {
        _mainNavigation.value = ThreeDSNavigation
    }

}
