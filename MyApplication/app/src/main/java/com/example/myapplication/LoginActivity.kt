package com.example.myapplication // GANTI dengan nama package Anda yang sesuai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    // Launcher untuk menangani hasil login Google
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pastikan nama file XML layout sesuai, misalnya: activity_login.xml
        setContentView(R.layout.login_page)

        // 1. Konfigurasi Google Sign In (Filter Domain UGM)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .setHostedDomain("mail.ugm.ac.id") // Wajib domain UGM
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 2. Inisialisasi Komponen UI
        val btnGoogleUgm = findViewById<LinearLayout>(R.id.btn_google_ugm)
        val btnGuest = findViewById<AppCompatButton>(R.id.btn_guest)
        val tvSignUp = findViewById<TextView>(R.id.tv_signup)

        // 3. Aksi Tombol Login Google
        btnGoogleUgm.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

        // 4. Aksi Tombol Guest
        btnGuest.setOnClickListener {
            Toast.makeText(this, "Masuk sebagai Tamu", Toast.LENGTH_SHORT).show()

            // Pindah ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Tutup LoginActivity agar tidak bisa kembali
        }

        // 5. Aksi Teks Sign Up (Opsional)
        tvSignUp.setOnClickListener {
            // Jika Sign Up arahnya sama (Google Login), panggil fungsi yang sama
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Fungsi Logika Pengecekan Email
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email

            // DEBUG: Munculkan toast email apa yang terdeteksi
            // Toast.makeText(this, "Email terdeteksi: $email", Toast.LENGTH_SHORT).show()

            if (email != null && email.endsWith("@mail.ugm.ac.id")) {
                // === KONDISI 1: SUKSES ===
                Toast.makeText(this, "Login Sukses! Mengalihkan...", Toast.LENGTH_SHORT).show()
                Log.d("LOGIN_UGM", "Sukses. Token: ${account.idToken}")

                // Pindah ke Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                // === KONDISI 2: EMAIL SALAH ===
                signOut() // Logout paksa
                Toast.makeText(this, "Gagal: Email $email bukan @mail.ugm.ac.id", Toast.LENGTH_LONG).show()
                Log.e("LOGIN_UGM", "Email salah: $email")
            }

        } catch (e: ApiException) {
            // === KONDISI 3: ERROR DARI GOOGLE ===
            // Ini yang paling sering terjadi jika SHA-1 belum diatur
            Log.w("LOGIN_UGM", "signInResult:failed code=" + e.statusCode)

            if (e.statusCode == 10) {
                Toast.makeText(this, "Error 10: SHA-1 belum terdaftar di Firebase", Toast.LENGTH_LONG).show()
            } else if (e.statusCode == 12500) {
                Toast.makeText(this, "Error 12500: Cek nama package / SHA-1", Toast.LENGTH_LONG).show()
            } else if (e.statusCode == 7) {
                Toast.makeText(this, "Error 7: Tidak ada koneksi internet", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Google Login Error: ${e.statusCode}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Fungsi Logout Helper
    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this) {
            // User berhasil logout dari sesi Google di aplikasi
        }
    }
}