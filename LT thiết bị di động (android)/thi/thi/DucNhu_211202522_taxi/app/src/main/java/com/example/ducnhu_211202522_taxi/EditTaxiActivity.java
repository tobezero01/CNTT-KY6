package com.example.ducnhu_211202522_taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaxiActivity extends AppCompatActivity {

    private EditText editTextSoXe, editTextQuangDuong, editTextDonGia, editTextKm;
    private Button buttonSave;
    private SqliteDb_1004 db;
    private int taxiId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_taxi);

        editTextSoXe = findViewById(R.id.editTextSoXe);
        editTextQuangDuong = findViewById(R.id.editTextQuangDuong);
        editTextDonGia = findViewById(R.id.editTextDonGia);
        editTextKm = findViewById(R.id.editTextKm);
        buttonSave = findViewById(R.id.buttonSave);

        db = new SqliteDb_1004(this);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        taxiId = intent.getIntExtra("id", -1);
        String soXe = intent.getStringExtra("soXe");
        double quangDuong = intent.getDoubleExtra("quangDuong", 0);
        int donGia = intent.getIntExtra("donGia", 0);
        int km = intent.getIntExtra("km", 0);

        // Điền dữ liệu vào các trường
        editTextSoXe.setText(soXe);
        editTextQuangDuong.setText(String.valueOf(quangDuong));
        editTextDonGia.setText(String.valueOf(donGia));
        editTextKm.setText(String.valueOf(km));

        // Lưu thay đổi khi nhấn nút Save
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSoXe = editTextSoXe.getText().toString();
                double newQuangDuong = Double.parseDouble(editTextQuangDuong.getText().toString());
                int newDonGia = Integer.parseInt(editTextDonGia.getText().toString());
                int newKm = Integer.parseInt(editTextKm.getText().toString());

                db.updateTaxi(new Taxi_211202522( newSoXe, newQuangDuong, newDonGia, newKm));

                setResult(RESULT_OK); // Set the result code to indicate success
                finish(); // Close the activity
            }
        });
    }
}
