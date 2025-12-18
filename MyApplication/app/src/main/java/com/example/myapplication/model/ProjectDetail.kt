package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ProjectDetail(
    @SerializedName("project")
    val project: ProjectInfo,
    @SerializedName("users")
    val users: List<UserTeam>
)




