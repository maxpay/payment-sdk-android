package com.maxpay.sdk.payment.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.payment.model.PayInitInfo
import com.maxpay.sdk.payment.model.PayPaymentInfo
import com.maxpay.sdk.payment.model.request.SalePayment
import com.maxpay.sdk.payment.model.response.BaseResponse
import com.maxpay.sdk.payment.utils.SingleLiveEvent

internal interface PaymentViewState {
     val salePaymentResponse: SingleLiveEvent<BaseResponse>
     val authPaymentResponse: SingleLiveEvent<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val payInitData: MutableLiveData<PayInitInfo>
     val isFromWebView: MutableLiveData<Boolean>
     val tmpPaymentData: MutableLiveData<SalePayment>
     val payPaymentInfo: MutableLiveData<PayPaymentInfo>
}