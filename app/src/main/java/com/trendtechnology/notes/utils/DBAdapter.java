package com.trendtechnology.notes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trendtechnology.notes.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBAdapter {
    private static final String DB_NAME = "notes_data_base";
    private static final int DB_VERSION = 1;

    public static final String NOTES_TABLE = "notes";
    public static final String NOTE_ID = "note_id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_TEXT = "note_text";
    public static final String NOTE_CREATION_DATE = "note_creation_date";
    public static final String NOTE_CHANGE_DATE = "note_change_date";

    private static final String DB_CREATE_NOTES_TABLE = "CREATE TABLE "
            + NOTES_TABLE + "("
            + NOTE_ID + " integer primary key, "
            + NOTE_TITLE + " text not null, "
            + NOTE_TEXT + " text not null, "
            + NOTE_CREATION_DATE + " datetime default current_timestamp, "
            + NOTE_CHANGE_DATE + " datetime default current_timestamp);";

    private String noteColumns[] = {NOTE_ID, NOTE_TITLE, NOTE_TEXT,
            NOTE_CREATION_DATE, NOTE_CHANGE_DATE};

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private final Context context;

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context, DB_NAME, DB_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertData(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, note.getName());
        cv.put(NOTE_TEXT, note.getText());
        cv.put(NOTE_CREATION_DATE, formatDateTime(note.getCreationDate()));
        cv.put(NOTE_CHANGE_DATE, formatDateTime(note.getChangeDate()));

        return db.insert(NOTES_TABLE, null, cv) > 0;
    }

    public boolean deleteData(int id) {
        return db.delete(NOTES_TABLE, NOTE_ID + "=" + id, null) > 0;
    }

    public Cursor getData() {
        return db.query(NOTES_TABLE, noteColumns, null, null, null, null,
                NOTE_ID + " DESC");
    }

    public Cursor getDataById(int id) {
        Cursor cursor = db.query(NOTES_TABLE, noteColumns, NOTE_ID + "="
                + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateData(Note note, int id) {
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, note.getName());
        cv.put(NOTE_TEXT, note.getText());
        cv.put(NOTE_CHANGE_DATE, formatDateTime(note.getChangeDate()));

        return db.update(NOTES_TABLE, cv, NOTE_ID + "=" + id, null) > 0;
    }

    private String formatDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_NOTES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
            onCreate(db);
        }
    }
}
