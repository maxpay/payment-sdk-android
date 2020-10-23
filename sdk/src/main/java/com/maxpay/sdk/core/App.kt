package com.maxpay.sdk.core

import android.app.Application
import com.maxpay.sdk.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    facadeModule,
                    viewModelModule,
                    utils,
                    dataModule
                )
            )
        }
    }
}