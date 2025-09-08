package com.example.intentapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        const val EXTRA_ADDRESS = "extra_address"
    }

    // Activity Result API untuk menerima data dari ThirdActivity
    private val launcher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                result ->
            // Memeriksa result code
            if (result.resultCode == Activity.RESULT_OK) {
                // Mengambil data Intent
                val data = result.data
                // Mendapatkan alamat dari data Intent
                val name = data?.getStringExtra(MainActivity.EXTRA_NAME)
                val address = data?.getStringExtra(EXTRA_ADDRESS)
                // Menetapkan teks di TextView
                binding.txtName.text = "$name beralamat di $address"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)
        with(binding) {
            txtName.text = name

            btnToThirdActivity.setOnClickListener {
                val intent = Intent(this@SecondActivity,
                    ThirdActivity::class.java).apply {
                        putExtra(MainActivity.EXTRA_NAME, name)
                }
                launcher.launch(intent)
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}