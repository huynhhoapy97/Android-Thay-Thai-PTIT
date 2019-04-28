package com.example.bai1tuan9;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    ArrayList<ColorObject> data= new ArrayList<>();
    ColorAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);


        Adapter = new ColorAdapter(this,R.layout.listview_item_row,data);
        lv.setAdapter(Adapter);

        new ColorAsync().execute("http://cdn.crunchify.com/wp-content/uploads/code/jsonArray.txt");
    }

    class ColorAsync extends AsyncTask<String, Integer, ColorObject> {
        @Override
        protected ColorObject doInBackground(String... strings) {
            String UrlWebservice = strings[0];// lấy địa chỉ Webservice được truyền vào
            String JsonString = new ReadJson().getJSONStringFromURL(UrlWebservice);
            ArrayList<ColorObject> datas   = new Gson().fromJson(JsonString, new TypeToken<ArrayList<ColorObject>>() {
            }.getType());

            for (int i = 0 ; i< datas.size(); i++) {
                ColorObject colorObject = datas.get(i);
                data.add(colorObject);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ColorObject colorObject) {
            super.onPostExecute(colorObject);
            Adapter.notifyDataSetChanged();
        }
    }

}
