package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ProjectData(
    @SerializedName("project_id")
    val project_id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_image_url")
    val cover_image_url: String,
    @SerializedName("users")
    val users: List<User>
)
