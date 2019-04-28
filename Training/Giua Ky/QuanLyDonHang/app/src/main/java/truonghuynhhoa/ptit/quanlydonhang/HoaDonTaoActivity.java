package truonghuynhhoa.ptit.quanlydonhang;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import truonghuynhhoa.ptit.adapter.HangHoaDonAdapter;
import truonghuynhhoa.ptit.adapter.MaKhachHangAdapter;
import truonghuynhhoa.ptit.adapter.MaMatHangAdapter;
import truonghuynhhoa.ptit.model.HangHoaDon;
import truonghuynhhoa.ptit.model.KhachHang;
import truonghuynhhoa.ptit.model.MatHang;

public class HoaDonTaoActivity extends AppCompatActivity {

    private Spinner spinnerKhachHang, spinnerMatHang;
    private MaKhachHangAdapter adapterMaKhachHang;
    private MaMatHangAdapter adapterMaMatHang;

    private TextView txtHoTen, txtDienThoai, txtDiaChi, txtNgayLap, txtNgayGiao;
    private ImageView imgNgayLap, imgNgayGiao, imgXacNhan, imgHuy;

    private ListView lvHangHoaDon;
    private HangHoaDonAdapter hangHoaDonAdapter;
    private List<HangHoaDon> hangHoaDonList;

    // Tên CSDL
    private String DATABASE_NAME = "quanlydonhang.sqlite";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    private List<KhachHang> khachHangList;
    private List<MatHang> matHangList;

    private Calendar calendarNgayLap, calendarNgayGiao;
    private SimpleDateFormat formatNgayLap, formatNgayGiao;

    private int soThuTu, soLuong;
    private int maKhachHang;
    private String maHang;

    public List<HangHoaDon> getHangHoaDonList() {
        return hangHoaDonList;
    }

    public void setHangHoaDonList(List<HangHoaDon> hangHoaDonList) {
        this.hangHoaDonList = hangHoaDonList;
    }

