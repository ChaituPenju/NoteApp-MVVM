package com.chaitupenjudcoder.notesapp.viewmodels;

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
): ViewModel() {

    val allNotes: LiveData<List<Note>>
        get() = repository.getAllNotes()

    fun getNote(id: Int): Flow<Note> = repository.getNoteById(id)


    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note);
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note);
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note);
    }

    fun deleteAllNotes() = viewModelScope.launch {
        repository.deleteAllNotes();
    }
}
