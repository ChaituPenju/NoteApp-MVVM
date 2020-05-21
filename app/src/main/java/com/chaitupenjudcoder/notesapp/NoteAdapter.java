package com.chaitupenjudcoder.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    myItemClickListener m;

    protected NoteAdapter() {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note note = getItem(i);
        noteHolder.title.setText(note.getTitle());
        noteHolder.description.setText(note.getDescription());
        noteHolder.priority.setText(String.valueOf(note.getPriority()));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title, description, priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            priority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        m.myItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface myItemClickListener {
        void myItemClick(Note note);
    }

    public void setMyItemClickListener(myItemClickListener m) {
        this.m = m;
    }
}
