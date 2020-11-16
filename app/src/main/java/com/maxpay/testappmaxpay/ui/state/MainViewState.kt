package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.model.MaxPayTheme
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.utils.SingleLiveEvent
import com.maxpay.testappmaxpay.model.ProductItemtUI

interface MainViewState {
     val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>>
     val countProducts: MutableLiveData<Int>
     val fullPrice: MutableLiveData<Float>

     val settings: MutableLiveData<MaxpayPaymentData>
     val maxpayResult: SingleLiveEvent<MaxpayResult>
     val maxPayTheme: MutableLiveData<MaxPayTheme>
     val pk: MutableLiveData<String>
}