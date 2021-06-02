package com.maxpay.testappmaxpay.ui

import android.app.Application
import android.content.Context
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.data.PayCallback
import com.maxpay.sdk.data.PayResult
import com.maxpay.sdk.model.*
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.sdk.utils.DateInterface
import com.maxpay.testappmaxpay.core.MyAndroidViewModel
import com.maxpay.testappmaxpay.model.ProductItemtUI
import com.maxpay.testappmaxpay.ui.state.MainViewState
import com.maxpay.testappmaxpay.ui.state.MainViewStateImpl
import com.maxpay.testappmaxpay.utils.SignatureHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

    private val dateInterface: DateInterface by inject()

    private val _viewState = MainViewStateImpl()
    val viewState: MainViewState
        get() = _viewState

    init {
        if (_viewState.settings.value == null)
            _viewState.settings.value = PayPaymentInfo(currency = Currency.getInstance(Locale.getDefault()), amount = 0.0F, transactionId = "Pay${dateInterface.getCurrentTimeStamp()}")

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

    fun payWithSDK(context: Context) {
        val sdk: SDKFacade = SdkFacadeImpl.instance

        _viewState.settings.value?.let {
            it.transactionId = "Pay${dateInterface.getCurrentTimeStamp()}"
            when (it.transactionType) {
                TransactionType.SALE3D -> {
                    it.sale3dRedirectUrl = "https://callbacks.envlog.net/shopEcho.php"
                    it.sale3dCallBackUrl = "https://callbacks.envlog.net/callback.php"
                }
                TransactionType.AUTH3D -> {
                    it.auth3dRedirectUrl = "https://callbacks.envlog.net/shopEcho.php"
                }
            }
            val data = PayInitInfo(
                apiVersion = 1,
                fieldsToShow = _viewState.maxPayAvailableFields.value,
                publicKey = _viewState.pk.value ?: "pkLive_HzmqN88yqNwwzuCRBgboOIvVOiNAX09x",
                theme = _viewState.payTheme.value ?: null,
                paymentGateway = PayGatewayInfo.SANDBOX
            )
            sdk.pay(context, data, it, object: PayCallback {
                override fun onResponseResult(result: PayResult?) {
                    _viewState.payResult.value = result
                }

                override fun onNeedCalculateSignature(dataForSignature: PaySignatureInfo?,
                                                      signatureCalback: (String)-> Unit) {
                    Thread {
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
