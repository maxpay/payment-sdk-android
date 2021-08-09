package com.maxpay.sdk.di
import android.app.Application
import android.content.SharedPreferences
import com.maxpay.sdk.SdkHelper
import com.maxpay.sdk.datamodule.api.Api
import com.maxpay.sdk.datamodule.repository.MaxPayRepositoryImpl
import com.maxpay.sdk.datamodule.repository.MaxPayRepository
import com.maxpay.sdk.model.PayTheme
import com.maxpay.sdk.ui.MainViewModel
import com.maxpay.sdk.utils.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel(androidApplication()) }
}

val facadeModule = module {
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
    single<IpHelper> { IpHelperImpl() }
    single<CountryISOHelper> { CountryISOHelperImpl() }
    single { CustomTabsHelper() }
    single { ExpiryParser() }
    factory { (theme: PayTheme?) -> EditTextValidator(theme) }
    factory { (theme: PayTheme?) -> UIComponentThemeEditor(theme) }

}
fun getPrefs(app: Application): SharedPreferences =
    app.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
