package com.maxpay.testappmaxpay.utils

import com.maxpay.sdk.core.removeZero
import com.maxpay.sdk.model.MaxpaySignatureData
import java.security.MessageDigest
import kotlin.reflect.full.memberProperties

class SignatureHelper(
    private val privateKey: String
    ) {

//    payment.signature = com.maxpay.testappmaxpay.utils.SignatureHelper(_viewState.maxpayInitData.value?.privateKey!!)
//    .getHashOfRequest(payment)


    internal fun getHashOfRequest(request: MaxpaySignatureData): String {
        request.amount?.toFloat()?.let {
            return calculateHash(request, it)
        }?: kotlin.run {
            return "errorSignature"
        }

    }

    private fun calculateHash(sigH: MaxpaySignatureData, amount: Float): String {
        sigH.amount = amount.removeZero()
        var str = ""
        val arr = arrayListOf<String>()
        for (prop in MaxpaySignatureData::class.memberProperties) {
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

