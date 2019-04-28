package truonghuynhhoa.ptit.quanlydonhang;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.MatHangAdapter;
import truonghuynhhoa.ptit.model.MatHang;

public class MatHangActivity extends AppCompatActivity implements Validator.ValidationListener{
    @NotEmpty
    private EditText edtMaHang;

    @NotEmpty
    private EditText edtTenHang;

    @NotEmpty
    private EditText edtDonViTinh;

    @NotEmpty
    @Digits(integer = 9)
    @Min(value = 1000)
    private EditText edtDonGia;

    private EditText edtTimKiem;

    private Button btnThem, btnXoa, btnSua, btnHuy;
    private ListView lvMatHang;
    private MatHangAdapter matHangAdapter;
    private List<MatHang> matHangList;

    private List<String> danhSachMaHang;
    private List<String> danhSachTenHang;
    private List<String> danhSachDonGia;

    private Validator validator;

    private boolean isThem;

    // Tên CSDL
    private String DATABASE_NAME = "quanlydonhang.sqlite";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathang);
        addControls();
        addEvents();
    }

    private void loadDatas() {
        MatHang matHang = new MatHang();

        Cursor cursor = database.rawQuery("select * from MatHang", null);

        matHangList.clear();

        while (cursor.moveToNext()){
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String donVi = cursor.getString(2);
            String donGia = cursor.getString(3);

            matHang = new MatHang(ma, ten, donVi, donGia);
            matHangList.add(matHang);
        }

        matHangAdapter.clear();
        matHangAdapter.addAll(matHangList);
        cursor.close();
    }

    private void addControls() {
        isThem = true;

        // mở CSDL, có thì mở, không có thì tạo mới CSDL không có bảng nào
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        edtMaHang = findViewById(R.id.edtMaHang);
        edtTenHang = findViewById(R.id.edtTenHang);
        edtDonViTinh = findViewById(R.id.edtDonViTinh);
        edtDonGia = findViewById(R.id.edtDonGia);

        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnHuy = findViewById(R.id.btnHuy);

        lvMatHang = findViewById(R.id.lvMatHang);

        matHangList = new ArrayList<MatHang>();
        matHangAdapter = new MatHangAdapter(MatHangActivity.this, R.layout.item_mathang);
        lvMatHang.setAdapter(matHangAdapter);

        loadDatas();

        validator = new Validator(MatHangActivity.this);
        validator.setValidationListener(MatHangActivity.this);

        edtTimKiem = findViewById(R.id.edtTimKiem);

        danhSachMaHang = new ArrayList<String>();
        danhSachTenHang = new ArrayList<String>();
        danhSachDonGia = new ArrayList<String>();
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThem = true;

                // Kiểm tra validate
                validator.validate();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MatHangActivity.this);
                builder.setTitle("Xóa khách hàng");
                builder.setMessage("Bạn chắc chắn xóa?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String maHang = edtMaHang.getText().toString();

                        // Thực hiện xóa khách hàng
                        kiemTraChiTietHoaDonMatHang(maHang);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ẩn hộp thoại đi
                        dialog.dismiss();
                    }
                });

                // Tạo hộp thoại
                AlertDialog dialog = builder.create();
                // Không đóng hộp thoại khi nhấn ở ngoài
                dialog.setCanceledOnTouchOutside(false);
                // Hiển thị hộp thoại lên
                dialog.show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isThem = false;

                // Kiểm tra validate
                validator.validate();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datTrangThaiNut(true);
                datTrangThaiThongTin(new MatHang());
                datTrangThaiLoi();
            }
        });

        lvMatHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatHang matHang = (MatHang) parent.getItemAtPosition(position);

                datTrangThaiNut(false);
                datTrangThaiThongTin(matHang);
                datTrangThaiLoi();
            }
        });

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                danhSachMaHang = layDanhSachMaHang();
                danhSachTenHang = layDanhSachTenHang();
                danhSachDonGia = layDanhSachDonGia();

                List<MatHang> matHangTheoMaHang = new ArrayList<MatHang>();
                List<MatHang> matHangTheoTenHang = new ArrayList<MatHang>();
                List<MatHang> matHangTheoDonGia = new ArrayList<MatHang>();

                if(s != null && s.length() > 0){
                    for(int i = 0; i < danhSachMaHang.size(); i++){
                        if(danhSachMaHang.get(i).contains(s.toString())){
                            matHangTheoMaHang.add(matHangList.get(i));
                        }
                    }

                    for(int i = 0; i < danhSachTenHang.size(); i++){
                        if(danhSachTenHang.get(i).contains(s.toString())){
                            matHangTheoTenHang.add(matHangList.get(i));
                        }
                    }

                    for(int i = 0; i < danhSachDonGia.size(); i++){
                        if(danhSachDonGia.get(i).contains(s.toString())){
                            matHangTheoDonGia.add(matHangList.get(i));
                        }
                    }

                    if(matHangTheoMaHang.size() != 0){
                        matHangAdapter.clear();
                        matHangAdapter.addAll(matHangTheoMaHang);
                    }
                    else if(matHangTheoTenHang.size() != 0){
                        matHangAdapter.clear();
                        matHangAdapter.addAll(matHangTheoTenHang);
                    }
                    else if(matHangTheoDonGia.size() != 0){
                        matHangAdapter.clear();
                        matHangAdapter.addAll(matHangTheoDonGia);
                    }
                    else {
                        matHangAdapter.clear();
                        matHangAdapter.addAll(matHangTheoMaHang);
                    }
                }
                else {
                    matHangTheoMaHang.clear();
                    matHangTheoTenHang.clear();
                    matHangTheoDonGia.clear();

                    loadDatas();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void datTrangThaiNut(boolean trangThai){
        if(trangThai){
            btnThem.setEnabled(true);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
            btnHuy.setEnabled(false);
        }
        else{
            btnThem.setEnabled(false);
            btnXoa.setEnabled(true);
            btnSua.setEnabled(true);
            btnHuy.setEnabled(true);
        }
    }

    private void datTrangThaiThongTin(MatHang matHang){
        if(matHang.getMa() == null){
            edtMaHang.setText("");
            edtMaHang.setEnabled(true);

            edtTenHang.setText("");
            edtDonViTinh.setText("");
            edtDonGia.setText("");

            edtMaHang.requestFocus();
        }
        else{
            edtMaHang.setText(matHang.getMa());
            edtMaHang.setEnabled(false);

            edtTenHang.setText(matHang.getTen());
            edtDonViTinh.setText(matHang.getDonVi());
            edtDonGia.setText(matHang.getDonGia());

            edtMaHang.requestFocus();
        }
    }

    private void datTrangThaiLoi(){
        edtMaHang.setError(null);
        edtTenHang.setError(null);
        edtDonViTinh.setError(null);
        edtDonGia.setError(null);
    }

    @Override
    public void onValidationSucceeded() {
        if(isThem){

            /*String tenHang = edtTenHang.getText().toString();
            String donViTinh = edtDonViTinh.getText().toString();

            if(kiemTraChuoi(tenHang)){
                edtTenHang.setError("Name is not digit");
                return;
            }

            if(kiemTraChuoi(donViTinh)){
                edtDonViTinh.setError("Unit brief is not digit");
                return;
            }*/

            themMatHang();
        }
        else{

            /*String tenHang = edtTenHang.getText().toString();
            String donViTinh = edtDonViTinh.getText().toString();

            if(kiemTraChuoi(tenHang)){
                edtTenHang.setError("Name is not digit");
                return;
            }

            if(kiemTraChuoi(donViTinh)){
                edtDonViTinh.setError("Unit brief is not digit");
                return;
            }*/

            suaMatHang();
        }
    }

    public void themMatHang() {

        // Lấy dữ liệu trên ô nhập
        String ma = edtMaHang.getText().toString();
        String ten = edtTenHang.getText().toString();
        String donVi = edtDonViTinh.getText().toString();
        String donGia = edtDonGia.getText().toString();

        Cursor cursor = database.rawQuery("select * from MatHang where Ma=?", new String[]{ma});

        // Đã tồn tại mặt hàng với mã hàng này
        if (cursor.moveToNext()){
            Toast.makeText(MatHangActivity.this,
                    "Mã hàng đã tồn tại",
                    Toast.LENGTH_LONG).show();

            edtMaHang.requestFocus();

            return;
        }

        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("Ma", ma);
        row.put("Ten", ten);
        row.put("DonVi", donVi);
        row.put("DonGia", donGia);

        // result ở đây là khóa chính của element vừa thêm vào
        long result = database.insert("MatHang", null, row);
        if(result != -1){
            Toast.makeText(MatHangActivity.this,
                    "Thêm mặt hàng thành công",
                    Toast.LENGTH_LONG).show();
        }

        // Cập nhật lại dữ liệu trên ListView
        loadDatas();

        // Cập nhật lại trạng thái cho nút nhấn và ô nhập
        datTrangThaiNut(true);
        datTrangThaiThongTin(new MatHang());
    }

    public void suaMatHang() {

        // Lấy dữ liệu trên ô nhập
        String ma = edtMaHang.getText().toString();
        String ten = edtTenHang.getText().toString();
        String donVi = edtDonViTinh.getText().toString();
        String donGia = edtDonGia.getText().toString();

        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("Ten", ten);
        row.put("DonVi", donVi);
        row.put("DonGia", donGia);

        // result ở đây là khóa chính của element vừa thêm vào
        long result = database.update("MatHang", row, "Ma=?", new String[]{String.valueOf(ma)});
        if(result != -1){
            Toast.makeText(MatHangActivity.this,
                    "Cập nhật thông tin mặt hàng thành công",
                    Toast.LENGTH_LONG).show();
        }

        // Cập nhật lại dữ liệu trên ListView
        loadDatas();

        // Cập nhật lại trạng thái cho nút nhấn và ô nhập
        datTrangThaiNut(true);
        datTrangThaiThongTin(new MatHang());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(MatHangActivity.this);

            if(view instanceof EditText){
                ((EditText) view).setError(message);
            }
            else{
                Toast.makeText(MatHangActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void kiemTraChiTietHoaDonMatHang(String maHang){
        Cursor cursor = database.rawQuery("select * from ChiTietHoaDon where MaHang=?", new String[]{String.valueOf(maHang)});

        // Không có dòng nào có nghĩa là mặt hàng này chưa có hóa đơn nên có thể xóa
        if(!cursor.moveToNext()){
            int result = database.delete("MatHang", "Ma=?", new String[]{String.valueOf(maHang)});
            if(result != 0){
                Toast.makeText(MatHangActivity.this,
                        "Xóa mặt hàng thành công",
                        Toast.LENGTH_LONG).show();
            }

            // Cập nhật lại dữ liệu trên ListView
            loadDatas();

            // Cập nhật lại trạng thái cho nút nhấn và ô nhập
            datTrangThaiNut(true);
            datTrangThaiThongTin(new MatHang());
        }
        else{
            Toast.makeText(MatHangActivity.this,
                    "Mặt hàng đã có trong chi tiết hóa đơn, không thể xóa",
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean kiemTraChuoi(String giaTri){
        boolean check = true;

        if(giaTri.matches("[0-9]")){
            check = false;
        }

        return check;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MatHangActivity.this);
        builder.setTitle("Thoát");
        builder.setMessage("Bạn chắc chắn thoát?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ẩn hộp thoại đi
                dialog.dismiss();
            }
        });

        // Tạo hộp thoại
        AlertDialog dialog = builder.create();
        // Không đóng hộp thoại khi nhấn ở ngoài
        dialog.setCanceledOnTouchOutside(false);
        // Hiển thị hộp thoại lên
        dialog.show();
    }

    public List<String> layDanhSachMaHang(){
        List<String> danhSachMaHang = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select * from MatHang", null);

        while (cursor.moveToNext()){
            String ma = cursor.getString(0);

            danhSachMaHang.add(ma);
        }

        cursor.close();

        return danhSachMaHang;
    }

    public List<String> layDanhSachTenHang(){
        List<String> danhSachTenHang = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select * from MatHang", null);

        while (cursor.moveToNext()){
            String ten = cursor.getString(1);

            danhSachTenHang.add(ten);
        }

        cursor.close();

        return danhSachTenHang;
    }

    public List<String> layDanhSachDonGia(){
        List<String> danhSachDonGia = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select * from MatHang", null);

        while (cursor.moveToNext()){
            String donGia = cursor.getString(3);

            danhSachDonGia.add(donGia);
        }

        cursor.close();

        return danhSachDonGia;
    }
}
