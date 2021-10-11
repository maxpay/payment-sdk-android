package com.maxpay.sdk.payment.utils

import androidx.annotation.IntDef
import androidx.annotation.StringDef

internal class Constants{
    companion object {
        const val PAY_CALLBACK_BROADCAST = "pay_callback_BROADCAST"
        const val PAY_CALLBACK_BROADCAST_SIGNATURE = "pay_signature_broadcast"
        const val PAY_BROAD_SIGNATURE_RES = "pay_signature_RES"
        const val PERMISSION = 999

        @StringDef(
            Extra.PAY_DATA, Extra.RETURN_URL, Extra.PAY_TERM_URL,
            Extra.PAY_PARAQ, Extra.PAY_MD, Extra.PAY_INIT_DATA
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class Extra {
            companion object {
                const val PAY_DATA = "pay.data"
                const val RETURN_URL = "return_url"
                const val PAY_TERM_URL = "pay_term_url"
                const val PAY_PARAQ = "pay_paraq"
                const val PAY_MD = "pay_md"
                const val PAY_INIT_DATA = "pay_init_data"
                const val PAY_PAYMENT_DATA = "pay_payment_data"
                const val PAY_CUSTOM_THEME_DATA = "pay_custom_theme_data"

                const val PAY_BROADCAST_DATA = "broadcast_data"
                const val PAY_BROADCAST_SIGNATURE_DATA = "broadcast_signature_data"
            }
        }

        @IntDef(
            RequiredLength.CARD_INPUT_LENGTH, RequiredLength.CVV_INPUT_LENGTH,
            RequiredLength.EXPIRY_INPUT_LENGTH, RequiredLength.ZIP_INPUT_LENGTH,
            RequiredLength.CARDHOLDER_INPUT_LENGTH
        )
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
