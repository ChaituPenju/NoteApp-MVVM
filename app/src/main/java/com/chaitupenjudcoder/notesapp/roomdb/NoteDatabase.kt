package com.chaitupenjudcoder.notesapp.roomdb;

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chaitupenjudcoder.notesapp.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getNoteDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(notesInsertCallback())
                    .build()

                INSTANCE = instance

                instance
            }
        }

        private fun notesInsertCallback() = object: RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db);

                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let {
                        it.noteDao().insert(Note(title = "Note One", description = "The First Note", priority = 3))
                        it.noteDao().insert(Note(title = "Note Two", description = "The Second Note", priority = 5))
                        it.noteDao().insert(Note(title = "Note Three", description = "The Third Note", priority = 4))
                    }
                }
            }
        }
    }
}
