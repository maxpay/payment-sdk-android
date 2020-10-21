package com.maxpay.sdk.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.response.BaseResponse

class PaymentViewStateImpl : PaymentViewState {
    override val salePaymentResponse: MutableLiveData<Any> = MutableLiveData()
    override val authPaymentResponse: MutableLiveData<BaseResponse> = MutableLiveData()
}