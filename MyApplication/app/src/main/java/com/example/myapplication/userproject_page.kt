package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Adapter.TeamAdapter
import com.example.myapplication.model.ProjectData
import com.example.myapplication.model.ProjectDetail
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback // Import correct Callback
import retrofit2.Response

class userproject_page : AppCompatActivity() {

    // 1. Declare UI Variables
    private lateinit var tvProjectName: TextView
    private lateinit var tvDescription: TextView

    private lateinit var tvTeamName: TextView
    private lateinit var imgProject: ImageView
    private lateinit var rvTeam: RecyclerView



    // 2. Declare Adapter for the Team List
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userproject_page)

        // 3. Initialize Views (Connect to XML IDs)
        initViews()

        // 4. Setup RecyclerView for Team Members
        setupRecyclerView()

        // 5. Get ID from Intent
        // "project_id" must match the key used in HomeActivity
        val projectId = intent.getIntExtra("project_id", 0)

        if (projectId != 0) {
            fetchProjectDetail(projectId)
        } else {
            Toast.makeText(this, "Error: Invalid Project ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        tvProjectName = findViewById(R.id.tv_project_name)
        tvDescription = findViewById(R.id.tv_Deskripsi) // Check your XML ID
        imgProject = findViewById(R.id.Img_Project)
        tvTeamName = findViewById(R.id.tv_Nama_Team)
        rvTeam = findViewById(R.id.rvTeamMembers) // The RecyclerView in your layout
    }

    private fun setupRecyclerView() {
        // Initialize adapter with empty list first
        teamAdapter = TeamAdapter(emptyList()) {userTeam ->
            Toast.makeText(this, "Clicked: ${userTeam.username}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfilePage::class.java)
            intent.putExtra("user_id", userTeam.user_id)
            startActivity(intent)
        }
        rvTeam.adapter = teamAdapter
        rvTeam.layoutManager = GridLayoutManager(this, 2) // Grid for team members
    }

    private fun fetchProjectDetail(projectId: Int) {
        val apiService = ApiClient.getInstance()

        // Ensure your ApiService returns Call<ProjectDetail>
        apiService.getProjectDetail(projectId).enqueue(object : Callback<ProjectDetail> {

            override fun onResponse(
                call: Call<ProjectDetail>,
                response: Response<ProjectDetail>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!

                    // --- PART A: Fill Static Data (Project Info) ---
                    val project = data.project
                    val title = project.title
                    tvProjectName.text = title
                    tvDescription.text = project.description


                    val fullUrl = "http://192.168.1.2:8000/storage/" + project.cover_image_url
                    Glide.with(this@userproject_page)
                        .load(fullUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(android.R.drawable.ic_dialog_alert)
                        .into(imgProject)

                    // --- PART B: Fill List Data (Team Adapter) ---
                    val usersList = data.users

                    val manager = usersList.find { it.role == "Project Manager" }
                    val managername = manager?.username

                    if (manager != null) {
                        tvTeamName.text = "$managername team"
                    } else {
                        tvTeamName.text = "$title team"
                    }

                    // Pass the LIST of users to the adapter, not the project object
                    teamAdapter.updateData(usersList)

                } else {
                    Toast.makeText(this@userproject_page, "Data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProjectDetail>, t: Throwable) {
                Toast.makeText(this@userproject_page, "Connection error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}