package truonghuynhhoa.ptit.bai1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.NhanVienAdapter;
import truonghuynhhoa.ptit.model.NhanVien;

public class MainActivity extends AppCompatActivity {

    private Button btnNhapNV;
    private ImageView imgXoa;
    private EditText edtMaNV, edtTenNV;
    private RadioButton radNu, radNam;

    private ListView lvDanhSachNhanVien;

    private List<NhanVien> danhSachNhanVien;
    private NhanVienAdapter nhanVienAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnNhapNV = findViewById(R.id.btnNhapNV);
        imgXoa = findViewById(R.id.imgXoa);
        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV = findViewById(R.id.edtTenNV);
        radNu = findViewById(R.id.radNu);
        radNam = findViewById(R.id.radNam);

        nhanVienAdapter = new NhanVienAdapter(MainActivity.this, R.layout.item_nhanvien);

        danhSachNhanVien = new ArrayList<NhanVien>();

        lvDanhSachNhanVien = findViewById(R.id.lvDanhSachNhanVien);
        lvDanhSachNhanVien.setAdapter(nhanVienAdapter);
    }

    private void addEvents() {
        btnNhapNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMaNV.getText().toString();
                String ten = edtTenNV.getText().toString();
                Boolean gioiTinh = false;
                if(radNu.isChecked()){
                    gioiTinh = true;
                }
                if(radNam.isChecked()){
                    gioiTinh = false;
                }

                NhanVien nhanVien = new NhanVien(ma, ten, gioiTinh);
                danhSachNhanVien.add(nhanVien);

                nhanVienAdapter.clear();
                nhanVienAdapter.addAll(danhSachNhanVien);

                edtMaNV.setText("");
                edtTenNV.setText("");
                radNu.setChecked(false);
                radNam.setChecked(false);
                edtMaNV.setFocusable(true);
            }
        });

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = lvDanhSachNhanVien.getChildCount()-1; i >= 0; i--)
                {
                    v = lvDanhSachNhanVien.getChildAt(i);
                    //Ta chỉ lấy CheckBox ra kiểm tra
                    CheckBox chkXoa=(CheckBox) v.findViewById(R.id.chkXoa);
                    //Nếu nó Checked thì xóa ra khỏi arrEmployee
                    if(chkXoa.isChecked())
                    {
                        //xóa phần tử thứ i ra khỏi danh sách
                        danhSachNhanVien.remove(i);
                    }
                }
                //Sau khi xóa xong thì gọi update giao diện
                nhanVienAdapter.clear();
                nhanVienAdapter.addAll(danhSachNhanVien);
            }
        });
    }
}
