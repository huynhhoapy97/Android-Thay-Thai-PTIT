package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import truonghuynhhoa.ptit.model.KhachHang;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class MaKhachHangAdapter extends ArrayAdapter<KhachHang> {

    private Activity context;
    private int resource1;
    private int resource2;

    public MaKhachHangAdapter(Activity context, int resource1, int resource2) {
        super(context, resource1, resource2);
        this.context = context;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemKhachHang = layoutInflater.inflate(this.resource2, null);

        TextView txtHoTen = itemKhachHang.findViewById(R.id.txtHoTen);
        TextView txtDienThoai = itemKhachHang.findViewById(R.id.txtDienThoai);
        TextView txtDiaChi = itemKhachHang.findViewById(R.id.txtDiaChi);

        KhachHang khachHang = getItem(position);

        if(position == 0){
            txtHoTen.setVisibility(View.GONE);
            txtDienThoai.setVisibility(View.GONE);
            txtDiaChi.setVisibility(View.GONE);
        }

        txtHoTen.setText(khachHang.getTen());
        txtDienThoai.setText(khachHang.getDienThoai());
        txtDiaChi.setText(khachHang.getDiaChi());

        return itemKhachHang;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemKhachHangLuaChon = layoutInflater.inflate(this.resource1, null);

        ImageView imgHinhAnh = itemKhachHangLuaChon.findViewById(R.id.imgHinhAnh);
        TextView txtThongTin = itemKhachHangLuaChon.findViewById(R.id.txtThongTin);

        KhachHang khachHang = getItem(position);
        if(khachHang.getTen() == null){
            txtThongTin.setText("Chọn khách hàng");
        }
        else{
            txtThongTin.setText(khachHang.getTen());
        }

        return itemKhachHangLuaChon;
    }
}
