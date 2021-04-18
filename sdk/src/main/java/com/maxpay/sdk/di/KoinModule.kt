package com.maxpay.sdk.di
import android.app.Application
import com.maxpay.sdk.SdkHelper
import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.datamodule.repository.MaxPayRepositoryImpl
import com.maxpay.sdk.model.MaxPayRepository
import com.maxpay.sdk.model.MaxPayTheme
import com.maxpay.sdk.ui.MainViewModel
import com.maxpay.sdk.utils.*
import org.koin.android.ext.koin.androidApplication
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
    single { PrefsUtils(get()) }
    single<IpHelper> { IpHelperImpl() }
    single<CountryISOHelper> { CountryISOHelperImpl() }
    single { CustomTabsHelper() }
    single { ExpiryParser() }
    factory { (theme: MaxPayTheme?) -> EditTextValidator(theme) }
    factory { (theme: MaxPayTheme?) -> UIComponentThemeEditor(theme) }

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

