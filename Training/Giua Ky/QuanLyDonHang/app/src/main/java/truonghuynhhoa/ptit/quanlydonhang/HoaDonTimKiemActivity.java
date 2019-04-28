package truonghuynhhoa.ptit.quanlydonhang;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import truonghuynhhoa.ptit.adapter.ChiTietHoaDonAdapter;
import truonghuynhhoa.ptit.adapter.HoaDonTimKiemAdapter;
import truonghuynhhoa.ptit.model.ChiTietHoaDon;
import truonghuynhhoa.ptit.model.HoaDon;
import truonghuynhhoa.ptit.model.KhachHang;
import truonghuynhhoa.ptit.model.MatHang;
import truonghuynhhoa.ptit.model.MatHangTrongHoaDon;

public class HoaDonTimKiemActivity extends AppCompatActivity {

    private AutoCompleteTextView autoTimKiem;
    private HoaDonTimKiemAdapter adapterHoaDonTimKiem;
    private List<HoaDon> hoaDonList;

    private ListView lvChiTietHoaDon;
    private ChiTietHoaDonAdapter adapterChiTietHoaDon;
    private List<MatHangTrongHoaDon> matHangTrongHoaDonList;

    private Button btnTimKiem;
    private TextView txtSoHoaDon, txtNgayLap, txtNgayGiao, txtHoTen, txtDienThoai, txtDiaChi;

