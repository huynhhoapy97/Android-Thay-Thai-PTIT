package truonghuynhhoa.ptit.quanlydonhang;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.KhachHangAdapter;
import truonghuynhhoa.ptit.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText edtHoTen;

    @NotEmpty
    private EditText edtDiaChi;

    @NotEmpty
    @Digits(integer = 10)
    @Length(min=10, max = 11)
    private EditText edtDienThoai;

    private EditText edtTimKiem;

    private Button btnThem, btnXoa, btnSua, btnHuy;
    private ListView lvKhachHang;
    private KhachHangAdapter khachHangAdapter;
    private List<KhachHang> khachHangList;

    private List<String> danhSachHoTen;
    private List<String> danhSachDienThoai;

    // Tên CSDL
    private String DATABASE_NAME = "quanlydonhang.sqlite";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    private Validator validator;

    private boolean isThem;
    private int maKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        addControls();
        addEvents();
    }

    private void loadDatas() {
        KhachHang khachHang = new KhachHang();

        Cursor cursor = database.rawQuery("select * from KhachHang", null);

        khachHangList.clear();

        while (cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String diaChi = cursor.getString(2);
            String dienThoai = cursor.getString(3);

            khachHang = new KhachHang(ma, ten, diaChi, dienThoai);
            khachHangList.add(khachHang);
        }

        khachHangAdapter.clear();
        khachHangAdapter.addAll(khachHangList);
        cursor.close();
    }

    private void addControls() {
        isThem = true;

        // mở CSDL, có thì mở, không có thì tạo mới CSDL không có bảng nào
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        edtHoTen = findViewById(R.id.edtHoTen);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtDienThoai = findViewById(R.id.edtDienThoai);

        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnHuy = findViewById(R.id.btnHuy);

        lvKhachHang = findViewById(R.id.lvKhachHang);

        khachHangList = new ArrayList<KhachHang>();
        khachHangAdapter = new KhachHangAdapter(KhachHangActivity.this, R.layout.item_khachhang);
        lvKhachHang.setAdapter(khachHangAdapter);

        // lấy danh sách khách hàng đẩy lên ListView
        loadDatas();

        datTrangThaiNut(true);
        datTrangThaiThongTin(new KhachHang());

        validator = new Validator(KhachHangActivity.this);
        validator.setValidationListener(KhachHangActivity.this);

        edtTimKiem = findViewById(R.id.edtTimKiem);

        danhSachHoTen = new ArrayList<String>();
        danhSachDienThoai = new ArrayList<String>();
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
            // Hiển thị hộp thoại xác nhận xóa
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KhachHangActivity.this);
                builder.setTitle("Xóa khách hàng");
                builder.setMessage("Bạn chắc chắn xóa?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện xóa khách hàng
                        kiemTraHoaDonKhachHang();
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
                datTrangThaiThongTin(new KhachHang());
                datTrangThaiLoi();
            }
        });

        lvKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhachHang khachHang = (KhachHang) parent.getItemAtPosition(position);
                maKhachHang = khachHang.getMa();

                datTrangThaiNut(false);
                datTrangThaiThongTin(khachHang);
                datTrangThaiLoi();
            }
        });

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                danhSachHoTen = layDanhSachHoTen();
                danhSachDienThoai = layDanhSachDienThoai();

                List<KhachHang> khachHangTheoHoTen = new ArrayList<KhachHang>();
                List<KhachHang> khachHangTheoDienThoai = new ArrayList<KhachHang>();

                if(s != null && s.length() > 0){
                    for(int i = 0; i < danhSachHoTen.size(); i++){
                        if(danhSachHoTen.get(i).contains(s.toString())){
                            khachHangTheoHoTen.add(khachHangList.get(i));
                        }
                    }

                    for(int i = 0; i < danhSachDienThoai.size(); i++){
                        if(danhSachDienThoai.get(i).contains(s.toString())){
                            khachHangTheoDienThoai.add(khachHangList.get(i));
                        }
                    }

                    if(khachHangTheoHoTen.size() != 0){
                        khachHangAdapter.clear();
                        khachHangAdapter.addAll(khachHangTheoHoTen);
                    }
                    else if(khachHangTheoDienThoai.size() != 0){
                        khachHangAdapter.clear();
                        khachHangAdapter.addAll(khachHangTheoDienThoai);
                    }
                    else {
                        khachHangAdapter.clear();
                        khachHangAdapter.addAll(khachHangTheoHoTen);
                    }
                }
                else {
                    khachHangTheoHoTen.clear();
                    khachHangTheoDienThoai.clear();

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

    private void datTrangThaiThongTin(KhachHang khachHang){
        if(khachHang.getMa() == null){
            edtHoTen.setText("");
            edtDiaChi.setText("");
            edtDienThoai.setText("");

            edtHoTen.requestFocus();
        }
        else{
            edtHoTen.setText(khachHang.getTen());
            edtDiaChi.setText(khachHang.getDiaChi());
            edtDienThoai.setText(khachHang.getDienThoai());

            edtHoTen.requestFocus();
        }
    }

    private void datTrangThaiLoi(){
        edtHoTen.setError(null);
        edtDiaChi.setError(null);
        edtDienThoai.setError(null);
    }

    // Kiểm tra không có lỗi
    @Override
    public void onValidationSucceeded() {
        if(isThem){

            /*String hoTen = edtHoTen.getText().toString();
            String diaChi = edtDiaChi.getText().toString();

            if(!kiemTraChuoi(hoTen)){
                edtHoTen.setError("Name is not digit");
                return;
            }

            if(!kiemTraChuoi(diaChi)){
                edtDiaChi.setError("Address is not digit");
                return;
            }*/

            themKhachHang();
        }
        else{

            /*String hoTen = edtHoTen.getText().toString();
            String diaChi = edtDiaChi.getText().toString();

            if(kiemTraChuoi(hoTen)){
                edtHoTen.setError("Name is not digit");
                return;
            }

            if(kiemTraChuoi(diaChi)){
                edtDiaChi.setError("Address is not digit");
                return;
            }*/

            suaKhachHang();
        }
    }

    // Kiểm tra có lỗi
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(KhachHangActivity.this);

            if(view instanceof EditText){
                ((EditText) view).setError(message);
            }
            else{
                Toast.makeText(KhachHangActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void themKhachHang() {

        // Lấy dữ liệu trên ô nhập
        String ten = edtHoTen.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String dienThoai = edtDienThoai.getText().toString();

        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("Ten", ten);
        row.put("DiaChi", diaChi);
        row.put("DienThoai", dienThoai);

        // result ở đây là khóa chính của element vừa thêm vào
        long result = database.insert("KhachHang", null, row);
        if(result != -1){
            Toast.makeText(KhachHangActivity.this,
                    "Thêm khách hàng thành công",
                    Toast.LENGTH_LONG).show();
        }

        // Cập nhật lại dữ liệu trên ListView
        loadDatas();

        // Cập nhật lại trạng thái cho nút nhấn và ô nhập
        datTrangThaiNut(true);
        datTrangThaiThongTin(new KhachHang());
    }

    public void suaKhachHang(){
        // Lấy dữ liệu trên ô nhập
        String ten = edtHoTen.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String dienThoai = edtDienThoai.getText().toString();

        ContentValues row = new ContentValues();
        // Sửa cột nào thì put cột đó
        row.put("Ten", ten);
        row.put("DiaChi", diaChi);
        row.put("DienThoai", dienThoai);

        // Mặc dù id ở đây là int nhưng vẫn phải để dạng chuỗi. Mọi dữ liệu khi đưa vào sqlite đều đưa về chuỗi, sau đó nó sẽ tự động chuyển thành kiểu hợp lý
        // result ở đây là số dòng chỉnh sửa được
        int result = database.update("KhachHang", row, "Ma=?", new String[]{String.valueOf(maKhachHang)});
        if(result != 0){
            Toast.makeText(KhachHangActivity.this,
                    "Cập nhật thông tin thành công",
                    Toast.LENGTH_LONG).show();
        }

        // Cập nhật lại dữ liệu trên ListView
        loadDatas();

        // Cập nhật lại trạng thái cho nút nhấn và ô nhập
        datTrangThaiNut(true);
        datTrangThaiThongTin(new KhachHang());
    }

    public void kiemTraHoaDonKhachHang(){
        Cursor cursor = database.rawQuery("select * from HoaDon where MaKH=?", new String[]{String.valueOf(maKhachHang)});

        // Không có dòng nào có nghĩa là khách hàng này chưa có hóa đơn nên có thể xóa
        if(!cursor.moveToNext()){
            int result = database.delete("KhachHang", "Ma=?", new String[]{String.valueOf(maKhachHang)});
            if(result != 0){
                Toast.makeText(KhachHangActivity.this,
                        "Xóa khách hàng thành công",
                        Toast.LENGTH_LONG).show();
            }

            // Cập nhật lại dữ liệu trên ListView
            loadDatas();

            // Cập nhật lại trạng thái cho nút nhấn và ô nhập
            datTrangThaiNut(true);
            datTrangThaiThongTin(new KhachHang());
        }
        else{
            Toast.makeText(KhachHangActivity.this,
                    "Khách hàng đã có hóa đơn, không thể xóa",
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
        AlertDialog.Builder builder = new AlertDialog.Builder(KhachHangActivity.this);
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

    public List<String> layDanhSachHoTen(){
        List<String> danhSachHoTen = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select * from KhachHang", null);

        while (cursor.moveToNext()){
            String ten = cursor.getString(1);

            danhSachHoTen.add(ten);
        }

        cursor.close();

        return danhSachHoTen;
    }

    public List<String> layDanhSachDienThoai(){
        List<String> danhSachDienThoai = new ArrayList<String>();

        Cursor cursor = database.rawQuery("select * from KhachHang", null);

        while (cursor.moveToNext()){
            String dienThoai = cursor.getString(3);

            danhSachDienThoai.add(dienThoai);
        }

        cursor.close();

        return danhSachDienThoai;
    }
}
