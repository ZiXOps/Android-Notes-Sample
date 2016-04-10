package com.trendtechnology.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Детальный просмотр заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 8 Apr 2016
 */
public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

}
