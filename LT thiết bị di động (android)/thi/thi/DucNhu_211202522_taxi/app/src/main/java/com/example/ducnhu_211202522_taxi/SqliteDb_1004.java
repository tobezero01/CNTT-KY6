package com.example.ducnhu_211202522_taxi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteDb_1004 extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NhuDinhDuc_Sqlite";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TAXI = "Taxi_211202522";
    public static  String COLUMN_ID = "id";
    public static  String COLUMN_SOXE = "soXe";
    public static  String COLUMN_QUANGDUONG = "quangDuong";
    public static  String COLUMN_DONGIA = "donGia";
    public static  String COLUMN_KM = "km";
    public SqliteDb_1004(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TAXI +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_SOXE + " TEXT, " +
                COLUMN_QUANGDUONG + " REAL, " +
                COLUMN_DONGIA + " INTEGER, " +
                COLUMN_KM + " INTEGER " +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXI);
        onCreate(db);
    }

    // Phương thức để lấy tất cả các dòng dữ liệu trong bảng Taxi
    public List<Taxi_211202522> getAllTaxi() {
        List<Taxi_211202522> taxiList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TAXI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        /// Duyệt qua tất cả các dòng và thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                Taxi_211202522 taxi = new Taxi_211202522();
                taxi.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                taxi.setSoXe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOXE)));
                taxi.setQuangDuong(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANGDUONG)));
                taxi.setDonGia(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DONGIA)));
                taxi.setKm(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KM)));
                // Thêm taxi vào danh sách
                taxiList.add(taxi);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và trả về danh sách taxi
        cursor.close();
        return taxiList;
    }

    // Phương thức để thêm một dòng dữ liệu mới vào bảng Taxi
    public void addTaxi(Taxi_211202522 taxi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SOXE, taxi.getSoXe());
        values.put(COLUMN_QUANGDUONG, taxi.getQuangDuong());
        values.put(COLUMN_DONGIA, taxi.getDonGia());
        values.put(COLUMN_KM, taxi.getKm());

        // Chèn một dòng mới vào bảng Taxi
        db.insert(TABLE_TAXI, null, values);
        db.close(); // Đóng kết nối CSDL
    }
    public void deleteAllBills() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAXI, null, null);
        db.close();
    }
    // Thêm phương thức updateTaxi vào SqliteDb_1004

    public void updateTaxi(Taxi_211202522 taxi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOXE, taxi.getSoXe());
        values.put(COLUMN_QUANGDUONG, taxi.getQuangDuong());
        values.put(COLUMN_DONGIA, taxi.getDonGia());
        values.put(COLUMN_KM, taxi.getKm());

        // Cập nhật một dòng trong bảng Taxi
        db.update(TABLE_TAXI, values, COLUMN_ID + " = ?", new String[]{String.valueOf(taxi.getId())});
        db.close(); // Đóng kết nối CSDL
    }

}
