package com.chaitupenjudcoder.notesapp.roomdb;

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chaitupenjudcoder.notesapp.models.Note

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
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }

    private fun roomCallback() = object: RoomDatabase.Callback() {
        override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
            super.onCreate(db);

        }
    };
}
