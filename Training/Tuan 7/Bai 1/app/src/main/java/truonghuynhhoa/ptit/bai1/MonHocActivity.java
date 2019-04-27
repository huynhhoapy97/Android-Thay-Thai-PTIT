package truonghuynhhoa.ptit.bai1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.MonHocAdapter;
import truonghuynhhoa.ptit.model.MonHoc;

public class MonHocActivity extends AppCompatActivity {

    // Tên CSDL
    private String DATABASE_NAME = "quanlymonhoc.sqlite";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    EditText edtMa, edtTen, edtSoTiet;
    Button btnThem, btnTai;
    ListView lvMonHoc;
    MonHocAdapter monHocAdapter;
    List<MonHoc> monHocList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_hoc);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themMonHoc();
            }
        });

         btnTai.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 taiDanhSachMonHoc();
             }
         });
    }

    private void taiDanhSachMonHoc() {
        MonHoc monHoc = new MonHoc();

        Cursor cursor = database.rawQuery("select * from MonHoc", null);

        monHocList.clear();

        while (cursor.moveToNext()){
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            int soTiet = cursor.getInt(2);

            monHoc = new MonHoc(ma, ten, soTiet);
            monHocList.add(monHoc);
        }

        monHocAdapter.clear();
        monHocAdapter.addAll(monHocList);
        cursor.close();
    }

    private void themMonHoc() {
        String ma = edtMa.getText().toString();
        String ten = edtTen.getText().toString();
        int soTiet = Integer.valueOf(edtSoTiet.getText().toString());

        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("Ma", ma);
        row.put("Ten", ten);
        row.put("SoTiet", soTiet);

        // result ở đây là khóa chính của element vừa thêm vào
        long result = database.insert("MonHoc", null, row);
        if(result != -1){
            Toast.makeText(MonHocActivity.this,
                    "Thêm môn học thành công",
                    Toast.LENGTH_LONG).show();

            edtMa.setText("");
            edtTen.setText("");
            edtSoTiet.setText("");
        }
    }

    private void addControls() {
        // mở CSDL, có thì mở, không có thì tạo mới CSDL không có bảng nào
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        edtMa = findViewById(R.id.edtMa);
        edtTen = findViewById(R.id.edtTen);
        edtSoTiet = findViewById(R.id.edtSoTiet);

        btnThem = findViewById(R.id.btnThem);
        btnTai = findViewById(R.id.btnTai);

        lvMonHoc = findViewById(R.id.lvMonHoc);
        monHocAdapter = new MonHocAdapter(MonHocActivity.this, R.layout.item_mon_hoc);
        lvMonHoc.setAdapter(monHocAdapter);

        monHocList = new ArrayList<MonHoc>();
    }
}
