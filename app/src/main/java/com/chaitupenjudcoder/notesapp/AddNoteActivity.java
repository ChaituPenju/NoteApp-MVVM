package com.chaitupenjudcoder.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.chaitupenjudcoder.notesapp.databinding.ActivityAddNoteBinding;
import com.chaitupenjudcoder.notesapp.models.Note;

public class AddNoteActivity extends AppCompatActivity {

    public static final String ID_EXTRA = "id";
    public static final String TITLE_EXTRA = "title";
    public static final String DESCRIPTION_EXTRA = "description";
    public static final String PRIORITY_EXTRA = "priority";

    private ActivityAddNoteBinding addNoteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(ID_EXTRA)) {
            setTitle("Edit Note");
            Note note = new Note(
                    intent.getStringExtra(TITLE_EXTRA),
                    intent.getStringExtra(DESCRIPTION_EXTRA),
                    intent.getIntExtra(PRIORITY_EXTRA, 7)
            );
            addNoteBinding.executePendingBindings();

            addNoteBinding.setNote(note);
        } else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveItem();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveItem() {
        String title1 = addNoteBinding.etTitle.getText().toString();
        String desc = addNoteBinding.etDescription.getText().toString();
        int priority1 = addNoteBinding.npPriority.getValue();

        if (title1.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(TITLE_EXTRA, title1);
        intent.putExtra(DESCRIPTION_EXTRA, desc);
        intent.putExtra(PRIORITY_EXTRA, priority1);

        int id = getIntent().getIntExtra(ID_EXTRA, -1);
        if (id != -1) {
            intent.putExtra(ID_EXTRA, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
