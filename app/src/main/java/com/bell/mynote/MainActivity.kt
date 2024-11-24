package com.bell.mynote

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bell.mynote.database.NoteDao
import com.bell.mynote.database.NoteRoomDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var noteDao: NoteDao
    private lateinit var listView: ListView
    private lateinit var adapter: DataAdapter
    private lateinit var btnAdd: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi database dan DAO
        val db = NoteRoomDatabase.getDatabase(this)
        noteDao = db.noteDao()

        // Inisialisasi view
        listView = findViewById(R.id.listView)
        btnAdd = findViewById(R.id.btnTambahData)
        btnLogout = findViewById(R.id.btnLogout)

        // Set adapter
        adapter = DataAdapter(this, mutableListOf())
        listView.adapter = adapter

        // Observasi data dari Room Database
        noteDao.getAllNotes().observe(this, Observer { notes ->
            adapter.clear()
            adapter.addAll(notes)
            adapter.notifyDataSetChanged()
        })

        // Tombol tambah data
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // Tombol logout
        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
