package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("pivot")
    val pivot: Pivot
)
