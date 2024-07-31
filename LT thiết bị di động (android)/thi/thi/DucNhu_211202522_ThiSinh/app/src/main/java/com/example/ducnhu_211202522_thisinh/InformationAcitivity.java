package com.example.ducnhu_211202522_thisinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InformationAcitivity extends AppCompatActivity {

    Button btnAdd,btnEdit, btnBack;
    EditText edtId,edtName,edtToan,edtHoa,edtLy;
    DbHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_acitivity);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        edtHoa = findViewById(R.id.edtHoa);
        edtLy = findViewById(R.id.edtLy);
        edtToan =findViewById(R.id.edtToan);
        edtName = findViewById(R.id.etName);
        edtId = findViewById(R.id.etId);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            String contactId = intent.getStringExtra("id");
            String ten = intent.getStringExtra("ten");
            double toan = intent.getDoubleExtra("toan",0);
            double ly = intent.getDoubleExtra("ly",0);
            double hoa = intent.getDoubleExtra("hoa",0);

            edtId.setText(String.valueOf(contactId));
            edtName.setText(ten);
            edtToan.setText(String.valueOf(toan));
            edtLy.setText(String.valueOf(ly));
            edtHoa.setText(String.valueOf(hoa));

            btnAdd.setEnabled(false);
            btnEdit.setEnabled(true);
        } else {
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
        }


        dbHelper = new DbHelper(this);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String name = edtName.getText().toString();
                Double toan = Double.parseDouble(edtToan.getText().toString());
                Double ly = Double.parseDouble(edtLy.getText().toString());
                Double hoa = Double.parseDouble(edtHoa.getText().toString());

                    ThiSinh contact = new ThiSinh(id, name, toan, ly,hoa);
                    dbHelper.updateContact(contact);
                    startActivity(new Intent(InformationAcitivity.this, MainActivity.class));

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(edtId.getText());
                String name = String.valueOf(edtName.getText());
                double toan = Double.parseDouble(String.valueOf(edtToan.getText()));
                double ly = Double.parseDouble(String.valueOf(edtLy.getText()));
                double hoa = Double.parseDouble(String.valueOf(edtHoa.getText()));

                if(!name.isEmpty() && !id.isEmpty()  ) {
                    ThiSinh newContact = new ThiSinh(id,name, toan,ly,hoa);
                    dbHelper.addThiSinh(newContact);
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

}