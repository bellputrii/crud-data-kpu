package com.bell.mynote

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bell.mynote.database.Note
import com.bell.mynote.database.NoteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataAdapter(private val context: Context, private val notes: MutableList<Note>) :
    ArrayAdapter<Note>(context, 0, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false)
        val note = getItem(position)

        // Inisialisasi view
        val txtId = view.findViewById<TextView>(R.id.txtId)
        val txtName = view.findViewById<TextView>(R.id.txtNama)
        val btnEdit = view.findViewById<ImageView>(R.id.iconEdit)
        val btnView = view.findViewById<ImageView>(R.id.iconView)
        val btnDelete = view.findViewById<ImageView>(R.id.iconDelete)

        // Set data ke tampilan
        txtId.text = note?.id.toString()
        txtName.text = note?.namapemilih

        // Aksi tombol
        btnEdit.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("note_id", note?.id) // Kirim ID note ke EditActivity
            context.startActivity(intent)
        }

        btnView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("note_id", note?.id)
            intent.putExtra("name", note?.namapemilih)
            intent.putExtra("nik", note?.nik)
            intent.putExtra("gender", note?.gender)
            intent.putExtra("address", note?.alamat)
            context.startActivity(intent)
        }

        btnDelete.setOnClickListener {
            (context as MainActivity).lifecycleScope.launch(Dispatchers.IO) {
                val db = NoteRoomDatabase.getDatabase(context)
                db.noteDao().delete(note!!)
            }
        }

        return view
    }
}