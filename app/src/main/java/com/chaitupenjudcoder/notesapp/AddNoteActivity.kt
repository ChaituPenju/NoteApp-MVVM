package com.chaitupenjudcoder.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chaitupenjudcoder.notesapp.databinding.ActivityAddNoteBinding
import com.chaitupenjudcoder.notesapp.models.Note

class AddNoteActivity: AppCompatActivity() {

    companion object {
        public const val ID_EXTRA = "id"
        public const val TITLE_EXTRA = "title"
        public const val DESCRIPTION_EXTRA = "description"
        public const val PRIORITY_EXTRA = "priority"
    }

    private lateinit var addNoteBinding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(ID_EXTRA)) {
            title = "Edit Note"
            val note = Note(
                intent.getStringExtra(TITLE_EXTRA),
                intent.getStringExtra(DESCRIPTION_EXTRA),
                intent.getIntExtra(PRIORITY_EXTRA, 7)
            )

            addNoteBinding.executePendingBindings()

            addNoteBinding.note = note
        } else {
            title = "Add Note"
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
        val title1 = addNoteBinding.etTitle.text.toString()
        val desc = addNoteBinding.etDescription.text.toString()
        val priority1 = addNoteBinding.npPriority.value

        if (title1.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Title and Description", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.putExtra(TITLE_EXTRA, title1)
        intent.putExtra(DESCRIPTION_EXTRA, desc)
        intent.putExtra(PRIORITY_EXTRA, priority1)

        val id = getIntent().getIntExtra(ID_EXTRA, -1)
        if (id != -1) {
            intent.putExtra(ID_EXTRA, id)
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}
