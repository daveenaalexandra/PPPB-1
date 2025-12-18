package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.model.Mahasiswa
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class ProfilePage : AppCompatActivity() {
    private lateinit var imgProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvNim: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var imgProject: ImageView
    private lateinit var tvProjectName: TextView

    private lateinit var btnLogout: ImageView

    // UI variable
    private lateinit var btnPad1: LinearLayout
    private lateinit var btnPad2: LinearLayout
    private lateinit var tvPad1: TextView
    private lateinit var tvPad2: TextView
    private lateinit var linePad1: View
    private lateinit var linePad2: View

    private var currentTab = "PAD 1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        // initial view
        initViews()

        // get Id from intent
        val userId = intent.getIntExtra("user_id", 0)

        if (userId != 0) {
            fetchUserData(userId, "PAD 1")
        } else {
            Toast.makeText(this, "Error: Invalid User ID", Toast.LENGTH_SHORT).show()
        }

        btnPad1.setOnClickListener {
            updateTabUI(isPad1Active=true)
            Toast.makeText(this, "PAD 1 Clicked", Toast.LENGTH_SHORT).show()
            fetchUserData(userId,"PAD 1")

        }

        btnPad2.setOnClickListener {
            updateTabUI(isPad1Active=false)
            Toast.makeText(this, "PAD 1 Clicked", Toast.LENGTH_SHORT).show()
            fetchUserData(userId,"PAD 2")
        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EditProfilePage::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        }






    }

    override fun onResume() {
        super.onResume()
        val userId = intent.getIntExtra("user_id", 0)
        if (userId != 0) {
            // Reload data to reflect changes made in EditProfilePage
            fetchUserData(userId, currentTab)
        }
    }

    private fun updateTabUI(isPad1Active: Boolean) {
        val activeColor = android.graphics.Color.parseColor("#00897B")
        val inactiveColor = android.graphics.Color.parseColor("#E0E0E0")

        if (isPad1Active) {
            tvPad1.setTextColor(activeColor)
            linePad1.setBackgroundColor(activeColor)

            tvPad2.setTextColor(inactiveColor)
            linePad2.setBackgroundColor(inactiveColor)
        } else {
            tvPad1.setTextColor(inactiveColor)
            linePad1.setBackgroundColor(inactiveColor)

            tvPad2.setTextColor(activeColor)
            linePad2.setBackgroundColor(activeColor)
        }


    }

    private fun initViews() {
        // Existing views
        imgProfile = findViewById(R.id.Profile_picture)
        tvName = findViewById(R.id.UserName)
        tvNim = findViewById(R.id.NIM)
        btnEditProfile = findViewById(R.id.editprofile)
        imgProject = findViewById(R.id.Img_Project)
        tvProjectName = findViewById(R.id.tvProjectName)
        btnLogout = findViewById(R.id.ic_logout)


        // --- ADD THESE LINES ---
        // Make sure these IDs exist in your XML layout!
        btnPad1 = findViewById(R.id.btn_pad1)
        btnPad2 = findViewById(R.id.btn_pad2)
        tvPad1 = findViewById(R.id.tv_pad1)
        tvPad2 = findViewById(R.id.tv_pad2)
        linePad1 = findViewById(R.id.line_pad1)
        linePad2 = findViewById(R.id.line_pad2)
    }

    private fun fetchUserData(userId: Int, type: String) {
        val apiService = ApiClient.getInstance()
        val call = apiService.getMahasiswa(userId)
            .enqueue(object : retrofit2.Callback<Mahasiswa> {

                override fun onResponse(
                    call: Call<Mahasiswa?>,
                    response: Response<Mahasiswa?>
                ) {
                    if (response.isSuccessful && response.body() != null) {

                        val data = response.body()!!

                        // 1. Load User Profile (Always the same regardless of tab)
                        tvName.text = data.username
                        tvNim.text = data.nim

                        val fullProfileUrl = "http://192.168.1.2:8000/storage/" + data.profile_picture
                        Glide.with(this@ProfilePage)
                            .load(fullProfileUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imgProfile)

                        // 2. Filter Projects based on the 'type' ("PAD 1" or "PAD 2")
                        val projectList = data.projects

                        // THIS IS THE KEY CHANGE:
                        // Find the first project where project_type matches the 'type' string
                        // We use equals(..., true) to ignore case (e.g., "pad 1" vs "PAD 1")
                        val filteredProject = projectList.find {
                            it.project_type.equals(type, ignoreCase = true)
                        }

                        // 3. Update Project UI
                        if (filteredProject != null) {
                            tvProjectName.text = filteredProject.title

                            val fullProjectUrl = "http://192.168.1.2:8000/storage/" + filteredProject.cover_image_url
                            Glide.with(this@ProfilePage)
                                .load(fullProjectUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(imgProject)
                        } else {
                            // Handle case where user has no project of this type
                            tvProjectName.text = "No $type Project Found"
                            imgProject.setImageResource(R.drawable.ic_launcher_background) // Set a default/empty image
                        }

                    } else {
                        Toast.makeText(this@ProfilePage, "Data not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Mahasiswa?>, t: Throwable) {
                    Toast.makeText(this@ProfilePage, "Connection error", Toast.LENGTH_SHORT).show()
                }

            })
    }

}