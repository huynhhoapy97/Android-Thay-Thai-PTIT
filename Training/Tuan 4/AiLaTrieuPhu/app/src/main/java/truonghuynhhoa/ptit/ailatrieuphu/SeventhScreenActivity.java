package truonghuynhhoa.ptit.ailatrieuphu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SeventhScreenActivity extends AppCompatActivity {

    ListView listResult;
    ArrayAdapter<String> arrayAdapter;
    Integer [] dapandung = new Integer[]{1, 2, 2, 1,1};
    String [] cauhoi = new  String[]{"Câu hỏi 1: " + dapandung[0] + "câu đúng",
            "Câu hỏi 2: " + dapandung[1] + "câu đúng", "Câu hỏi 3: " + dapandung[2] + "câu đúng",
            "Câu hỏi 4: " + dapandung[3] + "câu đúng" ,"Câu hỏi 5: " + dapandung[4] + "câu đúng"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        listResult = findViewById(R.id.listResult); // lay ra

        //Khoi tao arrraylist adapter
        arrayAdapter = new ArrayAdapter<String>(
                //Man hinh hien tai dang dung de xuat.
                SeventhScreenActivity.this,
                android.R.layout.simple_list_item_1, cauhoi
        );
        // Lien ket arraylist toi list view, tuc la tu arraylist thong qua thang adater toi dc listview
        listResult.setAdapter(arrayAdapter);

    }
}
