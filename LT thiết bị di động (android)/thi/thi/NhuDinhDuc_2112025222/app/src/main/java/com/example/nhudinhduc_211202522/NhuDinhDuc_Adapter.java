package com.example.nhudinhduc_211202522;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//public class NhuDinhDuc_Adapter extends BaseAdapter {
//
//    private Context context;
//    private ArrayList<Contact_NhuDinhDuc> contacts;
//
//    public NhuDinhDuc_Adapter(Context context, ArrayList<Contact_NhuDinhDuc> contacts) {
//        this.context = context;
//        this.contacts = contacts;
//        Collections.sort(this.contacts, new Comparator<Contact_NhuDinhDuc>() {
//            @Override
//            public int compare(Contact_NhuDinhDuc t0, Contact_NhuDinhDuc t1) {
//                return t0.getTen().compareToIgnoreCase(t1.getTen());
//            }
//        });
//    }
//
//    @Override
//    public int getCount() {
//        return contacts.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return contacts.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        view = inflater.inflate(R.layout.item_contact, viewGroup, false);
//
//        TextView tvId = view.findViewById(R.id.tvSTT);
//        TextView tvName = view.findViewById(R.id.tvName);
//        TextView tvPhone = view.findViewById(R.id.tvPhone);
//
//        Contact_NhuDinhDuc contact = (Contact_NhuDinhDuc) getItem(i);
//        tvId.setText(String.valueOf(i+1));
//        tvName.setText(contact.getTen());
//        tvPhone.setText(contact.getSoDienThoai());
//
//        return view;
//    }
//
//}
public class NhuDinhDuc_Adapter extends ArrayAdapter<Contact_NhuDinhDuc> {

    private Context context;
    private ArrayList<Contact_NhuDinhDuc> contacts;

    public NhuDinhDuc_Adapter(Context context, ArrayList<Contact_NhuDinhDuc> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;

        // Sắp xếp danh sách theo tên tăng dần
        Collections.sort(this.contacts, new Comparator<Contact_NhuDinhDuc>() {
            @Override
            public int compare(Contact_NhuDinhDuc o1, Contact_NhuDinhDuc o2) {
                return o1.getTen().compareToIgnoreCase(o2.getTen());
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact_NhuDinhDuc contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }

        TextView tvId = convertView.findViewById(R.id.tvSTT);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);

        tvId.setText(String.valueOf(position + 1));
        tvName.setText(contact.getTen());
        tvPhone.setText(contact.getSoDienThoai());

        return convertView;
    }
}
