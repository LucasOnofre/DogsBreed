package com.onoffrice.dogsbreed.data.request.services

import com.onoffrice.dogsbreed.data.remote.model.SignUpWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignupRequest
import io.reactivex.Single
import retrofit2.http.*

interface Service {

    @POST("signup")
    fun makeSignUp(@Body email: SignupRequest): Single<SignUpWrapper>

    @GET("feed")
   fun getFeed(): Single<Any>

}