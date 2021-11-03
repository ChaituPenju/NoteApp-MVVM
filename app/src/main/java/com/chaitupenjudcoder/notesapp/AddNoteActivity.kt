package com.chaitupenjudcoder.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chaitupenjudcoder.notesapp.databinding.ActivityAddNoteBinding
import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.utils.showToast

class AddNoteActivity: AppCompatActivity() {

    companion object {
        const val NOTE_EXTRA = "note_item"
    }

    private lateinit var note: Note
    private lateinit var addNoteBinding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(NOTE_EXTRA)) {
            title = "Edit Note"
            note = intent?.getParcelableExtra<Note>(NOTE_EXTRA) ?: Note(id = -1, "", "", 5)

            addNoteBinding.executePendingBindings()

            addNoteBinding.note = note
        } else {
            title = "Add Note"
            note = Note(id = -1, "", "", 5)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item -> {
                saveItem()
                return true
            }

            else -> Unit
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveItem() {
        val title = addNoteBinding.etTitle.text.toString()
        val desc = addNoteBinding.etDescription.text.toString()
        val priority = addNoteBinding.npPriority.value

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            showToast("Please Enter Title and Description")
            return
        }

        val intent = Intent().apply {
            var editedNote = Note(
                title = title,
                description = desc,
                priority = priority
            )

            if (note.id != -1) {
                editedNote = editedNote.copy(id = note.id)
            } else Unit

            putExtra(NOTE_EXTRA, editedNote)
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}
