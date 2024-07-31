package com.example.ducnhu_211202522_thisinh;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ThiSinh.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ThiSinh";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HOTEN = "hoTen";
    public static final String COLUMN_TOAN = "diemToan";
    public static final String COLUMN_LY = "diemLy";
    public static final String COLUMN_HOA = "diemHoa";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_HOTEN + " TEXT, " +
                COLUMN_TOAN + " REAL, " +
                COLUMN_LY + " REAL, " +
                COLUMN_HOA + " REAL)";
        db.execSQL(createTable);
    }
    // Phương thức cập nhật một contact
    public void updateContact(ThiSinh contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, contact.getHoTen());

        values.put(COLUMN_TOAN, contact.getDiemToan());
        values.put(COLUMN_LY, contact.getDiemLy());
        values.put(COLUMN_HOA, contact.getDiemHoa());

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public ArrayList<ThiSinh> getAllContacts() {
        ArrayList<ThiSinh> tss = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEN));
                @SuppressLint("Range") double toan = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOAN));
                @SuppressLint("Range") double ly = cursor.getDouble(cursor.getColumnIndex(COLUMN_LY));
                @SuppressLint("Range") double hoa = cursor.getDouble(cursor.getColumnIndex(COLUMN_HOA));

                ThiSinh ts = new ThiSinh(id, ten, toan,ly,hoa);
                tss.add(ts);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tss;
    }
    // lấy thông tin của 1 contact
    public ThiSinh getThiSinh(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_HOTEN, COLUMN_TOAN, COLUMN_LY, COLUMN_HOA},
                COLUMN_ID + "=?", new String[]{id}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") String thiSinhId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String hoTen = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEN));
            @SuppressLint("Range") double diemToan = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOAN));
            @SuppressLint("Range") double diemLy = cursor.getDouble(cursor.getColumnIndex(COLUMN_LY));
            @SuppressLint("Range") double diemHoa = cursor.getDouble(cursor.getColumnIndex(COLUMN_HOA));
            cursor.close();
            return new ThiSinh(thiSinhId, hoTen, diemToan, diemLy, diemHoa);
        }
        return null;
    }

    public void deleteThiSinh(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa tất cả các dữ liệu từ bảng
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void addThiSinh(ThiSinh ts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, ts.getId());
        values.put(COLUMN_HOTEN, ts.getHoTen());
        values.put(COLUMN_TOAN, ts.getDiemToan());
        values.put(COLUMN_LY, ts.getDiemLy());
        values.put(COLUMN_HOA, ts.getDiemHoa());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
