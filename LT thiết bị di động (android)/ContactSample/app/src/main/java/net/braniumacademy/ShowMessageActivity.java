package net.braniumacademy;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ShowMessageActivity extends AppCompatActivity {
    ListView listViewMessage;
    Button buttonBack1;
    ArrayList<String> messageList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        buttonBack1 = (Button) findViewById(R.id.buttonBack1);
        buttonBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listViewMessage = (ListView) findViewById(R.id.listViewMessage);
        messageList = new ArrayList<String>();
        readMessage();
    }

    private void readMessage() {
        Uri uri = Uri.parse("content://sms");
        Cursor c2 = getContentResolver().query(uri, null, null, null, null);
        while (c2.moveToNext()) {
            int index_phonenumber = c2.getColumnIndex("address");
            int index_date = c2.getColumnIndex("date");
            int index_body = c2.getColumnIndex("body");
            String phonenumber = c2.getString(index_phonenumber);
            String date_ = c2.getString(index_date);
            String body_ = c2.getString(index_body);
            messageList.add(phonenumber + "\n" + date_ + "\n" + body_);
        }
        c2.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, messageList);
        listViewMessage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}