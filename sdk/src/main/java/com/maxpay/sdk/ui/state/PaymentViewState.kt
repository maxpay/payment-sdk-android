package com.maxpay.sdk.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent

internal interface PaymentViewState {
     val salePaymentResponse: SingleLiveEvent<BaseResponse>
     val authPaymentResponse: SingleLiveEvent<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val maxpayInitData: MutableLiveData<MaxPayInitData>
     val isFromWebView: MutableLiveData<Boolean>
    val tmpPaymentData: MutableLiveData<SalePayment>
}