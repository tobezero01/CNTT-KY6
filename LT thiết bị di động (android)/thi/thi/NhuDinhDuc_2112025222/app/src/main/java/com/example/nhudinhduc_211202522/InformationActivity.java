package com.example.nhudinhduc_211202522;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InformationActivity extends AppCompatActivity {
    EditText edtId, edtName, edtPhone;
    Button btnAdd, btnBack,btnEdit;
    DatabaseHelper dbHelper;
    int contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initWidgets();

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            contactId = intent.getIntExtra("id", -1);
            String ten = intent.getStringExtra("ten");
            String soDienThoai = intent.getStringExtra("soDienThoai");

            edtId.setText(String.valueOf(contactId));
            edtName.setText(ten);
            edtPhone.setText(soDienThoai);

            btnAdd.setEnabled(false);
            btnEdit.setEnabled(true);
        } else {
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String sdt = edtPhone.getText().toString();

                if(!name.isEmpty() && !sdt.isEmpty() ) {
                    Contact_NhuDinhDuc newContact = new Contact_NhuDinhDuc(name, sdt);
                    dbHelper.addContacts(newContact);
                    finish();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty() && contactId != -1) {
                    Contact_NhuDinhDuc contact = new Contact_NhuDinhDuc(contactId, name, phone);
                    dbHelper.updateContact(contact);
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initWidgets() {
        // Ánh xạ các view từ layout
        edtId = findViewById(R.id.etId);
        edtName = findViewById(R.id.etName);
        edtPhone = findViewById(R.id.etPhone);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
    }
}