package com.maxpay.testappmaxpay.core

import android.app.Application
import com.maxpay.testappmaxpay.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule
                )
            )
        }
    }
}