package com.trendtechnology.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trendtechnology.notes.model.Note;

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
        Note note = new Note();
        note.setName(titleEditText.getText().toString());
        note.setText(noteEditText.getText().toString());
        Calendar rightNow = Calendar.getInstance();
        note.setCreationDate(rightNow.getTime());
        note.setChangeDate(rightNow.getTime());
    }

}
