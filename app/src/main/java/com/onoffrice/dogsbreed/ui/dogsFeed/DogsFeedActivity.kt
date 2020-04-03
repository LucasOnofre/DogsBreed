package com.onoffrice.dogsbreed.ui.dogsFeed

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.data.local.FeedItem
import com.onoffrice.dogsbreed.ui.base.BaseActivity
import com.onoffrice.dogsbreed.utils.extensions.setVisible
import kotlinx.android.synthetic.main.activity_feed.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DogsFeedActivity : BaseActivity(R.layout.activity_feed) {

    private lateinit  var feedList: List<FeedItem>

    private val viewModel: DogsFeedViewModel by viewModel()

    private val feedAdapter: FeedAdapter by lazy {
        val adapter = FeedAdapter(object : FeedAdapter.ItemClickListener{
            override fun onClickCharacter(position: Int) {
               // handleListClick(position)
            }
        })
        feedRv.layoutManager = LinearLayoutManager(this)
        feedRv.adapter       = adapter
        adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.getFeed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feed_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_pug      -> { viewModel.getFeed(getString(R.string.menu_item_pug))      }
            R.id.action_husky    -> { viewModel.getFeed(getString(R.string.menu_item_husky))    }
            R.id.action_hound    -> { viewModel.getFeed(getString(R.string.menu_item_hound))    }
            R.id.action_labrador -> { viewModel.getFeed(getString(R.string.menu_item_labrador)) }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleListClick(position: Int) {
        if (::feedList.isInitialized) {
            feedList.forEach { it.isItemSelected = false }
            feedList[position].isItemSelected = true
            feedAdapter.list = feedList
        }
    }

    private fun setObservers() {
        viewModel.run {
            loadingEvent.observe(this@DogsFeedActivity,Observer { displayLoading(it) })
            errorEvent.observe(this@DogsFeedActivity,  Observer { displayError(it) })
            feedItems.observe(this@DogsFeedActivity,   Observer { displayFeed(it) })
            toolbarTitle.observe(this@DogsFeedActivity,Observer { displayToolbarTitle(it) })
        }
    }

    private fun displayFeed(feedItems: List<FeedItem>) {
        feedList = feedItems
        feedAdapter.list = feedItems
    }

    private fun displayToolbarTitle(title: Any) {
        when(title) {
            is Int    ->  setToolbar(getString(title), true)
            is String ->  setToolbar(title, true)
        }
    }

    private fun displayError(message: Any) {
        when(message) {
            is Int    -> toast(getString(message))
            is String -> toast(message)
        }
    }

    private fun displayLoading(loading: Boolean) {
        progressBar.setVisible(loading)
    }
}
fun Context.createDogsFeedIntent() = intentFor<DogsFeedActivity>()

