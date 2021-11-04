package com.chaitupenjudcoder.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.chaitupenjudcoder.notesapp.databinding.ActivityAddNoteBinding
import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.utils.showToast
import com.chaitupenjudcoder.notesapp.viewmodels.NoteViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNoteActivity: AppCompatActivity() {

    companion object {
        const val NOTE_EXTRA = "note_item"
        const val NOTE_ID_EXTRA = "note_id"
    }

    private val noteViewModel: NoteViewModel by viewModel()

    private lateinit var note: Note
    private lateinit var addNoteBinding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(NOTE_ID_EXTRA)) {
            title = "Edit Note"
            lifecycleScope.launchWhenStarted {
                noteViewModel.getNote(id = intent.getIntExtra(NOTE_ID_EXTRA, 1)).collectLatest {
                    note = it

                    addNoteBinding.executePendingBindings()
                    addNoteBinding.note = note
                }
            }
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
