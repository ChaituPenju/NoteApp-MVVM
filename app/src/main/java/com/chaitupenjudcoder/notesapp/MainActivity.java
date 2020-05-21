package com.chaitupenjudcoder.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_notes);
        addNoteButton = findViewById(R.id.fab_addnote);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(it, ADD_REQUEST_CODE);
            }
        });

        final NoteAdapter adapter = new NoteAdapter();
        rv.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

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

        adapter.setMyItemClickListener(new NoteAdapter.myItemClickListener() {
            @Override
            public void myItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(ID_EXTRA, note.getId());
                intent.putExtra(TITLE_EXTRA, note.getTitle());
                intent.putExtra(DESCRIPTION_EXTRA, note.getDescription());
                intent.putExtra(PRIORITY_EXTRA, note.getPriority());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
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
