package com.onoffrice.dogsbreed.data.local

import android.content.Context
import android.content.SharedPreferences
import com.onoffrice.dogsbreed.Constants.PACKAGE_NAME

object PreferencesHelper {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private const val SHARED_PREFERENCES_NAME = "$PACKAGE_NAME.SHARED_PREFERENCES"
    private const val TOKEN = "$PACKAGE_NAME.TOKEN"


    var token: String
        get() = sharedPreferences.getString(TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(TOKEN, value).apply()

    fun clearSharedPref() {
        sharedPreferences.edit().clear().apply()
    }
}