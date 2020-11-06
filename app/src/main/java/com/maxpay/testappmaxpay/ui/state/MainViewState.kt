package com.maxpay.testappmaxpay.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.model.MaxPayInitData
import com.maxpay.sdk.model.MaxpayPaymentData
import com.maxpay.sdk.model.response.BaseResponse
import com.maxpay.testappmaxpay.model.ProductItemtUI

interface MainViewState {
     val listOfProducts: MutableLiveData<MutableList<ProductItemtUI>>
     val countProducts: MutableLiveData<Int>
     val fullPrice: MutableLiveData<Float>

     val settings: MutableLiveData<MaxpayPaymentData>
}