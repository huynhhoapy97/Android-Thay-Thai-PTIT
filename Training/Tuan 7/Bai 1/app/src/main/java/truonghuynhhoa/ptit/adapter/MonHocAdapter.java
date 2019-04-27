package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import truonghuynhhoa.ptit.bai1.R;
import truonghuynhhoa.ptit.model.MonHoc;

public class MonHocAdapter extends ArrayAdapter<MonHoc> {

    private Activity context;
    private int resource;

    public MonHocAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemMonHoc = layoutInflater.inflate(this.resource, null);

        TextView txtMa = itemMonHoc.findViewById(R.id.txtMa);
        TextView txtTen = itemMonHoc.findViewById(R.id.txtTen);
        TextView txtSoTiet = itemMonHoc.findViewById(R.id.txtSoTiet);

        MonHoc monHoc = getItem(position);
        txtMa.setText("Mã MH: " + monHoc.getMa());
        txtTen.setText("Tên MH: " + monHoc.getTen());
        txtSoTiet.setText("Số tiết: " + monHoc.getSoTiet());

        return itemMonHoc;


    }
}
