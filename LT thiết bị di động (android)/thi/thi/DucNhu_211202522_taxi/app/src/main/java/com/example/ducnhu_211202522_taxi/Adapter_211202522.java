package com.example.ducnhu_211202522_taxi;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ducnhu_211202522_taxi.R;
import com.example.ducnhu_211202522_taxi.Taxi_211202522;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter_211202522 extends ArrayAdapter<Taxi_211202522> {

    private Context context;
    private ArrayList<Taxi_211202522> hoaDonList;

    public Adapter_211202522(Context context, ArrayList<Taxi_211202522> hoaDonList) {
        super(context, 0, hoaDonList);
        this.context = context;
        this.hoaDonList = hoaDonList;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Taxi_211202522 hoaDon = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.taxi_item, parent, false);
        }

// Lấy các view từ layout
        TextView soXe = convertView.findViewById(R.id.soXe);
        TextView quangDuong = convertView.findViewById(R.id.quangDuong);
        TextView tongTien = convertView.findViewById(R.id.tongTien);

        // Thiết lập giá trị cho các view
        if (hoaDon != null) {
            soXe.setText(hoaDon.getSoXe());
            quangDuong.setText(String.valueOf(hoaDon.getQuangDuong()));
            double totalAmount = hoaDon.getDonGia() * hoaDon.getQuangDuong() * (100 - hoaDon.getKm()) / 100.0;
            tongTien.setText(String.valueOf(totalAmount));
        }

        return convertView;
    }

    public void setList(List<Taxi_211202522> newList) {
        hoaDonList.clear();
        hoaDonList.addAll(newList);
        notifyDataSetChanged();
    }
}
