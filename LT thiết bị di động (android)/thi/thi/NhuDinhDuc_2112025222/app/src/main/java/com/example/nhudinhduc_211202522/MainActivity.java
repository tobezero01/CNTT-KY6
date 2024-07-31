package com.example.nhudinhduc_211202522;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

    ListView lv1;
    EditText edtSearch;
    Button btnAdd, btnReload;
    NhuDinhDuc_Adapter adapter;
    ArrayList<Contact_NhuDinhDuc> contacts = new ArrayList<>();
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitWidgets();

        if(dbHelper.getAllContacts().isEmpty()) {
            addData();
        }
        contacts.addAll(dbHelper.getAllContacts());
        adapter = new NhuDinhDuc_Adapter(MainActivity.this, contacts);
        lv1.setAdapter(adapter);
        // Register context menu for the ListView
        registerForContextMenu(lv1);

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedId = contacts.get(i).getId();
                return false;
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addData() {
        dbHelper.addContacts(new Contact_NhuDinhDuc("Trung", "3364564564"));
        dbHelper.addContacts(new Contact_NhuDinhDuc("Duc11", "3346465456"));
        dbHelper.addContacts(new Contact_NhuDinhDuc("Hoan sech", "3456456456"));
        dbHelper.addContacts(new Contact_NhuDinhDuc("Duc Nhu", "4564564646"));
        dbHelper.addContacts(new Contact_NhuDinhDuc("Tung", "37564745666"));
        dbHelper.addContacts(new Contact_NhuDinhDuc("Kien", "35675675686"));
    }

    private void refreshData() {
        contacts.clear();
        contacts.addAll(dbHelper.getAllContacts());
        // Sắp xếp danh sách theo tên tăng dần
        Collections.sort(this.contacts, new Comparator<Contact_NhuDinhDuc>() {
            @Override
            public int compare(Contact_NhuDinhDuc o1, Contact_NhuDinhDuc o2) {
                return o1.getTen().compareToIgnoreCase(o2.getTen());
            }
        });
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }

    private void InitWidgets() {
        lv1 = findViewById(R.id.lv1);
        edtSearch = findViewById(R.id.edtSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnReload = findViewById(R.id.btnReload);
    }

    // khởi tạo context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lv1) {
            menu.setHeaderTitle("Select Action");
            menu.add(0, v.getId(), 0, "Duc_Edit");
            menu.add(0, v.getId(), 1, "Duc_Delete");
        }
    }

    // click contextMenu edit vs delete
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Duc_Delete") {
            new AlertDialog.Builder(this)
                    .setMessage("Duc wants to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbHelper.deleteContact(selectedId);
                            refreshData();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else if (item.getTitle() == "Duc_Edit") {
            Contact_NhuDinhDuc contact = dbHelper.getContact(selectedId);
            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
            intent.putExtra("id", contact.getId());
            intent.putExtra("ten", contact.getTen());
            intent.putExtra("soDienThoai", contact.getSoDienThoai());
            //intent.putExtra("isEdit", 1);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }


}