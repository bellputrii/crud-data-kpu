package com.bell.mynote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)  // Menggunakan Note, bukan DataItem

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>  // Menggunakan Note, bukan DataItem

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

}
