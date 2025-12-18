package com.example.myapplication // Sesuaikan package

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ProjectAdapter
import com.example.myapplication.model.ProjectData
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter

    // UI variable
    private lateinit var btnPad1: LinearLayout
    private lateinit var btnPad2: LinearLayout
    private lateinit var tvPad1: TextView
    private lateinit var tvPad2: TextView
    private lateinit var linePad1: View
    private lateinit var linePad2: View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        initViews()

        // recycle view
        recyclerView = findViewById(R.id.rvProject)
        // mastiin 2 culum
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        // adapter
        adapter = ProjectAdapter(emptyList()) { project ->
            // This code runs when an item is clicked
            Toast.makeText(this, "Clicked: ${project.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, userproject_page::class.java)
            intent.putExtra("project_id", project.project_id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        btnPad1.setOnClickListener {
            updateTabUI(isPad1Active=true)
            Toast.makeText(this, "PAD 1 Clicked", Toast.LENGTH_SHORT).show()
            fetchData("PAD 1")
        }

        btnPad2.setOnClickListener {
            updateTabUI(isPad1Active=false)
            Toast.makeText(this, "PAD 1 Clicked", Toast.LENGTH_SHORT).show()
            fetchData("PAD 2")
        }



        fetchData("PAD 1")
    }

    private fun initViews() {
        btnPad1 = findViewById(R.id.btn_pad1)
        btnPad2 = findViewById(R.id.btn_pad2)
        tvPad1 = findViewById(R.id.tv_pad1)
        tvPad2 = findViewById(R.id.tv_pad2)
        linePad1 = findViewById(R.id.line_pad1)
        linePad2 = findViewById(R.id.line_pad2)
    }

    private fun fetchData(type: String) {
        val apiService = ApiClient.getInstance()
        val call = apiService.getProjects(type)
        call.enqueue(object : retrofit2.Callback<List<ProjectData>> {
            override fun onResponse(
                call: Call<List<ProjectData>?>,
                response: Response<List<ProjectData>?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val pageResponse = response.body()!!
                    val project = pageResponse


                    adapter.updateData(project)
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal mengambil data", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(
                call: Call<List<ProjectData>?>,
                t: Throwable
            ) {
                Log.e("HomeActivity", "onFailure: ${t.message}")
                Toast.makeText(this@HomeActivity, "Gagal mengambil data onfailure", Toast.LENGTH_SHORT).show()
            }

        })
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
}
