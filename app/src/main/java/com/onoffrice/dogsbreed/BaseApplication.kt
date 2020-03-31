package com.onoffrice.dogsbreed

import androidx.multidex.MultiDexApplication
import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.dogsbreed.data.remote.RemotePreferencesHelper


class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        setPreferencesHelper()
    }


    private fun setPreferencesHelper() {
        PreferencesHelper.init(applicationContext)
        RemotePreferencesHelper.init(applicationContext)
    }
}
