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

import truonghuynhhoa.ptit.model.MatHang;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class MatHangAdapter extends ArrayAdapter<MatHang> {
    private Activity context;
    private int resource;

    public MatHangAdapter( Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemMatHang = layoutInflater.inflate(this.resource, null);

        TextView txtMaHang = itemMatHang.findViewById(R.id.txtMaHang);
        TextView txtTenHang = itemMatHang.findViewById(R.id.txtTenHang);
        TextView txtDonGia = itemMatHang.findViewById(R.id.txtDonGia);
        TextView txtDonVi = itemMatHang.findViewById(R.id.txtDonVi);

        MatHang matHang = getItem(position);
        txtMaHang.setText(matHang.getMa());
        txtTenHang.setText(matHang.getTen());
        txtDonGia.setText(matHang.getDonGia() + "/");
        txtDonVi.setText(matHang.getDonVi());

        return itemMatHang;
    }
}
