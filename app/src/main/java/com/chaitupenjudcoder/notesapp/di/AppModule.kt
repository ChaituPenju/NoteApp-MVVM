package com.chaitupenjudcoder.notesapp.di

import android.content.Context
import com.chaitupenjudcoder.notesapp.repositories.NoteRepository
import com.chaitupenjudcoder.notesapp.roomdb.NoteDatabase
import com.chaitupenjudcoder.notesapp.viewmodels.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideNoteDatabase(androidContext()) }
    single { provideNoteRepository(get()) }
    viewModel { NoteViewModel(get()) }
}

private fun provideNoteDatabase(context: Context) = NoteDatabase.getNoteDatabase(context.applicationContext)

private fun provideNoteRepository(database: NoteDatabase) = NoteRepository(database.noteDao())