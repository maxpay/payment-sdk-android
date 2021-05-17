package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.data.PayResult
import com.maxpay.sdk.model.AvailableFields
import com.maxpay.sdk.model.PayTheme
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.utils.SingleLiveEvent
import com.maxpay.testappmaxpay.model.ProductItemtUI

class MainViewStateImpl : MainViewState {
    override val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>> = MutableLiveData()
    override val countProducts: MutableLiveData<Int> = MutableLiveData()
    override val fullPrice: MutableLiveData<Float> = MutableLiveData()
    override val settings: MutableLiveData<PayPaymentInfo> = MutableLiveData()

    override val payResult: SingleLiveEvent<PayResult> = SingleLiveEvent()
    override val payTheme: MutableLiveData<PayTheme> = MutableLiveData()
    override val maxPayAvailableFields: MutableLiveData<AvailableFields> = MutableLiveData()

    override val pk: MutableLiveData<String> = MutableLiveData()
}