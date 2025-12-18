package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Mahasiswa(
    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("angkatan")
    val angkatan: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("linkedin")
    val linkedin: String?, // Nullable because users might not set this

    @SerializedName("instagram")
    val instagram: String?,

    @SerializedName("profile_picture")
    val profile_picture: String?,

    @SerializedName("phone_number")
    val phone_number: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("nim")
    val nim: String,

    @SerializedName("background")
    val background: String?,

    // IMPORTANT: Laravel relations return Arrays [], so this must be a List
    @SerializedName("projects")
    val projects: List<MahasiswaProject> = emptyList()
) {
    // Helper to get that single project easily without typing .projects[0]
    val latestProject: MahasiswaProject?
        get() = projects.firstOrNull()
}
