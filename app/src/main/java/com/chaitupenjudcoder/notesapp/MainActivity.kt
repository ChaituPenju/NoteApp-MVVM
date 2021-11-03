package com.chaitupenjudcoder.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenjudcoder.notesapp.adapters.NoteListAdapter
import com.chaitupenjudcoder.notesapp.databinding.ActivityMainBinding
import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.viewmodels.NoteViewModel

class MainActivity: AppCompatActivity() {

    companion object {
        private const val ADD_REQUEST_CODE = 1
        private const val EDIT_REQUEST_CODE = 2
    }

    private var noteViewModel: NoteViewModel? = null
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        noteRecyclerView = noteBinding.rvNotes

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        setupNotesRecyclerView()

        noteBinding.fabAddnote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            val title = data!!.getStringExtra(AddNoteActivity.TITLE_EXTRA)
            val desc = data.getStringExtra(AddNoteActivity.DESCRIPTION_EXTRA)
            val priority = data.getIntExtra(AddNoteActivity.PRIORITY_EXTRA, 1)
            val note = Note(title = title, description = desc, priority = priority)
            noteViewModel!!.insert(note)
            Toast.makeText(this, "Note Saved!!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            val id = data!!.getIntExtra(AddNoteActivity.ID_EXTRA, -1)
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }
            val title = data.getStringExtra(AddNoteActivity.TITLE_EXTRA)
            val description = data.getStringExtra(AddNoteActivity.DESCRIPTION_EXTRA)
            val priority = data.getIntExtra(AddNoteActivity.PRIORITY_EXTRA, 1)
            val note = Note(title = title, description = description, priority = priority)

            noteViewModel!!.update(note)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note Not Saved!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNotesRecyclerView() {
        val notesAdapter = NoteListAdapter { note ->
            val intent = Intent(
                this@MainActivity,
                AddNoteActivity::class.java
            )
            intent.putExtra(AddNoteActivity.ID_EXTRA, note.id)
            intent.putExtra(AddNoteActivity.TITLE_EXTRA, note.title)
            intent.putExtra(AddNoteActivity.DESCRIPTION_EXTRA, note.description)
            intent.putExtra(AddNoteActivity.PRIORITY_EXTRA, note.priority)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }

        noteViewModel!!.allNotes.observe(this, { list ->
            notesAdapter.submitList(list)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                noteViewModel!!.delete(notesAdapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(noteRecyclerView)
    }
}