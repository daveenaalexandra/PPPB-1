package com.example.myhomepage

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhomepage.databinding.ActivityLoginBinding
import com.example.myhomepage.databinding.ActivityMainBinding

private val TAG = "LoginActivityLifecycle"
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val username = "username"
        const val email = "email"
        const val phone = "phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnlogin.setOnClickListener {
                val intentToHomepageActivity =
                    Intent(this@LoginActivity, HomepageActivity::class.java)
                intentToHomepageActivity.putExtra(username, edtUsername.text.toString())
                startActivity(intentToHomepageActivity)
            }
            txtNewreg.setOnClickListener {
                val intentToRegisterActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intentToRegisterActivity)
            }
        }
    }
}