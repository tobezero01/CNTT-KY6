package net.braniumacademy;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ShowContactActivity extends AppCompatActivity {
    ListView listViewContact;
    Button buttonBack, buttonAddContact;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonAddContact = (Button) findViewById(R.id.buttonAddContact);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
        });
        showContact();
        listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(position, 0, 0, "Xoa?");
                    }
                });
            }
        });

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            String contact = adapter.getItem(item.getGroupId());
            int contactId = Integer.valueOf(contact.split("-")[0]);
            Toast.makeText(this, contact, Toast.LENGTH_SHORT).show();
            deleteContactById(contactId);
            showContact();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showContact();
    }

    private void showContact() {
        Uri uri = Uri.parse("content://contacts/people");

        listViewContact = (ListView) findViewById(R.id.listViewContact);
        ArrayList<String> list = new ArrayList<String>();
        Cursor c1 = getContentResolver().query(uri, null, null, null, null);
        c1.moveToFirst();
        while (!c1.isAfterLast()){
            String s = "";
            String idColumName = ContactsContract.Contacts._ID;
            int idindex = c1.getColumnIndex(idColumName);
            s = c1.getString(idindex) + "-";
            String nameColumName = ContactsContract.Contacts.DISPLAY_NAME;
            int nameIndex = c1.getColumnIndex(nameColumName);
            s = s + c1.getString(nameIndex);
            list.add(s);
            c1.moveToNext();
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, list);
            listViewContact.setAdapter(adapter);
        }
    }

    public void deleteContactById(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, ContactsContract.Contacts._ID + " = ?", selectionArgs, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
        }
    }
}