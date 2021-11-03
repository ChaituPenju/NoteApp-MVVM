package com.chaitupenjudcoder.notesapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenjudcoder.notesapp.databinding.NoteItemBinding
import com.chaitupenjudcoder.notesapp.models.Note

class NoteListAdapter(
    private val noteClickListener : (Note) -> Unit
): ListAdapter<Note, NoteListAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)

        holder.itemView.setOnClickListener {
            noteClickListener.invoke(note)
        }
    }

    fun getNoteAt(position: Int) = getItem(position)


    class NotesViewHolder(private val noteItemBinding: NoteItemBinding): RecyclerView.ViewHolder(noteItemBinding.root) {
        fun bind(note: Note) {
            noteItemBinding.note = note

            noteItemBinding.executePendingBindings()
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldNote: Note, newNote: Note): Boolean {
            return oldNote.id == newNote.id
        }

        override fun areContentsTheSame(oldNote: Note, newNote: Note): Boolean {
            return oldNote.title == newNote.title && oldNote.description == newNote.description && oldNote.priority == newNote.priority
        }
    }
}