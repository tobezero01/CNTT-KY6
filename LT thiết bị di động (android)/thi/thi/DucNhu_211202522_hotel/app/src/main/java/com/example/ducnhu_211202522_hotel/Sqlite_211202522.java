package com.example.ducnhu_211202522_hotel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Sqlite_211202522 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hotel_bills.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HOTEL_BILLS = "hotel_bills";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_ROOM_NUMBER = "room_number";
    private static final String COLUMN_UNIT_PRICE = "unit_price";
    private static final String COLUMN_NUMBER_OF_DAYS = "number_of_days";

    // Constructor
    public Sqlite_211202522(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate method to create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HOTEL_BILLS_TABLE = "CREATE TABLE " + TABLE_HOTEL_BILLS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CUSTOMER_NAME + " TEXT," +
                COLUMN_ROOM_NUMBER + " INTEGER," +
                COLUMN_UNIT_PRICE + " REAL," +
                COLUMN_NUMBER_OF_DAYS + " INTEGER" +
                ")";
        db.execSQL(CREATE_HOTEL_BILLS_TABLE);
    }

    // onUpgrade method (not implemented in this example)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement if needed
    }

    // Method to add a new hotel bill to the database
    public void addHotelBill(HoaDon_1004 bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, bill.getCustomerName());
        values.put(COLUMN_ROOM_NUMBER, bill.getRoomNumber());
        values.put(COLUMN_UNIT_PRICE, bill.getUnitPrice());
        values.put(COLUMN_NUMBER_OF_DAYS, bill.getNumberOfDays());
        db.insert(TABLE_HOTEL_BILLS, null, values);
        db.close();
    }

    // Method to retrieve all hotel bills from the database
    public List<HoaDon_1004> getAllHotelBills() {
        List<HoaDon_1004> billList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HOTEL_BILLS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") HoaDon_1004 bill = new HoaDon_1004(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_NUMBER)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_UNIT_PRICE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_OF_DAYS))
                );
                billList.add(bill);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return billList;
    }

    public void deleteAllBills() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOTEL_BILLS, null, null);
        db.close();
    }

    public void editBill(int billId, HoaDon_1004 newBill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, newBill.getCustomerName());
        values.put(COLUMN_ROOM_NUMBER, newBill.getRoomNumber());
        values.put(COLUMN_UNIT_PRICE, newBill.getUnitPrice());
        values.put(COLUMN_NUMBER_OF_DAYS, newBill.getNumberOfDays());
        db.update(TABLE_HOTEL_BILLS, values, COLUMN_ID + "=?", new String[]{String.valueOf(billId)});
        db.close();
    }

    public void deleteBill(int billId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOTEL_BILLS, COLUMN_ID + "=?", new String[]{String.valueOf(billId)});
        db.close();
    }
}
