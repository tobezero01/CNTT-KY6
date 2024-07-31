package com.example.nhuduc_211202522;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NhuDinhDuc_Adapter extends ArrayAdapter<GiaoDich> {
    private Context context;
    private ArrayList<GiaoDich> contacts;

    public NhuDinhDuc_Adapter(Context context, ArrayList<GiaoDich> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;

        Collections.sort(contacts, new Comparator<GiaoDich>() {
            @Override
            public int compare(GiaoDich t0, GiaoDich t1) {
                int x,y;
                if(t0.isLoai() ==true) x = 1;
                else x = 0;
                if(t1.isLoai() ==true) y = 1;
                else y = 0;
                return y-x;
            }
        });

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GiaoDich giaoDich= contacts.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvTien = convertView.findViewById(R.id.tvTien);
        TextView tvNd = convertView.findViewById(R.id.tvNd);
        TextView tvdate = convertView.findViewById(R.id.tvDate);

        if (giaoDich.isLoai() ) {
            tvTen.setText("Tien den :" + giaoDich.getTen());
            //tvTen.setTextColor(Color.red());

        }else {
            tvTen.setText("Tien di :" + giaoDich.getTen());
        }

        tvTien.setText(giaoDich.getTien());
        tvNd.setText(giaoDich.getNd());
        tvdate.setText(giaoDich.getDate());

        return convertView;
    }

}
