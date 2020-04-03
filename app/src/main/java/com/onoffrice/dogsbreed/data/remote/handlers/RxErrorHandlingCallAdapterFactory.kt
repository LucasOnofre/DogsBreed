package com.onoffrice.dogsbreed.data.remote.handlers

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.onoffrice.dogsbreed.BuildConfig
import com.onoffrice.dogsbreed.data.remote.model.ErrorResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val ERROR_UNKNOWN_HOST = "Rede Indisponível"
private const val ERROR_UNKNOWN = "Erro Desconhecido"
private const val ERROR_TIMEOUT_ERROR = "Tempo Excedido."
private const val ERROR_PARSE_PRODUCTION = "Pode ser necessário atualizar seu aplicativo."
private const val ERROR_PARSE_DEBUG = "JsonParseException"

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val original = RxJava2CallAdapterFactory.create()

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<Type, Any>? {
        return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit) as? CallAdapter<Type, Any>)
    }

    private class RxCallAdapterWrapper(private val retrofit: Retrofit, private val wrapped: CallAdapter<Type, Any>?) : CallAdapter<Type, Any> {

        override fun adapt(call: Call<Type>): Any {
            return (wrapped?.adapt(call) as Single<*>).onErrorResumeNext { throwable -> Single.error(asRetrofitException(throwable)) }
        }

        override fun responseType(): Type? {
            return wrapped?.responseType()
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            return when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    try {
                        val json = response?.errorBody()?.string()
                        val message = Gson().fromJson(json, ErrorResponse::class.java)
                        RetrofitException.create(message.message, throwable, RetrofitException.Type.HTTP, retrofit, response)
                    } catch (ex: Exception) {
                        RetrofitException.create(ERROR_UNKNOWN, throwable, RetrofitException.Type.HTTP, retrofit, response)
                    }
                }
                is IOException -> {
                    val message = when (throwable) {
                        is UnknownHostException -> ERROR_UNKNOWN_HOST
                        is SocketTimeoutException -> ERROR_TIMEOUT_ERROR
                        else -> ERROR_UNKNOWN
                    }
                    return RetrofitException.create(message, throwable, RetrofitException.Type.NETWORK)
                }
                is JsonParseException -> {
                    val message = if (BuildConfig.DEBUG) ERROR_PARSE_DEBUG + " " + throwable.message else ERROR_PARSE_PRODUCTION
                    return RetrofitException.create(message, throwable, RetrofitException.Type.UNKNOWN)
                }
                else -> {
                    return RetrofitException.create(ERROR_UNKNOWN, throwable, RetrofitException.Type.UNKNOWN)
                }
            }
        }
    }
}
