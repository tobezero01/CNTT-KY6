package com.example.nhuduc_211202522;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ThemMoi extends AppCompatActivity {
    EditText edtDate, edtDEnDi,edtTen,edNd,edtTien;
    Button btnAdd;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_moi);
        btnAdd = findViewById(R.id.btnAdd);
        edtDate = findViewById(R.id.edtDate);
        edtDEnDi = findViewById(R.id.edtDenDi);
        edNd = findViewById(R.id.edtNd);
        edtTen = findViewById(R.id.edtTen);
        edtTien =findViewById(R.id.edtTien);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean d;
                String name = edtTen.getText().toString();
                String date = edtDate.getText().toString();
                String dendi = edtDEnDi.getText().toString();
                String nd = edNd.getText().toString();
                String tien = edtTien.getText().toString();

                if(dendi =="true") d = true;
                else d =false;

                GiaoDich newG = new GiaoDich(name,tien,nd,date,d);
                db.add(newG);
                Intent intent = new Intent(ThemMoi.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}