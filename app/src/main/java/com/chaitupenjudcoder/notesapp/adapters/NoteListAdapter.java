package com.chaitupenjudcoder.notesapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitupenjudcoder.notesapp.R;
import com.chaitupenjudcoder.notesapp.databinding.NoteItemBinding;
import com.chaitupenjudcoder.notesapp.models.Note;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteHolder> {

    myItemClickListener m;

    public NoteListAdapter() {
        super(diffCallback);
    }

    public static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle()) &&
                    note.getDescription().equals(t1.getDescription()) &&
                    note.getPriority() == (t1.getPriority());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        NoteItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note note = getItem(i);
        noteHolder.bind(note);
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        NoteItemBinding noteItemBinding;

        NoteHolder(@NonNull NoteItemBinding noteItemBinding) {
            super(noteItemBinding.getRoot());

            this.noteItemBinding = noteItemBinding;
        }

        void bind(Note note) {
            noteItemBinding.setNote(note);

            noteItemBinding.getRoot().setOnClickListener(v -> {
                if (m != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    m.myItemClick(getItem(getAdapterPosition()));
                }
            });

            noteItemBinding.executePendingBindings();
        }
    }

    public interface myItemClickListener {
        void myItemClick(Note note);
    }

    public void setMyItemClickListener(myItemClickListener m) {
        this.m = m;
    }
}
