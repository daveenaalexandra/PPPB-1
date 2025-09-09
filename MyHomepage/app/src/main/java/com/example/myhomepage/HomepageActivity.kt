package com.example.myhomepage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhomepage.databinding.ActivityHomepageBinding

class HomepageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val Username = intent.getStringExtra(RegisterActivity.username)
        val Email = intent.getStringExtra(RegisterActivity.email)
        val Phone = intent.getStringExtra(RegisterActivity.phone)
        with(binding){
            txtWelname.text = "Welcome $Username"
            if (Email == null){
                txtWelmail.text = "Your email has been activated"
            } else {
                txtWelmail.text = "Your $Email has been activated"
            }
            if (Phone == null){
                txtWelphone.text = "Your phone has been registered"
            } else {
                txtWelphone.text = "Your $Phone has been registered"
            }
        }
    }
}