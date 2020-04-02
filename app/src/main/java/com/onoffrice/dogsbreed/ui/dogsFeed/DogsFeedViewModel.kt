package com.onoffrice.dogsbreed.ui.dogsFeed

import androidx.lifecycle.ViewModel
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.data.local.FeedItem
import com.onoffrice.dogsbreed.data.remote.model.FeedWrapper
import com.onoffrice.dogsbreed.data.repositories.Repository
import com.onoffrice.dogsbreed.utils.SingleLiveEvent
import com.onoffrice.dogsbreed.utils.extensions.singleSubscribe
import com.onoffrice.dogsbreed.utils.extensions.toFeedItemList
import io.reactivex.disposables.CompositeDisposable

class DogsFeedViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()

    val feedItems    = SingleLiveEvent<List<FeedItem>>()
    val errorEvent   = SingleLiveEvent<Any>()
    val loadingEvent = SingleLiveEvent<Boolean>()

    fun getFeed() {
        disposable.add(repository.getFeed().singleSubscribe(
                onLoading = {
                    loadingEvent.value = it
                },
                onSuccess = {
                    handleFeedResponse(it)
                },
                onError = {
                    errorEvent.value = it.message
                }))
    }

    private fun handleFeedResponse(feedResponse: FeedWrapper) {
        if (!feedResponse.dogsFeedList.isNullOrEmpty()) {
            feedItems.value = feedResponse.dogsFeedList.toFeedItemList()
        } else {
            errorEvent.value = R.string.error_feed
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
