package com.example.tuan5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<National> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listviewbai1);
        arrayList= new ArrayList<>();
        arrayList.add (new National("Russia","1100",R.drawable.a));
       arrayList.add (new National("American","1100",R.drawable.c));
       arrayList.add(new National("Việt Nam","1100",R.drawable.b));
        NationalAdapter nationalAdapter = new NationalAdapter(MainActivity.this, R.layout.dong_national,arrayList);
        listView.setAdapter(nationalAdapter);

    }
}
