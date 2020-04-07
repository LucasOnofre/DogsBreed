package com.onoffrice.dogsbreed

import androidx.multidex.MultiDexApplication
import com.onoffrice.dogsbreed.data.di.NetworkModulesInjector
import com.onoffrice.dogsbreed.data.di.ViewModulesInjector
import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setPreferencesHelper()
        setKoin()
    }

    private fun setKoin() {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@BaseApplication)

            modules(
                listOf(
                    ViewModulesInjector.dogSearchModule,
                    ViewModulesInjector.feedModule,
                    NetworkModulesInjector.networkModule
                )
            )
        }
    }


    private fun setPreferencesHelper() {
        PreferencesHelper.init(applicationContext)
    }
}
