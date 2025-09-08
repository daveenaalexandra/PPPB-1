package com.example.intentapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentapp.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data name dari SecondActivity
        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)

        with(binding) {
            btnToSecondActivity.setOnClickListener {
                val resultIntent = Intent()
                // Memasukkan alamat ke Intent
                resultIntent.putExtra(SecondActivity.EXTRA_ADDRESS, edtAddress.text.toString())
                resultIntent.putExtra(MainActivity.EXTRA_NAME, name)
                // Menetapkan result code dan data Intent
                setResult(Activity.RESULT_OK, resultIntent)
                // Menyelesaikan aktivitas
                finish()
            }
        }

        // Edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}