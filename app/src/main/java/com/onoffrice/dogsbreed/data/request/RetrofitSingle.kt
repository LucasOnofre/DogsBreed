package com.onoffrice.dogsbreed.data.request

import androidx.multidex.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitSingle{

    fun <S> createService(serviceClass: Class<S>, interceptors: List<Interceptor>? = null, url: String): S {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))

        interceptors?.let {
            for (interceptor in interceptors) {
                httpClient.addInterceptor(interceptor)
                //httpClient.addInterceptor({ chain -> createParametersDefault(chain) })
            }
        }
        retrofit.client(httpClient.build())
        return retrofit.build().create(serviceClass)
    }
}
