package com.onoffrice.dogsbreed.utils.extensions

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.singleSubscribe(
        onLoading: ((t: Boolean) -> Unit)? = null,
        onSuccess: ((t: T) -> Unit)? = null,
        onError: ((e: Throwable) -> Unit)? = null,
        subscribeOnScheduler: Scheduler? = Schedulers.io(),
        observeOnScheduler: Scheduler? = AndroidSchedulers.mainThread()): DisposableSingleObserver<T> {
    
    onLoading?.let { it(true) }
    
    return this.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribeWith(object : DisposableSingleObserver<T>() {
                override fun onSuccess(t: T) {
                    onLoading?.let { it(false) }
                    onSuccess?.let { it(t) }
                }
                
                override fun onError(e: Throwable) {
                    onLoading?.let { it(false) }
                    onError?.let { it(e) }
                }
            })
}