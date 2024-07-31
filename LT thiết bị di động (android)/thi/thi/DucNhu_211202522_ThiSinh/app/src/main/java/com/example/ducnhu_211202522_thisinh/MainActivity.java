package com.example.ducnhu_211202522_thisinh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<ThiSinh> arrayList = new ArrayList<>();
    Button btnAdd;
    Button btnReload;
    ThiSinh_Adapter adapter;
    DbHelper dbHelper = new DbHelper(this);
    ListView lv1;String selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = findViewById(R.id.lv1);
        btnAdd = findViewById(R.id.btnAdd);
        btnReload = findViewById(R.id.btnReload);

        if (dbHelper.getAllContacts().isEmpty()) {
            addData();
        }

        arrayList.addAll(dbHelper.getAllContacts());

        Collections.sort(arrayList, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                return (int) o1.tongDiem() - (int) o2.tongDiem();
            }
        });
        adapter = new ThiSinh_Adapter(MainActivity.this, arrayList);
        lv1.setAdapter(adapter);
        registerForContextMenu(lv1);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InformationAcitivity.class);
                startActivity(intent);
            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedId = arrayList.get(i).getId();
                return false;
            }
        });

    }

    public void addData() {
        dbHelper.addThiSinh(new ThiSinh("DH345","Trung", 7,8,9));
        dbHelper.addThiSinh(new ThiSinh("DH346","Tung", 10,8,9));
        dbHelper.addThiSinh(new ThiSinh("DH347","Truong", 7,9,9));
        dbHelper.addThiSinh(new ThiSinh("DH348","Duc", 7,5,9));
        dbHelper.addThiSinh(new ThiSinh("DH349","Kien", 8,8,9));
    }

    private void refreshData() {
        arrayList.clear();
        arrayList.addAll(dbHelper.getAllContacts());
        Collections.sort(arrayList, new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh o1, ThiSinh o2) {
                return (int) o1.tongDiem() - (int) o2.tongDiem();
            }
        });
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }

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
                            dbHelper.deleteThiSinh(selectedId);
                            refreshData();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else if (item.getTitle() == "Duc_Edit") {
            ThiSinh contact = dbHelper.getThiSinh(selectedId);
            Intent intent = new Intent(MainActivity.this, InformationAcitivity.class);
            intent.putExtra("id", contact.getId());
            intent.putExtra("ten", contact.getHoTen());
            intent.putExtra("toan", contact.getDiemToan());
            intent.putExtra("ly", contact.getDiemLy());
            intent.putExtra("hoa", contact.getDiemHoa());

            //intent.putExtra("soDienThoai", contact.getSoDienThoai());
            //intent.putExtra("isEdit", 1);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu, View v, Menu menuInfo) {
//        super.onCreateOptionsMenu(menu, v, menuInfo);
//        if (v.getId() == R.id.lv1) {
//
//            menu.add(0, v.getId(), 0, "Duc_Edit");
//            menu.add(0, v.getId(), 1, "Duc_Delete");
//        }
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sort_total_asc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>() {
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return Double.compare(ts1.getDiemToan() + ts1.getDiemLy() + ts1.getDiemHoa(),
//                            ts2.getDiemToan() + ts2.getDiemLy() + ts2.getDiemHoa());
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            case R.id.sort_total_desc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>() {
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return Double.compare(ts2.getDiemToan() + ts2.getDiemLy() + ts2.getDiemHoa(),
//                            ts1.getDiemToan() + ts1.getDiemLy() + ts1.getDiemHoa());
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            case R.id.sort_id_asc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>() {
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return ts1.getId().compareTo(ts2.getId());
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            case R.id.sort_id_desc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>(){
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return ts2.getId().compareTo(ts1.getId());
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            case R.id.sort_avg_asc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>() {
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return Double.compare((ts1.getDiemToan() + ts1.getDiemLy() + ts1.getDiemHoa()) / 3,
//                            (ts2.getDiemToan() + ts2.getDiemLy() + ts2.getDiemHoa()) / 3);
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            case R.id.sort_avg_desc:
//                Collections.sort(arrayList, new Comparator<ThiSinh>() {
//                @Override
//                public int compare(ThiSinh ts1, ThiSinh ts2) {
//                    return Double.compare((ts2.getDiemToan() + ts2.getDiemLy() + ts2.getDiemHoa()) / 3,
//                            (ts1.getDiemToan() + ts1.getDiemLy() + ts1.getDiemHoa()) / 3);
//                }
//            });
//            adapter.notifyDataSetChanged();
//            return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

}