package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.model.Mahasiswa
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilePage : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etNim: EditText
    private lateinit var btnConfirm: Button
    private lateinit var btnCancel: Button
    private lateinit var imgProfile: ImageView

    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofilepage)

        initViews()

        // 1. Get User ID from the Intent
        userId = intent.getIntExtra("user_id", 0)

        // 2. Load current data so user doesn't have to re-type everything
        if (userId != 0) {
            loadCurrentData(userId)
        }

        // 3. Handle Cancel
        btnCancel.setOnClickListener {
            finish() // Go back to previous page
        }

        // 4. Handle Confirm (Update Data)
        btnConfirm.setOnClickListener {
            val newName = etUsername.text.toString()
            val newNim = etNim.text.toString()
            updateProfile(userId, newName, newNim)
        }
    }

    private fun initViews() {
        etUsername = findViewById(R.id.et_username)
        etNim = findViewById(R.id.et_nim)
        btnConfirm = findViewById(R.id.btn_confirm)
        btnCancel = findViewById(R.id.btn_cancel)

    }

    private fun loadCurrentData(id: Int) {
        // Reuse your existing getMahasiswa logic to fill the boxes
        ApiClient.getInstance().getMahasiswa(id).enqueue(object : Callback<Mahasiswa> {
            override fun onResponse(call: Call<Mahasiswa>, response: Response<Mahasiswa>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    etUsername.setText(user.username)
                    etNim.setText(user.nim)

                }
            }
            override fun onFailure(call: Call<Mahasiswa>, t: Throwable) { }
        })
    }

    private fun updateProfile(id: Int, username: String, nim: String) {
        // CALL THE API TO UPDATE (See Step 3)
        ApiClient.getInstance().updateMahasiswa(id, username, nim).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditProfilePage, "Profile Updated!", Toast.LENGTH_SHORT).show()
                    finish() // Close activity and return to ProfilePage
                } else {
                    Toast.makeText(this@EditProfilePage, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditProfilePage, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}