package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import truonghuynhhoa.ptit.bai1.R;
import truonghuynhhoa.ptit.model.NhanVien;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Activity context;
    private int resource;

    public NhanVienAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemNhanVien = layoutInflater.inflate(R.layout.item_nhanvien, null);

        ImageView imgGioiTinh = itemNhanVien.findViewById(R.id.imgGioiTinh);
        TextView txtThongTin = itemNhanVien.findViewById(R.id.txtThongTin);
        CheckBox chkXoa = itemNhanVien.findViewById(R.id.chkXoa);

        NhanVien nhanVien = getItem(position);
        if(nhanVien.getGioiTinh()){
            imgGioiTinh.setImageResource(R.drawable.nu);
        }
        else{
            imgGioiTinh.setImageResource(R.drawable.nam);
        }

        txtThongTin.setText(nhanVien.getMa() + "-" + nhanVien.getTen());

        return itemNhanVien;
    }
}
