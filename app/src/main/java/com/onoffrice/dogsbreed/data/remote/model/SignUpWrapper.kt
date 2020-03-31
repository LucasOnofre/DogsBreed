package com.onoffrice.dogsbreed.data.remote.model

import com.google.gson.annotations.SerializedName


data class SignUpWrapper(
    val user: User
)

data class User(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val token: String
)