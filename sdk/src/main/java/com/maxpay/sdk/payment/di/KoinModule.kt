package com.maxpay.sdk.payment.di
import android.app.Application
import android.content.SharedPreferences
import com.maxpay.sdk.payment.SdkHelper
import com.maxpay.sdk.payment.datamodule.api.Api
import com.maxpay.sdk.payment.datamodule.repository.MaxPayRepositoryImpl
import com.maxpay.sdk.payment.datamodule.repository.MaxPayRepository
import com.maxpay.sdk.payment.model.PayTheme
import com.maxpay.sdk.payment.ui.MainViewModel
import com.maxpay.sdk.payment.utils.*
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

