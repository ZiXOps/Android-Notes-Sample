package com.trendtechnology.notes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.trendtechnology.notes.model.Note;
import com.trendtechnology.notes.utils.DBAdapter;

import java.util.Date;

/**
 * Activity для добавления новой заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class AddNoteActivity extends AppCompatActivity {

    protected static final int REQUEST_PICK_IMAGE = 1;

    private TextInputLayout titleEditText;
    private TextInputLayout noteEditText;
    private ImageView uploadImage;
    private ImageView uploadedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setupView();
    }

    private void setupView() {
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

        titleEditText = (TextInputLayout) findViewById(R.id.titleLabel);
        noteEditText = (TextInputLayout) findViewById(R.id.noteLabel);
        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        Button addNoteButton = (Button) findViewById(R.id.addNoteButton);
        if (addNoteButton != null)
            addNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNote();
                }
            });

        if (uploadImage != null) {
            uploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_PICK_IMAGE);
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE) {
            Uri selectedImage = data.getData();
            Log.d("test", selectedImage.toString());
            uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
            uploadedImage.setImageURI(selectedImage);
        }
    }

    /**
     * Сохраняет новую заметку.
     */
    protected void saveNote() {
        Note note = new Note();
        note.setTitile(titleEditText.getEditText().getText().toString());
        note.setText(noteEditText.getEditText().getText().toString());
        note.setCreationDate(new Date());
        note.setChangeDate(new Date());
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
