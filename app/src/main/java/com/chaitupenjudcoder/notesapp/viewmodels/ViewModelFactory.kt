package com.chaitupenjudcoder.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaitupenjudcoder.notesapp.repositories.NoteRepository

class ViewModelFactory(private val repository: NoteRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NoteViewModel::class.java) -> NoteViewModel(repository) as T
            else -> throw IllegalArgumentException("ViewModel not found!")
        }
    }
}