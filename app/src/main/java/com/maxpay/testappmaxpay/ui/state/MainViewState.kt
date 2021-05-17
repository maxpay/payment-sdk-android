package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.data.PayResult
import com.maxpay.sdk.model.AvailableFields
import com.maxpay.sdk.model.PayTheme
import com.maxpay.sdk.model.PayPaymentInfo
import com.maxpay.sdk.utils.SingleLiveEvent
import com.maxpay.testappmaxpay.model.ProductItemtUI

interface MainViewState {
     val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>>
     val countProducts: MutableLiveData<Int>
     val fullPrice: MutableLiveData<Float>

     val settings: MutableLiveData<PayPaymentInfo>
     val payResult: SingleLiveEvent<PayResult>
     val payTheme: MutableLiveData<PayTheme>
     val maxPayAvailableFields: MutableLiveData<AvailableFields>
     val pk: MutableLiveData<String>
}