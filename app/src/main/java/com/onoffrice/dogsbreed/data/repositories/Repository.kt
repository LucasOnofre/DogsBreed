package com.onoffrice.dogsbreed.data.repositories

import com.onoffrice.dogsbreed.data.local.PreferencesHelper
import com.onoffrice.dogsbreed.data.remote.model.FeedWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignUpWrapper
import com.onoffrice.dogsbreed.data.remote.model.SignupRequest
import com.onoffrice.dogsbreed.data.request.services.Service
import io.reactivex.Single

interface Repository {
    fun makeSignUp(email: SignupRequest): Single<SignUpWrapper>
    fun getFeed(category: String?): Single<FeedWrapper>
}

class RepositoryImp(val service: Service): Repository {

    override fun makeSignUp(email: SignupRequest): Single<SignUpWrapper> = service.makeSignUp(email)
        .doOnSuccess {
            PreferencesHelper.clearSharedPref()
            PreferencesHelper.token = it.user.token
        }

    override fun getFeed(category: String?): Single<FeedWrapper> = service.getFeed(category?.toLowerCase())

}

