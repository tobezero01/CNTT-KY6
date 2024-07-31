package com.example.ducnhu_211202522_thisinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThiSinh_Adapter extends ArrayAdapter<ThiSinh> {

    private Context context;
    private ArrayList<ThiSinh> contacts;

    public ThiSinh_Adapter(Context context, ArrayList<ThiSinh> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThiSinh contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_thisinh, parent, false);
        }

        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvName = convertView.findViewById(R.id.tvTen);
        TextView tvDiem = convertView.findViewById(R.id.tvDiem);

        tvId.setText(String.valueOf(contact.getId()));
        tvName.setText(contact.getHoTen());
        tvDiem.setText(String.valueOf(contact.tongDiem()));

        return convertView;
    }
}