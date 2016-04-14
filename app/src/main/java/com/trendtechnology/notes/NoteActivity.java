package com.trendtechnology.notes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.trendtechnology.notes.model.Note;
import com.trendtechnology.notes.utils.DBAdapter;

/**
 * Детальный просмотр заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 8 Apr 2016
 */
public class NoteActivity extends AppCompatActivity {

    private int noteId;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setupView();
    }

    private void setupView() {
        noteId = getIntent().getIntExtra("noteId", -1);
        db = new DBAdapter(getBaseContext());
        db.open();
        Note note = db.getNoteById(noteId);
        db.close();
        Log.d("test", note.toString());
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        binding.setVariable(com.trendtechnology.notes.BR.note, note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar != null)
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        ImageView deleteNote = (ImageView) findViewById(R.id.deleteNote);
        if (deleteNote != null)
            deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Добавить подтверждение удаления.
                    db.open();
                    db.deleteData(noteId);
                    db.close();
                    onBackPressed();
                }
            });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
                    intent.putExtra("noteId", noteId);
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
