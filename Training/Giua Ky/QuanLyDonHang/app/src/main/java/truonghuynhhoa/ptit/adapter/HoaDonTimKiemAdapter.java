package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import truonghuynhhoa.ptit.model.HoaDon;
import truonghuynhhoa.ptit.model.KhachHang;
import truonghuynhhoa.ptit.quanlydonhang.HoaDonTimKiemActivity;
import truonghuynhhoa.ptit.quanlydonhang.R;


public class HoaDonTimKiemAdapter extends ArrayAdapter<String> {
    private Activity context;
    private int resource;

    public HoaDonTimKiemAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemHoaDonLuaChon = layoutInflater.inflate(this.resource, null);

        TextView txtSoHoaDon = itemHoaDonLuaChon.findViewById(R.id.txtSoHoaDon);
        TextView txtKhachHang = itemHoaDonLuaChon.findViewById(R.id.txtKhachHang);
        TextView txtNgayLap = itemHoaDonLuaChon.findViewById(R.id.txtNgayLap);
        TextView txtNgayGiao = itemHoaDonLuaChon.findViewById(R.id.txtNgayGiao);

        HoaDonTimKiemActivity hoaDonTimKiemActivity = (HoaDonTimKiemActivity) context;

        String soHoaDon = getItem(position);
        HoaDon hoaDon = hoaDonTimKiemActivity.timKiemHoaDonTheoSo(soHoaDon);

        txtSoHoaDon.setText("Số hóa đơn: " + soHoaDon);

        KhachHang khachHang = hoaDonTimKiemActivity.layThongTinKhachHang(hoaDon.getMaKhachHang());
        txtKhachHang.setText("Khách hàng: " + khachHang.getTen());

        txtNgayLap.setText("Ngày lập: " + hoaDon.getNgayLap());
        txtNgayGiao.setText("Ngày giao: " + hoaDon.getNgayGiao());

        return itemHoaDonLuaChon;
    }
}
