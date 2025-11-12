package com.example.hellocompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.ui.theme.HelloComposeTheme // <-- Sesuaikan jika nama tema beda
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    // Memanggil fungsi Composable utama
                    // paddingValues dilewatkan oleh Scaffold, tapi di modul ini tidak digunakan
                    HelloToastScreen()
                }
            }
        }
    }
}
@Composable
fun HelloToastScreen() {
    // Dapatkan konteks lokal untuk digunakan oleh Toast
    val ctx = LocalContext.current

    // Buat state untuk 'count' yang bisa bertahan dari perubahan konfigurasi
    var count by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding untuk keseluruhan kolom
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tombol TOAST
        Button(
            onClick = {
                // Aksi untuk menampilkan Toast
                Toast.makeText(ctx, "Count $count", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.toast))
        }

        // Box kuning untuk menampilkan angka
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Mengisi sisa ruang vertikal
                .background(color = colorResource(id = R.color.yellow)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                fontSize = 64.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        // Tombol COUNT
        Button(
            onClick = {
                // Aksi untuk menambah 'count'
                count++
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.count))
        }
    }
}

@Composable
fun HelloToastScreenRowButtons() {
    val ctx = LocalContext.current
    var count by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box kuning di bagian atas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Memberi bobot 1 agar Box mengisi ruang atas
                .padding(vertical = 16.dp) // Padding di dalam Box
                .background(color = colorResource(id = R.color.yellow)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                fontSize = 64.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        // Row untuk tombol-tombol di bagian bawah
        Row(
            modifier = Modifier.fillMaxWidth(),
            // Memberi jarak 8.dp antar item di dalam Row
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tombol TOAST
            Button(
                onClick = {
                    Toast.makeText(ctx, "Count $count", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.weight(1f) // Bobot 1
            ) {
                Text(text = stringResource(id = R.string.toast))
            }

            // Tombol COUNT
            Button(
                onClick = { count++ },
                modifier = Modifier.weight(1f) // Bobot 1
            ) {
                Text(text = stringResource(id = R.string.count))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelloToastPreview() {
    HelloComposeTheme {
//        HelloToastScreen()
        HelloToastScreenRowButtons()
    }
}
