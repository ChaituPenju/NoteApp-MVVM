package com.chaitupenjudcoder.notesapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Int
) : Parcelable {
    constructor(title: String, desc: String, priority: Int) : this(
        title = title, description = desc, priority = priority
    )
}
