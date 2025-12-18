package com.example.myapplication.network

import com.example.myapplication.model.Mahasiswa
import com.example.myapplication.model.ProjectData
import com.example.myapplication.model.ProjectDetail
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("allprojects")
    fun getProjects(
        @Query("project_type")
        projectType: String
    ): Call<List<ProjectData>>
    @GET("project/{id}")
    fun getProjectDetail(
        @Path("id")
        projectId: Int
    ): Call<ProjectDetail>
    @GET("mahasiswa/{id}")
    fun getMahasiswa(
        @Path("id")
        mahasiswaId: Int
    ): Call<Mahasiswa>

    @FormUrlEncoded
    @POST("profile/update") // Ensure this matches your routes/api.php exactly
    fun updateMahasiswa(
        @Field("id") id: Int,             // Changed from @Query to @Field
        @Field("username") username: String, // Changed from @Query to @Field
        @Field("nim") nim: String         // Changed from @Query to @Field
    ): Call<Void>


}