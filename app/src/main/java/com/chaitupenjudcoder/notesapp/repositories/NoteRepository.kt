package com.chaitupenjudcoder.notesapp.repositories

import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.roomdb.NoteDao

class NoteRepository(
    private val noteDao: NoteDao
) {

    fun getAllNotes() = noteDao.getAllNotes()

    fun getNoteById(id: Int) = noteDao.getNoteById(id)

    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun update(note: Note) = noteDao.update(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()
}