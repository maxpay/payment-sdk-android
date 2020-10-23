package com.maxpay.sdk.di
import android.app.Application
import android.content.Context
import com.maxpay.sdk.SDKFacade
import com.maxpay.sdk.SdkFacadeImpl
import com.maxpay.sdk.SdkHelper
import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.datamodule.repository.MaxPayRepositoryImpl
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.ui.MainViewModel
import com.maxpay.sdk.utils.DateFormat
import com.maxpay.sdk.utils.DateInterface
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(androidApplication()) }
}

val facadeModule = module {
//    single<Context> { androidContext() }
//    single <SDKFacade> { SdkFacadeImpl() }
//    single { SdkFacadeImpl() }
    single { SdkHelper() }
}

val dataModule = module {
    single { Api(getPrefs(androidApplication()), null) }
    single <MaxPayRepository> { MaxPayRepositoryImpl(get()) }
}

val utils = module {
    factory<DateInterface> {
        return@factory DateFormat()
    }
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
fun getPrefs(app: Application) =
    app.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)