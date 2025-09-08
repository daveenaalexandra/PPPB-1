package com.example.intentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import com.example.intentapp.databinding.ActivityMainBinding
import android.net.Uri

private val TAG = "MainActivityLifecycle"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object{
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding){
            btnToSecondActivity.setOnClickListener {
                val intentToSecondActivity =
                    Intent(this@MainActivity, SecondActivity::class.java)
                intentToSecondActivity.putExtra(EXTRA_NAME, edtName.text.toString())
                startActivity(intentToSecondActivity)
            }
            btnSendMessage.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Hello, this is a message from my app!")
                startActivity(Intent.createChooser(intent, "Select an app"))
            }
            btnDial.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:1234567890")
                startActivity(intent)
            }
            btnOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.google.com")
                startActivity(intent)
            }
        }

        Log.d(TAG, "onCreate: dipanggil")
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: dipanggil")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: dipanggil")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: dipanggil")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: dipanggil")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: dipanggil")
    }
}
