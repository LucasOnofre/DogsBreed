package com.onoffrice.dogsbreed.data.remote.interceptors

import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.marvel_comics.data.remote.interceptors.RequestHeaderInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddHeaderInterceptor(val sendToken: Boolean) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        //Adds every item on request header list
            val request = original.newBuilder().also { request ->
                checkHeader().forEach {
                    request.addHeader(it.name, it.value)
                }
            }
                .method(original.method(), original.body())
                .build()

        return chain.proceed(request)
    }

    private fun checkHeader(): ArrayList<RequestHeaderInterceptor> {
        val headerInterceptors = arrayListOf<RequestHeaderInterceptor>()

        headerInterceptors.add(RequestHeaderInterceptor("Content-Type", "application/json"))

        if (sendToken) {
            headerInterceptors.add(RequestHeaderInterceptor("Authorization", PreferencesHelper.token))
        }
        return headerInterceptors
    }
}