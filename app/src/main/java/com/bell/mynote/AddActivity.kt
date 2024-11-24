package com.bell.mynote

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bell.mynote.database.Note
import com.bell.mynote.database.NoteRoomDatabase
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var etNamaPemilih: EditText
    private lateinit var etNik: EditText
    private lateinit var radioGender: RadioGroup
    private lateinit var etAlamat: EditText
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahdata)

        // Inisialisasi komponen dari layout
        etNamaPemilih = findViewById(R.id.et_add_namapemilih)
        etNik = findViewById(R.id.et_add_nik)
        radioGender = findViewById(R.id.radioGender)
        etAlamat = findViewById(R.id.et_add_alamat)
        btnSimpan = findViewById(R.id.btn_simpan_add_data)

        // Aksi saat tombol simpan diklik
        btnSimpan.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        // Ambil data dari input pengguna
        val namaPemilih = etNamaPemilih.text.toString().trim()
        val nik = etNik.text.toString().trim()
        val genderId = radioGender.checkedRadioButtonId
        val alamat = etAlamat.text.toString().trim()

        // Validasi input
        if (namaPemilih.isEmpty()) {
            etNamaPemilih.error = "Nama Pemilih wajib diisi"
            return
        }

        if (nik.isEmpty()) {
            etNik.error = "NIK wajib diisi"
            return
        }

        if (genderId == -1) {
            Toast.makeText(this, "Pilih gender", Toast.LENGTH_SHORT).show()
            return
        }

        if (alamat.isEmpty()) {
            etAlamat.error = "Alamat wajib diisi"
            return
        }

        // Ambil nilai gender berdasarkan ID RadioButton yang dipilih
        val gender = findViewById<RadioButton>(genderId).text.toString()

        // Simpan data ke database menggunakan Coroutine
        val note = Note(
            namapemilih = namaPemilih,
            nik = nik,
            gender = gender,
            alamat = alamat
        )

        val noteDao = NoteRoomDatabase.getDatabase(this).noteDao()

        lifecycleScope.launch {
            noteDao.insert(note)
            Toast.makeText(this@AddActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish() // Kembali ke aktivitas sebelumnya
        }
    }
}
