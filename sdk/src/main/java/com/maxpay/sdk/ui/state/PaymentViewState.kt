package com.maxpay.sdk.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.response.BaseResponse

interface PaymentViewState {
     val salePaymentResponse: MutableLiveData<Any>
     val authPaymentResponse: MutableLiveData<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val isFromWebView: MutableLiveData<Boolean>
}