package com.maxpay.sdk.utils

import androidx.annotation.IntDef
import androidx.annotation.StringDef

internal class Constants{
    companion object {

        const val MAXPAY_CALLBACK_BROADCAST = "maxpay_callback_BROADCAST"
        const val MAXPAY_CALLBACK_BROADCAST_SIGNATURE = "maxpay_signature_broadcast"
        const val MAXPAY_BROAD_SIGNATURE_RES = "maxpay_signature_RES"
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
                const val MAXPAY_PAYMENT_DATA = "maxpay_payment_data"
                const val MAXPAY_CUSTOM_THEME_DATA = "maxpay_custom_theme_data"

                const val MAXPAY_BROADCAST_DATA = "broadcast_data"
                const val MAXPAY_BROADCAST_SIGNATURE_DATA = "broadcast_signature_data"

                const val CALLBACK_SIGNATURE = "callback_SIGNATURE"
            }
        }

        @StringDef( Extra.MAXPAY_INIT_DATA)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Links {
            companion object {
                const val MAXPAY_CONTACT = "https://maxpay.com/contact.html"
                const val MAXPAY_PRIVACY = "https://maxpay.com/privacy/"
                const val MAXPAY_TERMS = "https://maxpay.com/terms/"
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

        @IntDef(RequiredLength.CARD_INPUT_LENGTH, RequiredLength.CVV_INPUT_LENGTH,
                RequiredLength.EXPIRY_INPUT_LENGTH, RequiredLength.ZIP_INPUT_LENGTH,
                RequiredLength.CARDHOLDER_INPUT_LENGTH)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RequiredLength {
            companion object {
                const val CARDHOLDER_INPUT_LENGTH = 2
                const val CARD_INPUT_LENGTH = 13
                const val COUNTRY_INPUT_LENGTH = 3
                const val CVV_INPUT_LENGTH = 3
                const val EXPIRY_INPUT_LENGTH = 4
                const val ZIP_INPUT_LENGTH = 6
            }
        }
    }
}
