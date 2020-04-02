package com.onoffrice.dogsbreed.ui.dogsFeed

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.ui.base.BaseActivity
import com.onoffrice.dogsbreed.utils.AppInjector
import com.onoffrice.dogsbreed.utils.extensions.setVisible
import kotlinx.android.synthetic.main.activity_feed.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class DogsFeedActivity : BaseActivity(R.layout.activity_feed) {

    private val viewModel by lazy {
        val factory = AppInjector.getDogsFeedViewModel()
        ViewModelProvider(this, factory).get(DogsFeedViewModel::class.java)
    }

    private val feedAdapter: FeedAdapter by lazy {
        val adapter = FeedAdapter(object : FeedAdapter.ItemClickListener{
            override fun onClickCharacter(feedView: View) {
                feedView.setBackgroundColor(context.resources.getColor(R.color.colorBlack))
            }
        })

        val layoutManager    = GridLayoutManager(this, 2)
        feedRv.layoutManager = layoutManager
        feedRv.adapter       = adapter
        adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(getString(R.string.toolbar_feed_title), true)
        setObservers()
        getFeed()
    }

    private fun getFeed() {
        viewModel.getFeed()
    }

    private fun setObservers() {
        viewModel.run {
            loadingEvent.observe(this@DogsFeedActivity,Observer { displayLoading(it) })
            errorEvent.observe(this@DogsFeedActivity,  Observer { displayError(it) })
            feedItems.observe(this@DogsFeedActivity,   Observer { displayFeed(it) })
        }
    }

    private fun displayFeed(feedItems: List<String>) {
        feedAdapter.list = feedItems
    }

    private fun displayLoading(loading: Boolean) {
        progressBar.setVisible(loading)
    }

    private fun displayError(message: Any) {
        when(message) {
            is Int    -> toast(getString(message))
            is String -> toast(message)
        }
    }
}
fun Context.createDogsFeedIntent() = intentFor<DogsFeedActivity>()

