package com.onoffrice.dogsbreed.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.ui.main.createDogSearchIntent
import com.onoffrice.dogsbreed.utils.extensions.startActivitySlideTransition
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setLogoAnimation()
        setDelayForActivity()
    }

    private fun setDelayForActivity() {
        val handle = Handler()
        handle.postDelayed({
            startActivitySlideTransition(createDogSearchIntent())
            finish()
            }, 4000)
    }

    private fun setLogoAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_transition)
        animation.repeatCount    = 1
        animation.duration       = 2000
        animation.fillAfter      = true
        animation.repeatMode     = Animation.REVERSE
        splash_logo.startAnimation(animation)
    }
}
