package com.maxpay.sdk.payment.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.payment.model.PayInitInfo
import com.maxpay.sdk.payment.model.PayPaymentInfo
import com.maxpay.sdk.payment.model.request.SalePayment
import com.maxpay.sdk.payment.model.response.BaseResponse
import com.maxpay.sdk.payment.utils.SingleLiveEvent

internal class PaymentViewStateImpl : PaymentViewState {
    override val salePaymentResponse: SingleLiveEvent<BaseResponse> = SingleLiveEvent()
    override val authPaymentResponse: SingleLiveEvent<BaseResponse> = SingleLiveEvent()
    override val savedSomething: MutableLiveData<String> = MutableLiveData()
    override val payInitData: MutableLiveData<PayInitInfo> = MutableLiveData()
    override val isFromWebView: MutableLiveData<Boolean> = MutableLiveData()

    override val tmpPaymentData: MutableLiveData<SalePayment> = MutableLiveData()
    override val payPaymentInfo: MutableLiveData<PayPaymentInfo> = MutableLiveData()
}