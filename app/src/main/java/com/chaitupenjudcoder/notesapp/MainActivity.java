package com.chaitupenjudcoder.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaitupenjudcoder.notesapp.adapters.NoteListAdapter;
import com.chaitupenjudcoder.notesapp.databinding.ActivityMainBinding;
import com.chaitupenjudcoder.notesapp.models.Note;
import com.chaitupenjudcoder.notesapp.viewmodels.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.chaitupenjudcoder.notesapp.AddNoteActivity.DESCRIPTION_EXTRA;
import static com.chaitupenjudcoder.notesapp.AddNoteActivity.ID_EXTRA;
import static com.chaitupenjudcoder.notesapp.AddNoteActivity.PRIORITY_EXTRA;
import static com.chaitupenjudcoder.notesapp.AddNoteActivity.TITLE_EXTRA;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private RecyclerView rv;
    private FloatingActionButton addNoteButton;
    private int ADD_REQUEST_CODE = 1;
    private int EDIT_REQUEST_CODE = 2;
    private ActivityMainBinding noteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        rv = noteBinding.rvNotes;
        addNoteButton = noteBinding.fabAddnote;

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        setupNotesRecyclerView();

        addNoteButton.setOnClickListener(v -> {
            Intent it = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivityForResult(it, ADD_REQUEST_CODE);
        });
    }

    private void setupNotesRecyclerView() {
        final NoteListAdapter adapter = new NoteListAdapter();

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        noteViewModel.getAllNotes().observe(this, adapter::submitList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);

        adapter.setMyItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            intent.putExtra(ID_EXTRA, note.getId());
            intent.putExtra(TITLE_EXTRA, note.getTitle());
            intent.putExtra(DESCRIPTION_EXTRA, note.getDescription());
            intent.putExtra(PRIORITY_EXTRA, note.getPriority());

            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(TITLE_EXTRA);
            String desc = data.getStringExtra(DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(PRIORITY_EXTRA, 1);
            Note note = new Note(title, desc, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved!!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNoteActivity.ID_EXTRA, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddNoteActivity.TITLE_EXTRA);
            String description = data.getStringExtra(AddNoteActivity.DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(AddNoteActivity.PRIORITY_EXTRA, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note Not Saved!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
