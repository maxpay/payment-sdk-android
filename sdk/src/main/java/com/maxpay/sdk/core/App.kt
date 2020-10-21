package com.maxpay.sdk.core

import android.app.Application
import com.maxpay.sdk.di.dataModule
import com.maxpay.sdk.di.networkModule
import com.maxpay.sdk.di.utils
import com.maxpay.sdk.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    utils,
                    dataModule
                )
            )
        }
    }
}