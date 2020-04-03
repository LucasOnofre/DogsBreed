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

    val feedItems     = SingleLiveEvent<MutableList<FeedItem>>()
    val errorEvent    = SingleLiveEvent<Any>()
    val loadingEvent  = SingleLiveEvent<Boolean>()
    val toolbarTitle  = SingleLiveEvent<Any>()
    val selectedBreed = SingleLiveEvent<String>()

    fun getFeed(category: String? = null) {
        selectedBreed.value = category
        disposable.add(repository.getFeed(category).singleSubscribe(
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

    private fun handleFeedResponse(feedResponse: FeedWrapper?) {
        checkCategory(feedResponse)

        checkFeedList(feedResponse)
    }

    private fun checkFeedList(feedResponse: FeedWrapper?) {
        if (!feedResponse?.dogsFeedList.isNullOrEmpty()) {
            feedItems.value = feedResponse?.dogsFeedList!!.toFeedItemList()
        } else {
            errorEvent.value = R.string.error_feed
        }
    }

    private fun checkCategory(feedResponse: FeedWrapper?) {
        if (feedResponse?.category.isNullOrEmpty()) {
            toolbarTitle.value = R.string.toolbar_feed_title
        } else {
            toolbarTitle.value = feedResponse!!.category.capitalize()
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
