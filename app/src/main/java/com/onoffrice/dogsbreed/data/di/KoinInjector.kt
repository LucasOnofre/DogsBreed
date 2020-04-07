package com.onoffrice.dogsbreed.data.di

import android.content.Context
import com.onoffrice.dogsbreed.Constants
import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.dogsbreed.data.remote.handlers.RxErrorHandlingCallAdapterFactory
import com.onoffrice.dogsbreed.data.remote.interceptors.AddHeaderInterceptor
import com.onoffrice.dogsbreed.data.remote.interceptors.NetworkInterceptor
import com.onoffrice.dogsbreed.data.repositories.Repository
import com.onoffrice.dogsbreed.data.repositories.RepositoryImp
import com.onoffrice.dogsbreed.data.request.ServiceGenerator
import com.onoffrice.dogsbreed.data.request.services.Service
import com.onoffrice.dogsbreed.ui.dogsFeed.DogsFeedViewModel
import com.onoffrice.dogsbreed.ui.main.DogsSearchViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinInjector {

    val dogSearchModule = module {
        viewModel { DogsSearchViewModel(get()) }
        single<Repository> { RepositoryImp(get()) }
    }

    val feedModule = module {
        viewModel { DogsFeedViewModel(get()) }
    }

    val networkModule = module {
        single { providesConverter() }
        single { providesJavaAdapter() }
        single { provideOkHttp(interceptors = provideAllInterceptors(context = get())) }
        single { provideRetrofitBuilder(okHttpClient = get(), adapterFactory = get(), converterFactory = get()) }
        single { provideService(retrofit = get(), baseUrl = provideBaseUrl()) }
    }

    private fun provideService(retrofit: Retrofit.Builder, baseUrl: String): Service {
        return ServiceGenerator(retrofit.baseUrl(baseUrl).build()).forClass(Service::class.java)
    }

    private fun provideAllInterceptors(context: Context) =
            listOf(
                provideLoggingInterceptor(),
                provideNetworkConnection(context),
                provideHeaderInterceptor()
            )

    private fun provideNetworkConnection(context: Context) = NetworkInterceptor(context)

    private fun providesConverter(): GsonConverterFactory = GsonConverterFactory.create()

    private fun provideOkHttp(interceptors: List<Interceptor>): OkHttpClient {
            val builder = OkHttpClient.Builder()

            interceptors.forEach { interceptor ->
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }

    private fun provideRetrofitBuilder(
            okHttpClient: OkHttpClient,
            adapterFactory: RxErrorHandlingCallAdapterFactory,
            converterFactory: GsonConverterFactory
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(adapterFactory)
                .addConverterFactory(converterFactory)
        }

    private fun provideBaseUrl() = Constants.BASE_URL

    private fun providesJavaAdapter() = RxErrorHandlingCallAdapterFactory()

    private fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun provideHeaderInterceptor(): Interceptor {
        return AddHeaderInterceptor(!PreferencesHelper.token.isNullOrEmpty())
    }
}