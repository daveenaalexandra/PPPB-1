package com.example.myhomepage

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhomepage.databinding.ActivityMainBinding

private val TAG = "MainActivityLifecycle"
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val username = "username"
        const val email = "email"
        const val phone = "phone"
        const val password = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnregis.setOnClickListener {
                val intentToHomepageActivity = Intent(this@RegisterActivity, HomepageActivity::class.java)
                intentToHomepageActivity.putExtra(username, edtUsername.text.toString())
                intentToHomepageActivity.putExtra(email, edtEmail.text.toString())
                intentToHomepageActivity.putExtra(phone, edtPhone.text.toString())
                startActivity(intentToHomepageActivity)
            }
            txtAlrlog.setOnClickListener {
                val intentToLoginActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intentToLoginActivity)
            }
        }
    }
}