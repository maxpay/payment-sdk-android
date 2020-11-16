package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxPayTheme
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.sdk.utils.SingleLiveEvent
import com.maxpay.testappmaxpay.model.ProductItemtUI

class MainViewStateImpl : MainViewState {
    override val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>> = MutableLiveData()
    override val countProducts: MutableLiveData<Int> = MutableLiveData()
    override val fullPrice: MutableLiveData<Float> = MutableLiveData()
    override val settings: MutableLiveData<MaxpayPaymentData> = MutableLiveData()

    override val maxpayResult: SingleLiveEvent<MaxpayResult> = SingleLiveEvent()
    override val maxPayTheme: MutableLiveData<MaxPayTheme> = MutableLiveData()

    override val pk: MutableLiveData<String> = MutableLiveData()
}