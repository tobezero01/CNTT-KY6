package com.example.nhuduc_211202522;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvDu;
    ListView lv1;
    Button btnAdd,btnReload;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    ArrayList<GiaoDich> arrayList = new ArrayList<>();
    NhuDinhDuc_Adapter adapter ;
    int soDU = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitWidgets();

        if(databaseHelper.getAll().isEmpty()) {
            addData();
        }

        arrayList.addAll(databaseHelper.getAll());
        adapter = new NhuDinhDuc_Adapter(MainActivity.this, arrayList);
        lv1.setAdapter(adapter);

        tvDu.setText("So du : " + String.valueOf(soDU));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThemMoi.class);
                startActivity(intent);
            }
        });
    }

    private void InitWidgets() {
        lv1 = findViewById(R.id.lv1);
        btnAdd = findViewById(R.id.btnAdd);
        btnReload = findViewById(R.id.btnReload);
        tvDu = findViewById(R.id.edtDu);
    }

    public void addData() {
        databaseHelper.add(new GiaoDich("Long","300 vnd","tien an" ,"20/12/2015", false));
        databaseHelper.add(new GiaoDich("Kien","3000 vnd","tien an" ,"20/12/2015", true));
        databaseHelper.add(new GiaoDich("Trung","30000 vnd","tien an" ,"20/12/2015", true));
    }
}