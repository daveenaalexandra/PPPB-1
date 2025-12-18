package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class UserTeam(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("profile_picture")
    val profile_picture: String?
)
