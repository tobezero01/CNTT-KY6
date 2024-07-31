package com.example.ducnhu_211202522_hotel;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter_211202522 extends ArrayAdapter<HoaDon_1004> {

    private Context context;
    private ArrayList<HoaDon_1004> hoaDonList;

    public Adapter_211202522(Context context, ArrayList<HoaDon_1004> hoaDonList) {
        super(context, 0, hoaDonList);
        this.context = context;
        this.hoaDonList = hoaDonList;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoaDon_1004 hoaDon = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bill_item, parent, false);
        }

        TextView txtCustomerName = convertView.findViewById(R.id.txtCustomerName);
        TextView txtRoomNumber = convertView.findViewById(R.id.txtRoomNumber);
        TextView txtTotalPrice = convertView.findViewById(R.id.txtTotalPrice);

        txtCustomerName.setText(hoaDon.getCustomerName());
        txtRoomNumber.setText(String.valueOf(hoaDon.getRoomNumber()));
        txtTotalPrice.setText(String.valueOf(hoaDon.getUnitPrice()*hoaDon.getNumberOfDays()));

        return convertView;
    }

    public void setList(List<HoaDon_1004> newList) {
        hoaDonList.clear();
        hoaDonList.addAll(newList);
        notifyDataSetChanged();
    }
}
