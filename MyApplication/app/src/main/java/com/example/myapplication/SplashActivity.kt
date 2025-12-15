package com.example.myapplication // Sesuaikan package

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Tahan selama 3000ms (3 detik), lalu pindah
        Handler(Looper.getMainLooper()).postDelayed({

            // Pindah ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Tutup SplashActivity agar user tidak bisa kembali ke sini saat tekan Back
            finish()

        }, 3000) // Waktu tunda dalam milidetik
    }
}