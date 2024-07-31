package com.example.ducnhu_211202522_taxi;

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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {


    ListView lv1;

    Adapter_211202522 adapter ;

    Button btnReload;
    ArrayList<Taxi_211202522> list = new ArrayList<>();

    private static final int REQUEST_CODE_EDIT = 1;
    SqliteDb_1004 db = new SqliteDb_1004(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = findViewById(R.id.lv1);
        btnReload = findViewById(R.id.btnReload);

        addSampleData();
        adapter = new Adapter_211202522(this, new ArrayList<>(list));
        lv1.setAdapter(adapter);

        // Đăng ký ContextMenu cho ListView
        registerForContextMenu(lv1);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void addSampleData() {
        db.deleteAllBills();
        db.addTaxi(new Taxi_211202522(1, "ABC123", 10.5, 15000, 10));
        db.addTaxi(new Taxi_211202522(2, "XYZ456", 20.0, 12000, 5));
        db.addTaxi(new Taxi_211202522(3, "LMN789", 15.2, 13000, 8));
        db.addTaxi(new Taxi_211202522(4, "PQR123", 18.3, 11000, 15));
        db.addTaxi(new Taxi_211202522(5, "DEF456", 25.0, 10000, 20));
        db.addTaxi(new Taxi_211202522(6, "GHI789", 12.1, 14000, 12));

        list.addAll(db.getAllTaxi());

        Collections.sort(list, new Comparator<Taxi_211202522>() {
            @Override
            public int compare(Taxi_211202522 o1, Taxi_211202522 o2) {
                return o1.getSoXe().compareTo(o2.getSoXe());
            }
        });

    }


    // khởi tạo context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lv1) {
            menu.setHeaderTitle("Select Action");
            menu.add(0, v.getId(), 0, "edit_211202522");
            menu.add(0, v.getId(), 1, "delete_211202522");
        }
    }
    private void refreshData() {
        list.clear();
        list.addAll(db.getAllTaxi());
        Collections.sort(list, new Comparator<Taxi_211202522>() {
            @Override
            public int compare(Taxi_211202522 o1, Taxi_211202522 o2) {
                return o1.getSoXe().compareTo(o2.getSoXe());
            }
        });
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }
    // click contextMenu edit vs delete
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Taxi_211202522 selectedTaxi = (Taxi_211202522) lv1.getItemAtPosition(info.position);


        if (item.getTitle() == "delete_211202522") {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to delete this item?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //db.deleteTaxi(selectedTaxi.getId());
                            refreshData();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else if (item.getTitle() == "edit_211202522") {
            Intent intent = new Intent(MainActivity.this, EditTaxiActivity.class);
            intent.putExtra("id", selectedTaxi.getId());
            intent.putExtra("soXe", selectedTaxi.getSoXe());
            intent.putExtra("quangDuong", selectedTaxi.getQuangDuong());
            intent.putExtra("donGia", selectedTaxi.getDonGia());
            intent.putExtra("km", selectedTaxi.getKm());
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            refreshData();
        }
    }

}