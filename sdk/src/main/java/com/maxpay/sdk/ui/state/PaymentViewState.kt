package com.maxpay.sdk.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.PayInitData
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent

internal interface PaymentViewState {
     val salePaymentResponse: SingleLiveEvent<BaseResponse>
     val authPaymentResponse: SingleLiveEvent<BaseResponse>
     val savedSomething: MutableLiveData<String>
     val payInitData: MutableLiveData<PayInitData>
     val isFromWebView: MutableLiveData<Boolean>
     val tmpPaymentData: MutableLiveData<SalePayment>
     val payPaymentInfo: MutableLiveData<PayPaymentInfo>
}