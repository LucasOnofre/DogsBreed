package com.onoffrice.dogsbreed.utils.helpers

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.utils.extensions.loadImage
import kotlinx.android.synthetic.main.image_modal_component.*

class ImageModal(context: Context, val imageUrl: String) : Dialog (context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_modal_component)
        setConfigs()
        loadImage(imageUrl)
    }

    private fun setConfigs() {
        this.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun loadImage(imageUrl: String) {
        imagePreview.loadImage(imageUrl)
    }
}