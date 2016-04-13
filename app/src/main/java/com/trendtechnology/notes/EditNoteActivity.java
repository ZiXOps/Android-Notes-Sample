package com.trendtechnology.notes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.BitmapFactory;
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
import com.trendtechnology.notes.utils.StoreImageUtils;

import java.util.Date;

/**
 * Activity для добавления новой или редактирования старой заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class EditNoteActivity extends AppCompatActivity {
    protected static final int REQUEST_PICK_IMAGE = 1;

    private boolean EditMode;
    private int noteId;
    private Note note;
    private DBAdapter db;

    ViewDataBinding binding;

    private TextInputLayout titleEditText;
    private TextInputLayout noteEditText;
    private ImageView uploadImage;
    private ImageView uploadedImage;

    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note);
        setupView();
    }

    private void setupView() {
        noteId = getIntent().getIntExtra("noteId", -1);
        EditMode = noteId >= 0;

        db = new DBAdapter(getBaseContext());
        if (EditMode) {
            db.open();
            note = db.getNoteById(noteId);
            db.close();
            Log.d("test", note.toString());
            binding.setVariable(com.trendtechnology.notes.BR.note, note);
        }

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
                    if (EditMode) {
                        editNote();
                    } else {
                        saveNote();
                    }
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
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE) {
            selectedImage = data.getData();
            Log.d("test", selectedImage.getPath());
            uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
            if (uploadedImage != null)
                uploadedImage.setImageURI(selectedImage);
                StoreImageUtils.saveFile(getBaseContext(), BitmapFactory.decodeFile(selectedImage.toString()), "testImg");
                uploadedImage.getContext();
        }
    }

    /**
     * Сохраняет новую заметку.
     */
    private void saveNote() {
        Note note = new Note();
        note.setTitile(titleEditText.getEditText().getText().toString());
        note.setText(noteEditText.getEditText().getText().toString());
        note.setCreationDate(new Date());
        note.setChangeDate(new Date());
        note.setImageUri(selectedImage);
        db.open();
        boolean success = db.insertData(note);
        if (success) {
            Toast.makeText(getBaseContext(), "Заметка добавлена",
                    Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Не удалось добавить заметку",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void editNote() {
        note.setTitile(titleEditText.getEditText().getText().toString());
        note.setText(noteEditText.getEditText().getText().toString());
        note.setChangeDate(new Date());
        //note.setImageUri(selectedImage);
        db.open();
        boolean success = db.updateData(note, noteId);
        if (success) {
            Toast.makeText(getBaseContext(), "Заметка обновлена",
                    Toast.LENGTH_LONG).show();
            onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Не удалось обновить заметку",
                    Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}
