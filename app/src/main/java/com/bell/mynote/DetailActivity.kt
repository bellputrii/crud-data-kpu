package com.bell.mynote

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etNik: EditText
    private lateinit var etGender: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnBack: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data)

        // Inisialisasi View
        etName = findViewById(R.id.et_dtl_namapemilih)
        etNik = findViewById(R.id.et_dtl_nik)
        etGender = findViewById(R.id.et_dtl_gender)
        etAddress = findViewById(R.id.et_dtl_alamat)
        btnBack = findViewById(R.id.btn_back)

        // Ambil data dari Intent
        val name = intent.getStringExtra("name")
        val nik = intent.getStringExtra("nik")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")

        // Set data ke EditText (read-only)
        etName.setText(name)
        etName.isEnabled = false
        etNik.setText(nik)
        etNik.isEnabled = false
        etGender.setText(gender)
        etGender.isEnabled = false
        etAddress.setText(address)
        etAddress.isEnabled = false

        // Tombol kembali
        btnBack.setOnClickListener {
            finish() // Menutup DetailActivity dan kembali ke layar sebelumnya
        }
    }
}
