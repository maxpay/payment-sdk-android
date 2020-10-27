package com.maxpay.sdk.utils

import android.content.SharedPreferences
import com.google.gson.Gson

class PrefsUtils(
    private val prefs: SharedPreferences
) {

//    fun saveTokenIfExists(responseFromServer: HashMap<String, String>) {
//        responseFromServer[Constants.ACCESS_TOKEN_KEY]?.let {
//            with (prefs.edit()) {
//                putString(Constants.ACCESS_TOKEN_KEY, it)
//                commit()
//            }
//        }
//    }
//
//    fun saveTokenIfExists(responseFromServer: LoginResponse) {
//        responseFromServer.accessToken.let {
//            with (prefs.edit()) {
//                putString(Constants.USER_ACCESS_TOKEN_KEY, it)
//                commit()
//            }
//        }
//    }
//
//    fun getAccessToken() = prefs.getString(Constants.ACCESS_TOKEN_KEY, null)
//
//    fun getUserAccessToken() = prefs.getString(Constants.USER_ACCESS_TOKEN_KEY, null)
//
//    fun removeAuthToken() {
//        with (prefs.edit()) {
//            remove(Constants.ACCESS_TOKEN_KEY)
//            commit()
//        }
//        with (prefs.edit()) {
//            remove(Constants.USER_ACCESS_TOKEN_KEY)
//            commit()
//        }
//    }
//
//    internal fun saveUserData(registrationModel: RegistrationModel) {
//        with (prefs.edit()) {
//            val jsonUserData = Gson().toJson(registrationModel)
//            putString(Constants.USER_PROFILE_KEY, jsonUserData)
//            commit()
//        }
//    }
//
//
//    internal fun getSavedUserData(): RegistrationModel? {
//        val jsonUserData = prefs.getString(Constants.USER_PROFILE_KEY, null)
//        return Gson().fromJson(jsonUserData, RegistrationModel::class.java)
//    }
//
//    fun saveBusinessCenter(bc: BusinessCenter) {
//        with (prefs.edit()) {
//            val jsonUserData = Gson().toJson(bc)
//            putString(Constants.BUSINESS_CENTER_PREF_KEY, jsonUserData)
//            commit()
//        }
//    }
//
//    fun getCurrentBusinessCenter(): BusinessCenter? {
//        val jsonBC = prefs.getString(Constants.BUSINESS_CENTER_PREF_KEY, null)
//        jsonBC?.let {
//            return Gson().fromJson(jsonBC, BusinessCenter::class.java)
//        }?: kotlin.run {
//            return null
//        }
//    }
//
//    fun saveIntroViewed(isViewed: Boolean) {
//        with (prefs.edit()) {
//            putBoolean(Constants.IS_INTRO_VIEWED_KEY, isViewed)
//            commit()
//        }
//    }
//
//    fun isIntroViewed(): Boolean =
//        prefs.getBoolean(Constants.IS_INTRO_VIEWED_KEY, false)
}