package com.maxpay.sdk.payment

import android.content.Context
import com.maxpay.sdk.payment.data.PayCallback
import com.maxpay.sdk.payment.model.PayInitInfo
import com.maxpay.sdk.payment.model.PayPaymentInfo

/**
 * Prepare payment information, and start payment activity
 *
 * @param data Initialisation data info [PayInitInfo]
 * @param pay Payment information [PayPaymentInfo]
 * @param callback Here you will get signature calculation callback, and response result [PayCallback]
 */
interface SDKFacade {
    fun pay(context: Context?, data: PayInitInfo, pay: PayPaymentInfo, callback: PayCallback)

    companion object {
        val instance: SDKFacade by lazy { SdkFacadeImpl.HOLDER.INSTANCE }
    }

}