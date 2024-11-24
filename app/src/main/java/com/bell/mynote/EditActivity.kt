package com.bell.mynote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bell.mynote.database.Note
import com.bell.mynote.database.NoteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditActivity : AppCompatActivity() {

    private var noteId: Int? = null
    private lateinit var etName: EditText
    private lateinit var etNik: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var etAddress: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Inisialisasi View
        etName = findViewById(R.id.etName)
        etNik = findViewById(R.id.etNik)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.radio1_laki2)
        rbFemale = findViewById(R.id.radio1_perempuan)
        etAddress = findViewById(R.id.etAddress)
        btnSave = findViewById(R.id.btnSave)

        // Ambil ID dari Intent
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId != -1) {
            loadNoteData(noteId!!)
        }

        // Simpan perubahan
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val nik = etNik.text.toString()
            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.radio1_laki2 -> "Laki-laki"
                R.id.radio1_perempuan -> "Perempuan"
                else -> ""
            }
            val address = etAddress.text.toString()

            if (name.isNotEmpty() && nik.isNotEmpty() && gender.isNotEmpty() && address.isNotEmpty()) {
                updateNote(noteId!!, name, nik, gender, address)
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadNoteData(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = NoteRoomDatabase.getDatabase(this@EditActivity)
            val note = db.noteDao().getNoteById(id)
            withContext(Dispatchers.Main) {
                note?.let {
                    etName.setText(it.namapemilih)
                    etNik.setText(it.nik)
                    etAddress.setText(it.alamat)
                    when (it.gender) {
                        "Laki-laki" -> rbMale.isChecked = true
                        "Perempuan" -> rbFemale.isChecked = true
                    }
                }
            }
        }
    }

    private fun updateNote(id: Int, name: String, nik: String, gender: String, address: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = NoteRoomDatabase.getDatabase(this@EditActivity)
            val updatedNote = Note(id, name, nik, gender, address)
            db.noteDao().update(updatedNote)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EditActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}


