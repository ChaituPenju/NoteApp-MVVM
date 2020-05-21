package com.chaitupenjudcoder.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    public static final String ID_EXTRA = "id";
    public static final String TITLE_EXTRA = "title";
    public static final String DESCRIPTION_EXTRA = "description";
    public static final String PRIORITY_EXTRA = "priority";

    EditText title, description;
    NumberPicker priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.et_title);
        description = findViewById(R.id.et_description);
        priority = findViewById(R.id.np_priority);

        priority.setMinValue(1);
        priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(ID_EXTRA)) {
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(TITLE_EXTRA));
            description.setText(intent.getStringExtra(DESCRIPTION_EXTRA));
            priority.setValue(intent.getIntExtra(PRIORITY_EXTRA, 1));
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
        String title1 = title.getText().toString();
        String desc = description.getText().toString();
        int priority1 = priority.getValue();

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
