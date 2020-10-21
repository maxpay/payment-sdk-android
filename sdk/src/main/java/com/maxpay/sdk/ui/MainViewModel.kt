package com.maxpay.sdk.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxpay.sdk.core.MyAndroidViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel(application: Application)
    : MyAndroidViewModel(application), KoinComponent {

}

//lass NewsViewModel(
//application: Application
//) : MyAndroidViewModel(application), KoinComponent {
//
//    private val newsRepository: NewsRepository by inject()
//    private val dateInterface: DateInterface by inject()
//
//    private val _newsList = MutableLiveData<MutableList<News>>()
//    val newsList: LiveData<MutableList<News>>
//    get() = _newsList
//
//    private var page = 1