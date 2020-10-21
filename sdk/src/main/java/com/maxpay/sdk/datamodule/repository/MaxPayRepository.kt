package com.maxpay.sdk.datamodule.repository

import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.model.MaxPayRepository

class MaxPayRepositoryImpl(
    private val api: Api
): MaxPayRepository {
}