    public int getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(int soThuTu) {
        this.soThuTu = soThuTu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_tao);
        addControls();
        addEvents();
    }

    private void addControls() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        khachHangList = new ArrayList<KhachHang>();
        matHangList = new ArrayList<MatHang>();

        spinnerKhachHang = findViewById(R.id.spinnerKhachHang);
        adapterMaKhachHang = new MaKhachHangAdapter(HoaDonTaoActivity.this,
                R.layout.item_khach_hang_lua_chon, R.layout.item_khachhang){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0){
                    return false;
                }
                else{
                    return true;
                }
            }
        };

        adapterMaKhachHang.add(new KhachHang());

        loadDuLieuKhachHang();
        for(KhachHang khachHang : khachHangList){
            adapterMaKhachHang.add(khachHang);
        }

        //adapterMaKhachHang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerKhachHang.setAdapter(adapterMaKhachHang);

        spinnerMatHang = findViewById(R.id.spinnerMatHang);
        adapterMaMatHang = new MaMatHangAdapter(HoaDonTaoActivity.this,
                R.layout.item_mat_hang_lua_chon, R.layout.item_mathang){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0){
                    return false;
                }
                else{
                    return true;
                }
            }
        };

        adapterMaMatHang.add(new MatHang());

        loadDuLieuMatHang();

        for(MatHang matHang : matHangList){
            adapterMaMatHang.add(matHang);
        }

        adapterMaMatHang.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        spinnerMatHang.setAdapter(adapterMaMatHang);

        txtHoTen = findViewById(R.id.txtHoTen);
        txtDienThoai = findViewById(R.id.txtDienThoai);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtNgayLap = findViewById(R.id.txtNgayLap);
        txtNgayGiao = findViewById(R.id.txtNgayGiao);

        imgNgayLap = findViewById(R.id.imgNgayLap);
        imgNgayGiao = findViewById(R.id.imgNgayGiao);

        calendarNgayLap = Calendar.getInstance();
        formatNgayLap = new SimpleDateFormat("dd/MM/yyyy");
        calendarNgayGiao = Calendar.getInstance();
        formatNgayGiao = new SimpleDateFormat("dd/MM/yyyy");

        imgXacNhan = findViewById(R.id.imgXacNhan);
        imgHuy = findViewById(R.id.imgHuy);

        soThuTu = 0;
        soLuong = 0;

        lvHangHoaDon = findViewById(R.id.lvHangHoaDon);
        hangHoaDonAdapter = new HangHoaDonAdapter(HoaDonTaoActivity.this, R.layout.item_hoa_don_tao);
        lvHangHoaDon.setAdapter(hangHoaDonAdapter);

        hangHoaDonList = new ArrayList<HangHoaDon>();

        maKhachHang = 0;
        maHang = "";
    }

    private void addEvents() {
        spinnerKhachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    maKhachHang = Integer.valueOf(adapterMaKhachHang.getItem(position).getMa());

                    KhachHang khachHang = getKhachHangTheoMa(maKhachHang);
                    txtHoTen.setText(khachHang.getTen());
                    txtDienThoai.setText(khachHang.getDienThoai());
                    txtDiaChi.setText(khachHang.getDiaChi());

                    spinnerKhachHang.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMatHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    maHang = adapterMaMatHang.getItem(position).getMa();

                    for(HangHoaDon hangHoaDon : hangHoaDonList){
                        if(maHang.equalsIgnoreCase(hangHoaDon.getMaHang())){
                            Toast.makeText(HoaDonTaoActivity.this,
                                    "Đã tồn tại mặt hàng này trong hóa đơn",
                                    Toast.LENGTH_LONG).show();

                            spinnerMatHang.setSelection(0);

                            return;
                        }
                    }

                    MatHang matHang = getMatHangTheoMa(maHang);

                    soThuTu++;
                    soLuong = 1;

                    HangHoaDon hangHoaDon = new HangHoaDon(soThuTu, matHang.getMa(), matHang.getTen(), soLuong);
                    hangHoaDonList.add(hangHoaDon);

                    hangHoaDonAdapter.clear();
                    hangHoaDonAdapter.addAll(hangHoaDonList);

                    spinnerMatHang.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgNgayLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgayLap();
            }
        });

        imgNgayGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgayGiao();
            }
        });

        imgXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtHoTen.getText().toString().equals("")){
                    Toast.makeText(HoaDonTaoActivity.this, "Vui lòng chọn thông tin khách hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(txtNgayLap.getText().toString().equals("")){
                    Toast.makeText(HoaDonTaoActivity.this, "Vui lòng chọn ngày lập hóa đơn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(txtNgayGiao.getText().toString().equals("")){
                    Toast.makeText(HoaDonTaoActivity.this, "Vui lòng chọn ngày giao hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(hangHoaDonList.size() == 0){
                    Toast.makeText(HoaDonTaoActivity.this, "Hóa đơn chưa có mặt hàng\nVui lòng chọn ít nhất một mặt hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Date ngayLap = formatNgayLap.parse(txtNgayLap.getText().toString());
                    Date ngayGiao = formatNgayGiao.parse(txtNgayGiao.getText().toString());

                    // Ngày lập hóa đơn sau ngày giao hàng
                    if(ngayLap.compareTo(ngayGiao) > 0){
                        Toast.makeText(HoaDonTaoActivity.this, "Ngày lập hóa đơn không thể sau ngày giao hàng\nVui lòng kiểm tra lại thời gian", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(HoaDonTaoActivity.this, "Số lượng: " + hangHoaDonList.size() + " STT: " + soThuTu, Toast.LENGTH_LONG).show();

                int soHoaDon = getTongSoHoaDon() + 1;
                String ngayLap = txtNgayLap.getText().toString();
                String ngayGiao = txtNgayGiao.getText().toString();

                boolean ketQua = lapHoaDon(soHoaDon, ngayLap, ngayGiao, maKhachHang);

                if(ketQua){

                    for(HangHoaDon hangHoaDon : hangHoaDonList){
                        String maHang = hangHoaDon.getMaHang();
                        int soLuong = hangHoaDon.getSoLuong();

                        lapChiTietHoaDon(soHoaDon, maHang, soLuong);
                    }

                    Toast.makeText(HoaDonTaoActivity.this,
                            "Lập hóa đơn thành công",
                            Toast.LENGTH_LONG).show();

                    lamMoiHoaDon();
                }
            }
        });

        imgHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonTaoActivity.this);
                builder.setTitle("Hủy hóa đơn");
                builder.setMessage("Bạn chắc chắn hủy hóa đơn?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lamMoiHoaDon();
                    }
                });
                builder.setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
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
    }

    public void chonNgayLap() {
        // Lắng nghe thông tin người dùng thay đổi trên giao diện
                DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarNgayLap.set(Calendar.YEAR, year);
                calendarNgayLap.set(Calendar.MONTH, month);
                calendarNgayLap.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtNgayLap.setText(formatNgayLap.format(calendarNgayLap.getTime()));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HoaDonTaoActivity.this,
                callBack,
                calendarNgayLap.get(Calendar.YEAR),
                calendarNgayLap.get(Calendar.MONTH),
                calendarNgayLap.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void chonNgayGiao() {
        // Lắng nghe thông tin người dùng thay đổi trên giao diện
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarNgayGiao.set(Calendar.YEAR, year);
                calendarNgayGiao.set(Calendar.MONTH, month);
                calendarNgayGiao.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtNgayGiao.setText(formatNgayGiao.format(calendarNgayGiao.getTime()));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HoaDonTaoActivity.this,
                callBack,
                calendarNgayGiao.get(Calendar.YEAR),
                calendarNgayGiao.get(Calendar.MONTH),
                calendarNgayGiao.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void loadDuLieuKhachHang(){
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

        cursor.close();
    }

    public KhachHang getKhachHangTheoMa(int maKhachHang){
        KhachHang khachHang = new KhachHang();

        Cursor cursor = database.rawQuery("select * from KhachHang where Ma=?", new String[]{String.valueOf(maKhachHang)});

        while (cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String diaChi = cursor.getString(2);
            String dienThoai = cursor.getString(3);

            khachHang = new KhachHang(ma, ten, diaChi, dienThoai);
        }

        cursor.close();

        return khachHang;
    }

    public void loadDuLieuMatHang(){
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

        cursor.close();
    }

    public MatHang getMatHangTheoMa(String maHang){
        MatHang matHang = new MatHang();

        Cursor cursor = database.rawQuery("select * from MatHang where Ma=?", new String[]{maHang});

        while (cursor.moveToNext()){
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String donVi = cursor.getString(2);
            String donGia = cursor.getString(3);

            matHang = new MatHang(ma, ten, donVi, donGia);
        }

        cursor.close();

        return matHang;
    }

    public int getTongSoHoaDon(){
        int dem = 0;

        Cursor cursor = database.rawQuery("select * from HoaDon", null);
        while (cursor.moveToNext()){
            dem++;
        }

        return dem;
    }

    public boolean lapHoaDon(int soHoaDon, String ngayLap, String ngayGiao, int maKhachHang){
        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("SoHD", soHoaDon);
        row.put("NgayLap", ngayLap);
        row.put("NgayGiao", ngayGiao);
        row.put("MaKH", maKhachHang);

        long result = database.insert("HoaDon", null, row);
        if(result != -1){
            return true;
        }

        return false;
    }

    public boolean lapChiTietHoaDon(int soHoaDon, String maHang, int soLuong){
        ContentValues row = new ContentValues();
        // Nhập đúng tên cột trong cơ sở dữ liệu
        row.put("SoHD", soHoaDon);
        row.put("MaHang", maHang);
        row.put("SoLuong", soLuong);

        long result = database.insert("ChiTietHoaDon", null, row);
        if(result != -1){
            return true;
        }

        return false;
    }

    public void lamMoiHoaDon(){
        spinnerKhachHang.setSelection(0);

        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtDienThoai.setText("");

        calendarNgayLap = Calendar.getInstance();
        calendarNgayGiao = Calendar.getInstance();

        txtNgayLap.setText("");
        txtNgayGiao.setText("");

        spinnerMatHang.setSelection(0);

        soThuTu = 0;
        soLuong = 0;

        hangHoaDonAdapter = new HangHoaDonAdapter(HoaDonTaoActivity.this, R.layout.item_hoa_don_tao);
        lvHangHoaDon.setAdapter(hangHoaDonAdapter);
        hangHoaDonList = new ArrayList<HangHoaDon>();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonTaoActivity.this);
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
}
