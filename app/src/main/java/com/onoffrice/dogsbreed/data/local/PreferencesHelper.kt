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


//    /** Saves the last top games response **/
//    var games: TopGamesList?
//        get() {
//            val userJson = sharedPreferences.getString(PREF_BANNERS, "")
//            return Gson().fromJson(userJson, TopGamesList::class.java) ?: return TopGamesList()
//        }
//        set(value) {
//            val banners = value ?: TopGamesList()
//            val json = Gson().toJson(banners, TopGamesList::class.java)
//            sharedPreferences.edit().putString(PREF_BANNERS, json).apply()
//            isOnline = true
//        }

    fun clearSharedPref() {
        sharedPreferences.edit().clear().apply()
    }
}