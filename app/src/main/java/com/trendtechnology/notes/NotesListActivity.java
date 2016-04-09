package com.trendtechnology.notes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trendtechnology.notes.model.Note;
import com.trendtechnology.notes.utils.DBAdapter;
import com.trendtechnology.notes.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Activity просмотра списка заметок.
 *
 * @author Alexey Nesterov
 * @version 1.00 8 Apr 2016
 */
public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notesList);
        NotesListAdapter notesListAdapter = new NotesListAdapter(getNotesFromBase());
        if (recyclerView != null) {
            recyclerView.setAdapter(notesListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
    }

    /**
     * Заполняет список заметок данными из базы.
     *
     * @return - список заметок.
     */
    private List<Note> getNotesFromBase() {
        List<Note> noteList = new ArrayList<>();
        DBAdapter db = new DBAdapter(getBaseContext());
        db.open();
        Cursor cursor = db.getData();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Note note = new Note(
                        cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_TEXT)),
                        new Date(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_CREATION_DATE)),
                        new Date(cursor.getColumnIndexOrThrow(DBAdapter.NOTE_CHANGE_DATE))
                );
                Log.d("test", note.toString());

                noteList.add(note);
                cursor.moveToNext();
            }
            db.close();
        }
        return noteList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
