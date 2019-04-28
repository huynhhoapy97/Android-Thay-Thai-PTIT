package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import truonghuynhhoa.ptit.model.MatHangTrongHoaDon;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class ChiTietHoaDonAdapter extends ArrayAdapter<MatHangTrongHoaDon> {

    private Activity context;
    private int resource;

    public ChiTietHoaDonAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemHoaDonTimKiem = layoutInflater.inflate(this.resource, null);

        TextView txtMaHang = itemHoaDonTimKiem.findViewById(R.id.txtMaHang);
        TextView txtTenHang = itemHoaDonTimKiem.findViewById(R.id.txtTenHang);
        TextView txtDonGia = itemHoaDonTimKiem.findViewById(R.id.txtDonGia);
        TextView txtDonViTinh = itemHoaDonTimKiem.findViewById(R.id.txtDonViTinh);
        TextView txtSoLuong = itemHoaDonTimKiem.findViewById(R.id.txtSoLuong);

        MatHangTrongHoaDon matHang = getItem(position);
        txtMaHang.setText(matHang.getMa());
        txtTenHang.setText(matHang.getTen());
        txtDonGia.setText(matHang.getDonGia() + "/");
        txtDonViTinh.setText(matHang.getDonVi());
        txtSoLuong.setText(String.valueOf(matHang.getSoLuong()));

        return itemHoaDonTimKiem;
    }
}
