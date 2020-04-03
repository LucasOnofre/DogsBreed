package com.onoffrice.dogsbreed

import androidx.multidex.MultiDexApplication
import com.onoffrice.dogsbreed.data.di.KoinInjector
import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.dogsbreed.data.remote.RemotePreferencesHelper
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
                    KoinInjector.dogSearchModule,
                    KoinInjector.feedModule,
                    KoinInjector.networkModule
                )
            )
        }
    }


    private fun setPreferencesHelper() {
        PreferencesHelper.init(applicationContext)
        RemotePreferencesHelper.init(applicationContext)
    }
}
