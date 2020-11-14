package com.maxpay.sdk.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent

class PaymentViewStateImpl : PaymentViewState {
    override val salePaymentResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    override val authPaymentResponse: SingleLiveEvent<BaseResponse> = SingleLiveEvent()
    override val savedSomething: MutableLiveData<String> = MutableLiveData()
    override val maxpayInitData: MutableLiveData<MaxPayInitData> = MutableLiveData()
    override val isFromWebView: MutableLiveData<Boolean> = MutableLiveData()
}