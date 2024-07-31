package net.braniumacademy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddContactActivity extends AppCompatActivity {
    private Button buttonAdd;
    private Button buttonCancel;
    private EditText etContactName;
    private EditText etContactPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        etContactName = (EditText) findViewById(R.id.etContactName);
        etContactPhone = (EditText) findViewById(R.id.etContactPhone);

        buttonCancel.setOnClickListener(v -> finish());
        buttonAdd.setOnClickListener(v -> {
            String contactName = etContactName.getText().toString();
            String contactPhone = etContactPhone.getText().toString();
            createContact(contactName, contactPhone);
            finish();
        });
    }

    private void createContact(String contactName, String contactPhone) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactPhone);
        startActivity(intent);
    }
}