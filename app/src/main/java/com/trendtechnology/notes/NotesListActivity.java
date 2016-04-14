package com.trendtechnology.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trendtechnology.notes.model.Note;
import com.trendtechnology.notes.noteList.NoteItemCallbacks;
import com.trendtechnology.notes.noteList.NotesListAdapter;
import com.trendtechnology.notes.utils.DBAdapter;
import com.trendtechnology.notes.utils.DateUtils;
import com.trendtechnology.notes.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity просмотра списка заметок.
 *
 * @author Alexey Nesterov
 * @version 1.00 8 Apr 2016
 */
public class NotesListActivity extends AppCompatActivity implements NoteItemCallbacks {

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        db = new DBAdapter(getBaseContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notesList);
        NotesListAdapter notesListAdapter = new NotesListAdapter(getNotesFromBase());
        if (recyclerView != null) {
            notesListAdapter.setNoteItemCallbacks(this);
            recyclerView.setAdapter(notesListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NotesListActivity.this, EditNoteActivity.class);
                    startActivity(intent);
                }
            });
    }

    /**
     * Заполняет список заметок данными из базы.
     *
     * @return список заметок.
     */
    private List<Note> getNotesFromBase() {
        List<Note> noteList = new ArrayList<>();
        db.open();
        Cursor cursor = db.getData();
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    Note note = new Note();
                    note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_ID)));
                    note.setTitile(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_TITLE)));
                    note.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_TEXT)));
                    note.setCreationDate(DateUtils.parseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_CREATION_DATE))));
                    note.setChangeDate(DateUtils.parseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_CHANGE_DATE))));
                    note.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_IMAGE_URI)));
                    noteList.add(note);
                    cursor.moveToNext();
                }
                db.close();
            }
        }
        return noteList;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            db.open();
            db.deleteAllNotes();
            db.close();
            setupView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteItemSelected(int noteId) {
        Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
        intent.putExtra("noteId", noteId);
        startActivity(intent);
    }

}
