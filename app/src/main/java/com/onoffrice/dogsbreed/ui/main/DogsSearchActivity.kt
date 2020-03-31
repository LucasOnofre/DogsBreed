package com.onoffrice.dogsbreed.ui.main

import android.content.Context
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.ui.base.BaseActivity
import com.onoffrice.dogsbreed.utils.AppInjector
import kotlinx.android.synthetic.main.activity_dogs_search.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class DogsSearchActivity : BaseActivity(R.layout.activity_dogs_search) {

    private val viewModel by lazy {
        val factory = AppInjector.getDogsSearchViewModel()
        ViewModelProvider(this, factory).get(DogsSearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setListeners()

    }

    private fun setListeners() {
        signUpBtn.setOnClickListener {
            viewModel.validateEmail(emailEt.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.run {
            loadingEvent.observe(this@DogsSearchActivity,Observer { displayLoading(it) })
            errorEvent.observe(this@DogsSearchActivity,  Observer { displayError(it) })
            openFeed.observe(this@DogsSearchActivity,  Observer {  })
        }
    }

    private fun displayLoading(loading: Boolean) {
        mainRefresh.isRefreshing = loading
    }

    private fun displayError(message: Any) {
         when(message) {
             is Int    -> toast(getString(message))
             is String -> toast(message)
         }
    }
}
fun Context.createDogSearchIntent() = intentFor<DogsSearchActivity>()

