package com.maxpay.sdk.di
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(androidApplication()) }
}

val networkModule = module {
//    single { SDKFacade(get()) }
    single <SDKFacade> { SdkFacadeImpl(androidContext()) }
}

val utils = module {
//    factory<DateInterface> {
//        return@factory DateFormat()
//    }
//    single { ColorSetter(get()) }
//
//    factory { WebViewUtil() }
//
//    single { FileUtil(get()) }
//
//    single {
//        CustomTabsIntent.Builder()
//            .setToolbarColor(androidApplication().resources.getColor(R.color.colorPrimary))
//            .setShowTitle(true)
//            .build()
//    }
}