package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import truonghuynhhoa.ptit.model.MatHang;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class MaMatHangAdapter extends ArrayAdapter<MatHang> {
    private Activity context;
    private int resource1;
    private int resource2;

    public MaMatHangAdapter(Activity context, int resource1, int resource2) {
        super(context, resource1, resource2);
        this.context = context;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemMatHang = layoutInflater.inflate(this.resource2, null);

        TextView txtMaHang = itemMatHang.findViewById(R.id.txtMaHang);
        TextView txtTenHang = itemMatHang.findViewById(R.id.txtTenHang);
        TextView txtDonGia = itemMatHang.findViewById(R.id.txtDonGia);
        TextView txtDonVi = itemMatHang.findViewById(R.id.txtDonVi);
        LinearLayout layout = itemMatHang.findViewById(R.id.layout);

        MatHang matHang = getItem(position);

        if(position == 0){
            txtMaHang.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }

        txtMaHang.setText(matHang.getMa());
        txtTenHang.setText(matHang.getTen());
        txtDonGia.setText(matHang.getDonGia() + "/");
        txtDonVi.setText(matHang.getDonVi());

        return itemMatHang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemMatHangLuaChon = layoutInflater.inflate(this.resource1, null);

        ImageView imgHinhAnh = itemMatHangLuaChon.findViewById(R.id.imgHinhAnh);
        TextView txtThongTin = itemMatHangLuaChon.findViewById(R.id.txtThongTin);

        MatHang matHang = getItem(position);
        if(matHang.getTen() == null){
            txtThongTin.setText("Chọn mặt hàng");
        }
        else{
            txtThongTin.setText(matHang.getTen());
        }

        return itemMatHangLuaChon;
    }
}
