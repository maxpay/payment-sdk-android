package com.maxpay.sdk.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.response.BaseResponse

class PaymentViewStateImpl : PaymentViewState {
    override val salePaymentResponse: MutableLiveData<Any> = MutableLiveData()
    override val authPaymentResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    override val savedSomething: MutableLiveData<String> = MutableLiveData()
    override val maxpayInitData: MutableLiveData<MaxPayInitData> = MutableLiveData()
    override val isFromWebView: MutableLiveData<Boolean> = MutableLiveData()
}