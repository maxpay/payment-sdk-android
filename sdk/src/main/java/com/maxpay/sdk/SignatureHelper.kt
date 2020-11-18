package com.maxpay.sdk

import com.maxpay.sdk.core.removeZero
import com.maxpay.sdk.model.request.SalePayment
import com.maxpay.sdk.model.request.TransactionType
import java.security.MessageDigest
import kotlin.reflect.full.memberProperties

class SignatureHelper {

    private val privateKey = "sklive_wbkz4pc670ajfywc9st0ioajc07cesok"

//    fun getHashOfRequest(
//        api_version: Int?,
//        auth_type: String? = "bysignature",
//        transaction_type: TransactionType,
//        currency: String?,
//        first_name: String?,
//        last_name: String?,
//        address: String?,
//        city: String?,
//        state: String?,
//        zip: String?,
//        country: String?,
//        user_phone: String?,
//
//
//    ): String {
//    }

    internal fun getHashOfRequest(request: SalePayment): String {
        val sigH = SignatureH(
            api_version = request.apiVersion,
            auth_type = request.auth_type,
            transaction_unique_id = request.transactionId,
            transaction_type = request.transactionType.toString(),
            currency = request.currency,
            first_name = request.firstName,
            last_name = request.lastName,
            address = request.address,
            city = request.city,
//            state = request.state,
            zip = request.zip,
            country = request.country,
            user_phone = request.userPhone,
            user_email = request.userEmail,
            user_ip = request.userIp,
            callback_url = request.callBackUrl,
            redirect_url = request.redirectUrl
        )

        sigH.amount = request.amount.removeZero()
        var str = ""
        val arr = arrayListOf<String>()
        for (prop in SignatureH::class.memberProperties) {
            if (prop.get(sigH) != null && !prop.get(sigH).toString().isEmpty())
                arr.add("${prop.name}=${prop.get(sigH)}|")
        }
        arr.sort()
        arr.forEach {
            str += it
        }
        str += privateKey
        str = str.toLowerCase()
        val hash = hashString(str, "sha256")
        return hash
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest.getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}


data class SignatureH(
    val api_version: Int? = null,
    val auth_type: String = "bysignature",
    val transaction_unique_id: String? = null,
    val transaction_type: String? = null,
    var amount: String? = null,
    val currency: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String? = null,
    val country: String? = null,
    val user_phone: String? = null,
    val user_email: String? = null,
    val user_ip: String? = null,
    val callback_url: String? = null,
    val redirect_url: String? = null
)


