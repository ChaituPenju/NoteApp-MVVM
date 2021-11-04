package com.chaitupenjudcoder.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenjudcoder.notesapp.AddNoteActivity.Companion.NOTE_EXTRA
import com.chaitupenjudcoder.notesapp.adapters.NoteListAdapter
import com.chaitupenjudcoder.notesapp.databinding.ActivityMainBinding
import com.chaitupenjudcoder.notesapp.models.Note
import com.chaitupenjudcoder.notesapp.utils.showToast
import com.chaitupenjudcoder.notesapp.viewmodels.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private val noteViewModel by viewModel<NoteViewModel>()

    companion object {
        private const val ADD_REQUEST_CODE = 1
        private const val EDIT_REQUEST_CODE = 2
    }

    private lateinit var noteBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNotesRecyclerView()

        noteBinding.fabAddnote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK -> {
                data?.getParcelableExtra<Note>(AddNoteActivity.NOTE_EXTRA)?.let { note ->
                    noteViewModel.insert(note)
                    showToast("Note Saved!!")
                } ?: run {
                    showToast("Note is Empty! Can't Save!")
                }

            }

            requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK -> {
                data?.getParcelableExtra<Note>(AddNoteActivity.NOTE_EXTRA)?.let { note ->
                    noteViewModel.update(note)
                    showToast("Note updated")
                } ?: run {
                    showToast("Note can't be updated")
                }
            }

            else -> showToast("Note Not Saved!!!")
        }
    }

    private fun setupNotesRecyclerView() {
        val notesAdapter = NoteListAdapter { note ->
            val intent = Intent(
                this@MainActivity,
                AddNoteActivity::class.java
            ).apply {
                putExtra(NOTE_EXTRA, note)
            }

            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        noteBinding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }

        noteViewModel.allNotes.observe(this, { list ->
            notesAdapter.submitList(list)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                noteViewModel.delete(notesAdapter.getNoteAt(viewHolder.adapterPosition))
                showToast("Note Deleted")
            }
        }).attachToRecyclerView(noteBinding.rvNotes)
    }
}