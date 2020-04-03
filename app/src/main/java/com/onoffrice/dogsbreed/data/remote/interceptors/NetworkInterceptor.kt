package com.onoffrice.dogsbreed.data.remote.interceptors

import android.content.Context
import com.onoffrice.dogsbreed.Constants
import com.onoffrice.dogsbreed.utils.extensions.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NetworkInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!context.isNetworkConnected()) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException() : IOException() {
    override val message: String
        get() = Constants.NETWORK_ERROR
}