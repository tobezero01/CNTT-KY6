package com.example.nhudinhduc_211202522;

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
    public static final String TABLE_CONTACTS = "Contact_NhuDinhDuc";
    public static  String COLUMN_ID = "id";
    public static  String COLUMN_TEN = "ten";
    public static  String COLUMN_SDT = "soDienThoai";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TEN + " TEXT, " +
                COLUMN_SDT + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public ArrayList<Contact_NhuDinhDuc> getAllContacts() {
        ArrayList<Contact_NhuDinhDuc> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(COLUMN_TEN));
                @SuppressLint("Range") String sdt = cursor.getString(cursor.getColumnIndex(COLUMN_SDT));
                Contact_NhuDinhDuc contact = new Contact_NhuDinhDuc(id, ten, sdt);
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    // Phương thức cập nhật một contact
    public void updateContact(Contact_NhuDinhDuc contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN, contact.getTen());
        values.put(COLUMN_SDT, contact.getSoDienThoai());

        db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // lấy thông tin của 1 contact
    public Contact_NhuDinhDuc getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{COLUMN_ID, COLUMN_TEN, COLUMN_SDT},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        @SuppressLint("Range") Contact_NhuDinhDuc contact = new Contact_NhuDinhDuc(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TEN)),
                cursor.getString(cursor.getColumnIndex(COLUMN_SDT))
        );
        cursor.close();
        return contact;
    }

    // Phương thức xóa một contact
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa tất cả các dữ liệu từ bảng
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }

    public void addContacts(Contact_NhuDinhDuc contacts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TEN, contacts.getTen());
        values.put(COLUMN_SDT, contacts.getSoDienThoai());
        db.insert(TABLE_CONTACTS, null, values);

        db.close();
    }

}
