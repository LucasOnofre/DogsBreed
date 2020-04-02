package com.onoffrice.dogsbreed.data.request.services

import com.onoffrice.dogsbreed.data.remote.model.FeedWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignUpWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignupRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @POST("signup")
    fun makeSignUp(@Body email: SignupRequest): Single<SignUpWrapper>

    @GET("feed")
   fun getFeed(): Single<FeedWrapper>

}