package com.chaitupenjudcoder.notesapp

import android.app.Application
import com.chaitupenjudcoder.notesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NotesApplication)
            modules(appModule)
        }
    }
}