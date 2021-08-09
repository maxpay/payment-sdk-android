package com.maxpay.testappmaxpay.di
import com.maxpay.testappmaxpay.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel(androidApplication()) }
}

val dataModule = module {
}