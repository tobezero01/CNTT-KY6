package com.example.nhuduc_211202522;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NhuDinhDuc_Sqlite";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Table_NhuDinhDuc";
    public static  String COLUMN_ID = "id";
    public static  String COLUMN_TEN = "ten";
    public static  String COLUMN_ND = "nd";
    public static  String COLUMN_TIEN = "tien";
    public static  String COLUMN_DATE = "date";
    public static  String COLUMN_LOAI = "loai";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TEN + " TEXT, " +
                COLUMN_ND + " TEXT, " +
                COLUMN_TIEN + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOAI + " TEXT " +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public ArrayList<GiaoDich> getAll() {
        ArrayList<GiaoDich> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(COLUMN_TEN));
                @SuppressLint("Range") String tien = cursor.getString(cursor.getColumnIndex(COLUMN_TIEN));
                @SuppressLint("Range") String nd = cursor.getString(cursor.getColumnIndex(COLUMN_ND));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String loai = cursor.getString(cursor.getColumnIndex(COLUMN_LOAI));
                GiaoDich contact = new GiaoDich(id, ten, nd,tien,date,Boolean.valueOf(loai));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add(GiaoDich contacts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TEN, contacts.getTen());
        values.put(COLUMN_TIEN, contacts.getTien());
        values.put(COLUMN_ND, contacts.getNd());
        values.put(COLUMN_DATE, contacts.getDate());
        values.put(COLUMN_LOAI, String.valueOf(contacts.isLoai()));

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
}
