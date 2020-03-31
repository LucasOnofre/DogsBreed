package com.onoffrice.dogsbreed.data.repositories


import com.onoffrice.dogsbreed.NetworkConstants
import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.dogsbreed.data.remote.interceptors.AddHeaderInterceptor
import com.onoffrice.dogsbreed.data.remote.model.SignUpWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignupRequest
import com.onoffrice.dogsbreed.data.request.RetrofitSingle
import com.onoffrice.dogsbreed.data.request.services.Service
import io.reactivex.Single

interface Repository {
    fun makeSignUp(email: SignupRequest): Single<SignUpWrapper>
    fun getFeed(): Single<Any>
}

class RepositoryImp: Repository {

    private val service = RetrofitSingle.createService(
        url             = NetworkConstants.BASE_URL,
        serviceClass    = Service::class.java,
        interceptors    = listOf(AddHeaderInterceptor(!PreferencesHelper.token.isNullOrEmpty()))
    )

    override fun makeSignUp(email: SignupRequest): Single<SignUpWrapper> = service.makeSignUp(email)
        .doOnSuccess {
            PreferencesHelper.clearSharedPref()
            PreferencesHelper.token = it.user.token
        }

    override fun getFeed(): Single<Any> = service.getFeed()

}

