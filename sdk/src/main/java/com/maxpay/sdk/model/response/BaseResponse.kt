package com.maxpay.sdk.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(

    val pareq: String,
    @SerializedName("acs_url")
    val accessUrl: String,
    val reference: String,
    val status: ResponseStatus,
    val code: Int?,
    val message: String?
//    "sessionid": "905714-fba-5f8eb65c-1754af4a60f-1249",
//    "transaction_unique_id": "AUTH3В_transaction_Levitskiy_1603280348",
//    "token": "5f8eeb55-6d70-4802-bfa8-0cbaa94aef34",
//    "reference": "A3FF00000016255968EC",
//    "timestamp": 1603280349,
//    "authcode": "",
//    "pareq": "eyJyZWZlcmVuY2UiOiJBM0ZGMDAwMDAwMTYyNTU5NjhFQ1hYMDEiLCJ0aW1lIjoxNjAzMjgwMzQ5LjU2ODYzNn0=",
//    "acs_url": "https://callback.maxpay.com/emitent/authentication/",
//    "eci": 5,
//    "status": "success",
//    "code": 0,
//    "message": "Transaction processed successfully"
)
