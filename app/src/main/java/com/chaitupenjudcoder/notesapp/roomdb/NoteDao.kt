package com.chaitupenjudcoder.notesapp.roomdb;

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chaitupenjudcoder.notesapp.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()
}