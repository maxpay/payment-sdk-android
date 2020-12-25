package com.maxpay.testappmaxpay.ui

import android.app.Application
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.core.MyAndroidViewModel
import com.maxpay.sdk.data.MaxpayCallback
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.AvailableFields
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.MaxpaySignatureData
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.utils.DateInterface
import com.maxpay.testappmaxpay.model.ProductItemtUI
import com.maxpay.testappmaxpay.ui.state.MainViewState
import com.maxpay.testappmaxpay.ui.state.MainViewStateImpl
import com.maxpay.testappmaxpay.utils.SignatureHelper
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

    private val dateInterface: DateInterface by inject()

    private val _viewState = MainViewStateImpl()
    val viewState: MainViewState
        get() = _viewState

    init {
        if (_viewState.settings.value == null)
            _viewState.settings.value = MaxpayPaymentData(currency = Currency.getInstance(Locale.getDefault()), amount = 0.0F, transactionId = "Pay${dateInterface.getCurrentTimeStamp()}")

        if (_viewState.maxPayAvailableFields.value == null)
            _viewState.maxPayAvailableFields.value = AvailableFields(showBillingAddressLayout = false)
    }

    fun addToBasket(item: ProductItemtUI) {
        _viewState.listOfProducts.value?.let { list ->
            list.find { it.id == item.id }?.let {
                it.count += 1
            }?: kotlin.run {
                list.add(item)
            }
        }?: kotlin.run {
            viewState.listOfProducts.value = mutableListOf(item)
        }
        calculateProducts()
    }

    fun removeFromBasket(item: ProductItemtUI) {
        _viewState.listOfProducts.value?.let { list ->
            list.remove(item)
        }
        calculateProducts()
    }

    fun changeBasketItemCount(item: ProductItemtUI) {
        _viewState.listOfProducts.value?.let { list ->
            list.find { it.id == item.id }?.let {
                it.count = item.count
            }
        }
        calculateProducts()
    }

    private fun calculateProducts() {
        var count = 0
        var price = 0.0F
        _viewState.listOfProducts.value?.forEach {
            count += it.count
            price += it.count*it.price
        }
        _viewState.countProducts.value = count
        _viewState.fullPrice.value = price
    }

    fun payWithSDK() {
        val sdk: SDKFacade = SdkFacadeImpl(
            MaxPayInitData(
                apiVersion = 1,
                fieldsToShow = _viewState.maxPayAvailableFields.value,
                publicKey = _viewState.pk.value ?: "pkLive_HzmqN88yqNwwzuCRBgboOIvVOiNAX09x",
                theme = _viewState.maxPayTheme.value ?: null
            )
        )

        _viewState.settings.value?.let {
            it.transactionId = "Pay${dateInterface.getCurrentTimeStamp()}"
            when (it.transactionType) {
                TransactionType.SALE3D -> {
                    it.redirectUrl = "https://callbacks.envlog.net/shopEcho.php"
                    it.callBackUrl = "https://callbacks.envlog.net/callback.php"
                }
            }
            if (it.firstName.isNullOrEmpty())
                it.firstName = "John"
            if (it.lastName.isNullOrEmpty())
                it.lastName = "Doe"
            if (it.country.isNullOrEmpty())
                it.country = "USA"
            sdk.pay(it, object: MaxpayCallback {
                override fun onResponseSuccess(result: MaxpayResult?) {
                    _viewState.maxpayResult.value = result
                }

                override fun onResponceError(result: MaxpayResult?) {
                    _viewState.maxpayResult.value = result
                }

                override fun onNeedCalculateSignature(dataForSignature: MaxpaySignatureData?,
                                                      signatureCalback: (String)-> Unit) {
                    Thread {
                        Thread.sleep(5000)
                        dataForSignature?.let { it1 ->
                            val signature =
                                SignatureHelper("sklive_wbkz4pc670ajfywc9st0ioajc07cesok").getHashOfRequest(
                                    it1
                                )
                            signatureCalback.invoke(signature)
                        }
                    }.start()


                }

            })
        }
    }
}
