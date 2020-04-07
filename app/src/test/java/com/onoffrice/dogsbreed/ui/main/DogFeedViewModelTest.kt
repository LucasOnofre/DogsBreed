package com.onoffrice.dogsbreed.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.whenever
import com.onoffrice.dogsbreed.data.remote.model.FeedWrapper
import com.onoffrice.dogsbreed.data.repositories.RepositoryImp
import com.onoffrice.dogsbreed.ui.dogsFeed.DogsFeedViewModel
import com.onoffrice.dogsbreed.ui.helper.RxImmediateSchedulerRule
import com.onoffrice.dogsbreed.utils.extensions.toFeedItemList
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    //MOCKS
    @Mock
    lateinit var mockDataRepository: RepositoryImp

    @Mock
    lateinit var viewModel: DogsFeedViewModel

    @Mock
    lateinit var feedWrapper: FeedWrapper

    private lateinit var category:String
    private lateinit var secondCategory:String

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = DogsFeedViewModel(mockDataRepository)

        feedWrapper = FeedWrapper(
            "husky",
            listOf("url1","url2"))

        category       = "husky"
        secondCategory = "labrador"
    }

    @Test
    fun `When getFeed() is called, updates the live data toolbar title with response`() {

        whenever(mockDataRepository.getFeed(category))
            .thenReturn(Single.just(feedWrapper))

        viewModel.getFeed(category)


        Assert.assertEquals(
            feedWrapper.category.capitalize(),
            viewModel.toolbarTitle.value)
    }

    @Test
    fun `When getFeed() is called, updates the live data feedItems with response`() {

        whenever(mockDataRepository.getFeed(category))
            .thenReturn(Single.just(feedWrapper))
        //Fire the test method
        viewModel = DogsFeedViewModel(mockDataRepository)
        viewModel.getFeed(category)

        //Check that our live data is updated
        Assert.assertEquals(
            feedWrapper.dogsFeedList.toFeedItemList(),
            viewModel.feedItems.value)
    }
}