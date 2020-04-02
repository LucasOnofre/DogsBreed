package com.onoffrice.dogsbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class FeedWrapper(
    val category: String,

    @SerializedName("list")
    val dogsFeedList: List<String>
)