    // Tên CSDL
    private String DATABASE_NAME = "quanlydonhang.sqlite";
    // Cho phép truy vấn hoặc tương tác với CSDL
    private SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_tim_kiem);
        addControls();
        addEvents();
    }

    private void addControls() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        autoTimKiem = findViewById(R.id.autoTimKiem);

        adapterHoaDonTimKiem = new HoaDonTimKiemAdapter(HoaDonTimKiemActivity.this,
                R.layout.item_hoa_don_lua_chon);
        hoaDonList = new ArrayList<HoaDon>();

        loadDuLieuHoaDon();

        for(HoaDon hoaDon : hoaDonList){
            adapterHoaDonTimKiem.add(String.valueOf(hoaDon.getSoHoaDon()));
        }

        autoTimKiem.setAdapter(adapterHoaDonTimKiem);

        btnTimKiem = findViewById(R.id.btnTimKiem);

        txtSoHoaDon = findViewById(R.id.txtSoHoaDon);
        txtNgayLap = findViewById(R.id.txtNgayLap);
        txtNgayGiao = findViewById(R.id.txtNgayGiao);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtDienThoai = findViewById(R.id.txtDienThoai);
        txtDiaChi = findViewById(R.id.txtDiaChi);

        lvChiTietHoaDon = findViewById(R.id.lvChiTietHoaDon);
        adapterChiTietHoaDon = new ChiTietHoaDonAdapter(HoaDonTimKiemActivity.this, R.layout.item_hoa_don_tim_kiem);
        lvChiTietHoaDon.setAdapter(adapterChiTietHoaDon);

        matHangTrongHoaDonList = new ArrayList<MatHangTrongHoaDon>();
    }

    private void addEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soHoaDon = autoTimKiem.getText().toString();
                if(soHoaDon.equals("")){
                    Toast.makeText(HoaDonTimKiemActivity.this, "Vui lòng nhập số hóa đơn", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    HoaDon hoaDon = timKiemHoaDonTheoSo(soHoaDon);
                    if(hoaDon.getSoHoaDon() == null){
                        Toast.makeText(HoaDonTimKiemActivity.this, "Số hóa đơn không tồn tại\nVui lòng thử lại", Toast.LENGTH_SHORT).show();
                        txtSoHoaDon.setText("");
                        txtNgayLap.setText("");
                        txtNgayGiao.setText("");
                        txtHoTen.setText("");
                        txtDienThoai.setText("");
                        txtDiaChi.setText("");

                        matHangTrongHoaDonList.clear();
                        adapterChiTietHoaDon.clear();
                        //adapterChiTietHoaDon.addAll(matHangTrongHoaDonList);
                    }
                    else {
                        matHangTrongHoaDonList.clear();
                        adapterChiTietHoaDon.clear();

                        String ngayLap = hoaDon.getNgayLap();
                        String ngayGiao = hoaDon.getNgayGiao();
                        int maKhachHang = hoaDon.getMaKhachHang();

                        KhachHang khachHang = layThongTinKhachHang(maKhachHang);
                        String hoTen = khachHang.getTen();
                        String dienThoai = khachHang.getDienThoai();
                        String diaChi = khachHang.getDiaChi();

                        txtSoHoaDon.setText(soHoaDon);
                        txtNgayLap.setText(ngayLap);
                        txtNgayGiao.setText(ngayGiao);
                        txtHoTen.setText(hoTen);
                        txtDienThoai.setText(dienThoai);
                        txtDiaChi.setText(diaChi);

                        List<ChiTietHoaDon> chiTietHoaDon = layThongTinChiTietHoaDon(soHoaDon);
                        for(ChiTietHoaDon chiTiet : chiTietHoaDon){
                            String maHang = chiTiet.getMaHang();
                            MatHang matHang = layMatHangTheoMa(maHang);

                            String ma = matHang.getMa();
                            String ten = matHang.getTen();
                            String donVi = matHang.getDonVi();
                            String donGia = matHang.getDonGia();
                            int soLuong = chiTiet.getSoLuong();

                            MatHangTrongHoaDon matHangTrongHoaDon = new MatHangTrongHoaDon(ma, ten, donVi, donGia, soLuong);

                            matHangTrongHoaDonList.add(matHangTrongHoaDon);
                        }

                        adapterChiTietHoaDon.addAll(matHangTrongHoaDonList);
                    }
                }
            }
        });
    }

    public void loadDuLieuHoaDon(){
        HoaDon hoaDon = new HoaDon();

        Cursor cursor = database.rawQuery("select * from HoaDon", null);

        hoaDonList.clear();

        while (cursor.moveToNext()){
            int soHoaDon  = cursor.getInt(0);
            String ngayLap = cursor.getString(1);
            String ngayGiao = cursor.getString(2);
            int maKhachHang = cursor.getInt(3);

            hoaDon = new HoaDon(soHoaDon, ngayLap, ngayGiao, maKhachHang);
            hoaDonList.add(hoaDon);
        }

        cursor.close();
    }

    public HoaDon timKiemHoaDonTheoSo(String so){
        HoaDon hoaDon = new HoaDon();

        Cursor cursor = database.rawQuery("select * from HoaDon where SoHD=?", new String[]{so});

        hoaDonList.clear();

        while (cursor.moveToNext()){
            int soHoaDon  = cursor.getInt(0);
            String ngayLap = cursor.getString(1);
            String ngayGiao = cursor.getString(2);
            int maKhachHang = cursor.getInt(3);

            hoaDon = new HoaDon(soHoaDon, ngayLap, ngayGiao, maKhachHang);
        }

        cursor.close();

        return hoaDon;
    }

    public KhachHang layThongTinKhachHang(int maKhachHang){
        KhachHang khachHang = new KhachHang();

        Cursor cursor = database.rawQuery("select * from KhachHang where Ma=?", new String[]{String.valueOf(maKhachHang)});

        while (cursor.moveToNext()){
            int ma  = cursor.getInt(0);
            String ten = cursor.getString(1);
            String diaChi = cursor.getString(2);
            String dienThoai = cursor.getString(3);

            khachHang = new KhachHang(ma, ten, diaChi, dienThoai);
        }

        cursor.close();

        return khachHang;
    }

    public List<ChiTietHoaDon> layThongTinChiTietHoaDon(String so){
        List<ChiTietHoaDon> chiTietHoaDon = new ArrayList<ChiTietHoaDon>();

        Cursor cursor = database.rawQuery("select * from ChiTietHoaDon where SoHD=?", new String[]{so});
        while (cursor.moveToNext()){
            int soHoaDon  = cursor.getInt(0);
            String maHang = cursor.getString(1);
            int soLuong = cursor.getInt(2);

            ChiTietHoaDon chiTiet = new ChiTietHoaDon(soHoaDon, maHang, soLuong);
            chiTietHoaDon.add(chiTiet);
        }

        return chiTietHoaDon;
    }

    public MatHang layMatHangTheoMa(String maHang){
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonTimKiemActivity.this);
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
