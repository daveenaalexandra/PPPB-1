package com.example.tampilanawalpertemuan1pppb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tampilanawalpertemuan1pppb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            txtNumber.text = number.toString()
            btnCount.setOnClickListener {
                number++
                txtNumber.text = number.toString()
            }
            btnToast.setOnClickListener {
                Toast.makeText(this@MainActivity, "Count $number", Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("CLOG", "OnCreate: log debug")
        Log.e("CLOG", "OnCreate: log error")
        Log.w("CLOG", "OnCreate: log warning")
        Log.i("CLOG", "OnCreate: log info")
    }
}