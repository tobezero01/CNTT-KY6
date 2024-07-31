package com.example.ducnhu_211202522_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ArrayList<HoaDon_1004> hoaDonList = new ArrayList<>();
    private ListView listView;
    Adapter_211202522 adapter;
    EditText edtSearch;
    Button btnReload;

    Sqlite_211202522 sql = new Sqlite_211202522(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khởi tạo danh sách hóa đơn và thêm dữ liệu mẫu
        InitWidgets();
        initData();
        // Khởi tạo và set Adapter cho ListView
        adapter = new Adapter_211202522(MainActivity.this, hoaDonList);
        listView.setAdapter(adapter);

        // Thiết lập sự kiện lắng nghe cho EditText
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Lấy số tiền từ EditText và chuyển đổi thành kiểu số
                    String searchAmountString = edtSearch.getText().toString();
                    if (!searchAmountString.isEmpty()) {
                        double searchAmount = Double.parseDouble(searchAmountString);

                        // Lọc danh sách các hóa đơn có tổng tiền lớn hơn hoặc bằng số tiền nhập vào
                        List<HoaDon_1004> filteredList = new ArrayList<>();
                        for (HoaDon_1004 bill : hoaDonList) {
                            if (bill.getNumberOfDays() * bill.getUnitPrice() >= searchAmount) {
                                filteredList.add(bill);
                            }
                        }

                        // Cập nhật ListView
                        adapter.setList(filteredList);
                        adapter.notifyDataSetChanged();
                    }
                    return true;
                }
                return false;
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy hóa đơn được chọn từ danh sách
                HoaDon_1004 selectedBill = hoaDonList.get(position);
                String studentName = selectedBill.getCustomerName();
// Đếm số hóa đơn có số tiền lớn hơn số tiền của hóa đơn được chọn
                double selectedBillAmount = selectedBill.getNumberOfDays() * selectedBill.getUnitPrice();
                int count = 0;
                for (HoaDon_1004 bill : hoaDonList) {
                    if (bill.getNumberOfDays() * bill.getUnitPrice() > selectedBillAmount) {
                        count++;
                    }
                }
                Toast.makeText(MainActivity.this, "Họ tên sinh viên: " + studentName +"\n"
                        +"Số hóa đơn có số tiền lớn hơn: " + count, Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    public void initData() {
        sql.deleteAllBills();

        sql.addHotelBill(new HoaDon_1004("John Doe", 101, 50.0, 3));
        sql.addHotelBill(new HoaDon_1004("Alice Smith", 109, 60.0, 2));
        sql.addHotelBill(new HoaDon_1004("Bob Johnson", 103, 70.0, 4));
        sql.addHotelBill(new HoaDon_1004("Emily Brown", 104, 80.0, 5));
        sql.addHotelBill(new HoaDon_1004("Michael Wilson", 105, 90.0, 6));
        sql.addHotelBill(new HoaDon_1004("Duc Nhu", 106, 100.0, 7));

        hoaDonList.addAll(sql.getAllHotelBills());

        Collections.sort(hoaDonList, new Comparator<HoaDon_1004>() {
            @Override
            public int compare(HoaDon_1004 bill1, HoaDon_1004 bill2) {
                // So sánh số phòng của hai hóa đơn
                return bill2.getRoomNumber() - bill1.getRoomNumber();
            }
        });
    }
    public void InitWidgets() {

        listView = findViewById(R.id.lv1);
        edtSearch = findViewById(R.id.edtSearch);
        btnReload = findViewById(R.id.btnReload);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}