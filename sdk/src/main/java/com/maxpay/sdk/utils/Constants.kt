package com.maxpay.sdk.utils

import androidx.annotation.StringDef

class Constants{
    companion object {
        const val MAXPAY_CALLBACK_BROADCAST = "maxpay_callback_BROADCAST"


        @StringDef(Extra.MAXPAY_DATA, Extra.RETURN_URL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Extra {
            companion object {
                const val MAXPAY_DATA = "maxpay.data"
                const val RETURN_URL = "return_url"
            }
        }

        @StringDef(Token.ACCESS_TOKEN_KEY, Token.USER_ACCESS_TOKEN_KEY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Token {
            companion object {
                const val ACCESS_TOKEN_KEY = "access_token_key"
                const val USER_ACCESS_TOKEN_KEY = "user_access_token_key"
            }
        }
    }
}
