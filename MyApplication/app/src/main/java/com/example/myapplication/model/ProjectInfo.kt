package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ProjectInfo(
    @SerializedName("project_id")
    val project_id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("cover_image_url")
    val cover_image_url: String,
    @SerializedName("youtube_video_url")
    val youtube_video_url: String?,
    @SerializedName("project_type")
    val project_type: String

)
