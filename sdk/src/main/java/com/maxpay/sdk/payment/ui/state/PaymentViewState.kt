package com.maxpay.sdk.payment.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.payment.model.PayInitInfo
import com.maxpay.sdk.payment.model.PayPaymentInfo
import com.maxpay.sdk.payment.model.request.PaymentDto
import com.maxpay.sdk.payment.model.response.BaseResponse
import com.maxpay.sdk.payment.utils.SingleLiveEvent

internal interface PaymentViewState {
     val salePaymentResponse: SingleLiveEvent<BaseResponse>
     val authPaymentResponse: SingleLiveEvent<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val payInitData: MutableLiveData<PayInitInfo>
     val isFromWebView: MutableLiveData<Boolean>
     val tmpPaymentDtoData: MutableLiveData<PaymentDto>
     val payPaymentInfo: MutableLiveData<PayPaymentInfo>
}