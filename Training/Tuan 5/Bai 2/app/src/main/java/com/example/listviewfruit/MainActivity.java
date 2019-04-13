package com.example.listviewfruit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv_fruit;
    ArrayList<Fruit> arrayFruit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_fruit = findViewById(R.id.lv_fruit);
        arrayFruit = new ArrayList<Fruit>();
        arrayFruit.add(new Fruit("Dâu Tây", "Dâu Tây là một loại trái cây được ưa thích khi đến Đà Lạt",R.drawable.dautay));
        arrayFruit.add(new Fruit("Chuối","Là một loại quả mà các chị em phụ nữ rất thích vì ăn đẹp da",R.drawable.chuoi));
        arrayFruit.add(new Fruit("Cam","Là một loại quả chua chua dùng làm nước uống bổ lắm ^_^",R.drawable.cam));
        arrayFruit.add(new Fruit("Táo", "Là một loại quả mà ngày xưa Adam đưa cho Eva ăn",R.drawable.tao));

        FruitAdapter fruitAdapter = new FruitAdapter(
                MainActivity.this,
                R.layout.fruit_row,
                arrayFruit
        );
        lv_fruit.setAdapter(fruitAdapter);
        lv_fruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Bạn vừa click vào " + arrayFruit.get(position).Ten,Toast.LENGTH_LONG).show();
            }
        });
    }
}
