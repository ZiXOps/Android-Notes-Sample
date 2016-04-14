package com.trendtechnology.notes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * Activity для добавления новой или редактирования старой заметки.
 *
 * @author Alexey Nesterov
 * @version 1.00 7 Apr 2016
 */
public class EditNoteActivity extends AppCompatActivity {
    protected static final int REQUEST_PICK_IMAGE = 1;
    protected static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 88;

    private boolean EditMode;
    private int noteId;
    private Note note;
    private DBAdapter db;

    ViewDataBinding binding;

    private TextInputLayout titleEditText;
    private TextInputLayout noteEditText;
    private ImageView uploadImage;
    private ImageView uploadedImage;
    private String uploadedImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
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
            binding.executePendingBindings();
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
        uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
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

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            // Сохраняет изображение в ExternalStorageDirectory.
            new saveImageTask().execute(data.getData());
        }
    }

    /**
     * Сохраняет изображение, выбранное пользователем.
     * Пока изображение обрабатывается, блокируется кнопка добавления заметки.
     */
    private class saveImageTask extends AsyncTask<Uri, Integer, String> {

        protected String doInBackground(Uri... urls) {
            String imageName = "";
            Uri fileUri = urls[0];
            Bitmap bmp = null;
            try {
                InputStream stream = null;
                stream = getContentResolver().openInputStream(fileUri);
                bmp = BitmapFactory.decodeStream(stream);
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bmp != null) {
                imageName = StoreImageUtils.storeImage(getBaseContext(), bmp);
            }
            return imageName;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Bitmap bitmap = StoreImageUtils.loadBitmap(getBaseContext(), result);
            if (bitmap != null) {
                uploadedImage.setImageBitmap(bitmap);
            }
            Log.i("onPostExecute", result);
            uploadedImageName = result;
            binding.executePendingBindings();
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.i("onProgressUpdate", "progress");
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
        if (uploadedImageName != null && !uploadedImageName.equals("")) {
            note.setImageName(uploadedImageName);
        }
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
        if (uploadedImageName != null && !uploadedImageName.equals("")) {
            note.setImageName(uploadedImageName);
        }
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
