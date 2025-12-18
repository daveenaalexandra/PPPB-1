package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("project_id")
    val project_id: Int,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("role")
    val role: String
)
