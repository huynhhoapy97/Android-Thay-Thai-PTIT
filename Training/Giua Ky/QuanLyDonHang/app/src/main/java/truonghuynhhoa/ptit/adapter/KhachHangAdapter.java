package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import truonghuynhhoa.ptit.model.KhachHang;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class KhachHangAdapter extends ArrayAdapter<KhachHang> {
    private Activity context;
    private int resource;

    public KhachHangAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemKhachHang = layoutInflater.inflate(this.resource, null);

        TextView txtHoTen = itemKhachHang.findViewById(R.id.txtHoTen);
        TextView txtDienThoai = itemKhachHang.findViewById(R.id.txtDienThoai);
        TextView txtDiaChi = itemKhachHang.findViewById(R.id.txtDiaChi);

        KhachHang khachHang = getItem(position);
        txtHoTen.setText(khachHang.getTen());
        txtDienThoai.setText(khachHang.getDienThoai());
        txtDiaChi.setText(khachHang.getDiaChi());

        return itemKhachHang;
    }
}

