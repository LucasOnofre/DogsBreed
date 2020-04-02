package com.onoffrice.dogsbreed.ui.main

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.ui.base.BaseActivity
import com.onoffrice.dogsbreed.ui.dogsFeed.createDogsFeedIntent
import com.onoffrice.dogsbreed.utils.extensions.startActivitySlideTransition
import com.onoffrice.dogsbreed.utils.extensions.validateEmailPattern
import kotlinx.android.synthetic.main.activity_dogs_search.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DogsSearchActivity : BaseActivity(R.layout.activity_dogs_search) {

    val viewModel: DogsSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setListeners()
        setViews()
    }

    private fun setViews() {
        emailEt.requestFocus()
    }

    private fun setListeners() {
        signUpBtn.setOnClickListener {
            validateEmail(emailEt.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.run {
            loadingEvent.observe(this@DogsSearchActivity,Observer { displayLoading(it) })
            errorEvent.observe(this@DogsSearchActivity,  Observer { displayError(it) })
            openFeed.observe(this@DogsSearchActivity,    Observer { startActivitySlideTransition(createDogsFeedIntent()) })
        }
    }

    private fun validateEmail(email: String) {
        if (email.validateEmailPattern()) {
            viewModel.makeSignUp(email)
        } else {
            toast(getString(R.string.erro_invalid_email))
        }
    }

    private fun displayLoading(loading: Boolean) {
        mainRefresh.isRefreshing = loading
    }

    private fun displayError(message: String) {
        toast(message)
    }
}
fun Context.createDogSearchIntent() = intentFor<DogsSearchActivity>()

