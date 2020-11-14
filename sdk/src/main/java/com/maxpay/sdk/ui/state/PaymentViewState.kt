package com.maxpay.sdk.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent

interface PaymentViewState {
     val salePaymentResponse: MutableLiveData<BaseResponse>
     val authPaymentResponse: SingleLiveEvent<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val maxpayInitData: MutableLiveData<MaxPayInitData>
     val isFromWebView: MutableLiveData<Boolean>
}