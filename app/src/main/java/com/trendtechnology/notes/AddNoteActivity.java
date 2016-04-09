package com.trendtechnology.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trendtechnology.notes.model.Note;
import com.trendtechnology.notes.utils.DBAdapter;

import java.util.Calendar;

/**
 * Activity для добавления новой заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class AddNoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setupView();
    }

    private void setupView() {
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        Button addNoteButton = (Button) findViewById(R.id.addNoteButton);
        if (addNoteButton != null)
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    /**
     * Сохраняет новую заметку.
     */
    protected void saveNote() {
        Calendar rightNow = Calendar.getInstance();
        Note note = new Note(
                titleEditText.getText().toString(),
                noteEditText.getText().toString(),
                rightNow.getTime(),
                rightNow.getTime()
        );
        DBAdapter db = new DBAdapter(getBaseContext());
        db.open();
        boolean success = db.insertData(note);
        if (success) {
            Toast.makeText(getBaseContext(), "Заметка добавлена",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Не удалось добавить заметку",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}
