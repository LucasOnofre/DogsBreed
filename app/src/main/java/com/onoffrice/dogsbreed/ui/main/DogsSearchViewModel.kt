package com.onoffrice.dogsbreed.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onoffrice.dogsbreed.data.remote.model.SignupRequest
import com.onoffrice.dogsbreed.data.repositories.Repository
import com.onoffrice.dogsbreed.utils.SingleLiveEvent
import com.onoffrice.dogsbreed.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class DogsSearchViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()

    val openFeed     =  SingleLiveEvent<Any>()
    val errorEvent   = SingleLiveEvent<String>()
    val loadingEvent = SingleLiveEvent<Boolean>()


    fun makeSignUp(email: String) {
        disposable.add(repository.makeSignUp(SignupRequest(email)).singleSubscribe(
                onLoading = {
                    loadingEvent.value = it
                },
                onSuccess = {
                    openFeed.call()
                },
                onError = {
                    errorEvent.value = it.message
                }))
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    class Factory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DogsSearchViewModel(repository) as T
        }
    }
}