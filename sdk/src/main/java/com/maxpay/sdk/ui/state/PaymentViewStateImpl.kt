package com.maxpay.sdk.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.PayInitData
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent

internal class PaymentViewStateImpl : PaymentViewState {
    override val salePaymentResponse: SingleLiveEvent<BaseResponse> = SingleLiveEvent()
    override val authPaymentResponse: SingleLiveEvent<BaseResponse> = SingleLiveEvent()
    override val savedSomething: MutableLiveData<String> = MutableLiveData()
    override val payInitData: MutableLiveData<PayInitData> = MutableLiveData()
    override val isFromWebView: MutableLiveData<Boolean> = MutableLiveData()

    override val tmpPaymentData: MutableLiveData<SalePayment> = MutableLiveData()
    override val payPaymentInfo: MutableLiveData<PayPaymentInfo> = MutableLiveData()
}