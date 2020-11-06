package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.testappmaxpay.model.ProductItemtUI

class MainViewStateImpl : MainViewState {
    override val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>> = MutableLiveData()
    override val countProducts: MutableLiveData<Int> = MutableLiveData()
    override val fullPrice: MutableLiveData<Float> = MutableLiveData()
    override val settings: MutableLiveData<MaxpayPaymentData> = MutableLiveData()
}