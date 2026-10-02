package com.example.ptskelompok6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pts_history.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PHONE_TYPE = "phone_type";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_FONT_NAME = "font_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_HISTORY + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_PHONE_TYPE + " TEXT, "
                + COLUMN_PHONE_NUMBER + " TEXT, "
                + COLUMN_FONT_NAME + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    public long insertHistory(String userName, String phoneType, String phoneNumber, String fontName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, userName);
        values.put(COLUMN_PHONE_TYPE, phoneType);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_FONT_NAME, fontName);
        long result = db.insert(TABLE_HISTORY, null, values);
        db.close();
        return result;
    }

    public int getHistoryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_HISTORY, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public String getAllHistoryString() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + COLUMN_ID + " ASC", null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int dataIndex = 1;

        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String phoneType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_TYPE));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            String fontName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FONT_NAME));

            if (sb.length() > 0) {
                sb.append("\n\n");
            }

            sb.append("Data ke-").append(dataIndex).append("\n")
              .append("User: ").append(userName).append("\n")
              .append("Jenis: ").append(phoneType).append("\n")
              .append("Nomor: ").append(phoneNumber);

            dataIndex++;
        }

        cursor.close();
        db.close();
        return sb.toString();
    }
}
