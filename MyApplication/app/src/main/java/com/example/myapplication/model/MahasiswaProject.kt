package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class MahasiswaProject(
    @SerializedName("project_id")
    val project_id: Int,

    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("project_type")
    val project_type: String,

    @SerializedName("cover_image_url")
    val cover_image_url: String?
)
