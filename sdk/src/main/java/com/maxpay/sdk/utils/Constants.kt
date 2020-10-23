package com.maxpay.sdk.utils

import androidx.annotation.StringDef

class Constants{
    companion object {

        const val MAXPAY_CALLBACK_BROADCAST = "maxpay_callback_BROADCAST"
        const val PERMISSION = 999

        @StringDef(Extra.MAXPAY_DATA, Extra.RETURN_URL, Extra.MAXPAY_TERM_URL,
                   Extra.MAXPAY_PARAQ, Extra.MAXPAY_MD, Extra.MAXPAY_INIT_DATA)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Extra {
            companion object {
                const val MAXPAY_DATA = "maxpay.data"
                const val RETURN_URL = "return_url"
                const val MAXPAY_TERM_URL = "maxpay_term_url"
                const val MAXPAY_PARAQ = "maxpay_paraq"
                const val MAXPAY_MD = "maxpay_md"

                const val MAXPAY_INIT_DATA = "maxpay_init_data"
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
