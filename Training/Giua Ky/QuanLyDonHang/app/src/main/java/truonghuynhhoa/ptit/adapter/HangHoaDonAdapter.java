package truonghuynhhoa.ptit.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import truonghuynhhoa.ptit.model.HangHoaDon;
import truonghuynhhoa.ptit.quanlydonhang.HoaDonTaoActivity;
import truonghuynhhoa.ptit.quanlydonhang.R;

public class HangHoaDonAdapter extends ArrayAdapter<HangHoaDon> {

    private Activity context;
    private int resource;
    private int soLuong;
    private List<Integer> danhSachSoLuong;
    private boolean check;

    public HangHoaDonAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        danhSachSoLuong = new ArrayList<Integer>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        check = false;

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View itemHangHoaDon = layoutInflater.inflate(this.resource, null);

        TextView txtSoThuTu = itemHangHoaDon.findViewById(R.id.txtSoThuTu);
        TextView txtMaHang = itemHangHoaDon.findViewById(R.id.txtMaHang);
        TextView txtTenHang = itemHangHoaDon.findViewById(R.id.txtTenHang);
        final TextView txtSoLuong = itemHangHoaDon.findViewById(R.id.txtSoLuong);

        ImageView imgGiam = itemHangHoaDon.findViewById(R.id.imgGiam);
        ImageView imgTang = itemHangHoaDon.findViewById(R.id.imgTang);
        ImageView imgXoa = itemHangHoaDon.findViewById(R.id.imgXoa);

        final HangHoaDon hangHoaDon = getItem(position);

        txtSoThuTu.setText(String.valueOf(hangHoaDon.getSoThuTu()));
        txtMaHang.setText(hangHoaDon.getMaHang());
        txtTenHang.setText(hangHoaDon.getTenHang());
        try{
            soLuong = danhSachSoLuong.get(position);
            check = true;
        }
        catch (Exception e){

            Log.e("ERROR ARRAY", e.toString());
        }
        finally {
            if(!check){
                /*List<Integer> a = new ArrayList<>();
                a.add(1);*/
                danhSachSoLuong.add(position, 1);
                //danhSachSoLuong.set(position, 1);
            }
            txtSoLuong.setText(String.valueOf(danhSachSoLuong.get(position)));
        }


        imgTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong = danhSachSoLuong.get(position);
                soLuong++;

                danhSachSoLuong.set(position, soLuong);
                txtSoLuong.setText(String.valueOf(danhSachSoLuong.get(position)));

                HoaDonTaoActivity hoaDonTaoActivity = (HoaDonTaoActivity) context;
                List<HangHoaDon> hangHoaDonList = hoaDonTaoActivity.getHangHoaDonList();

                for(int i = 0; i < hangHoaDonList.size(); i++){
                    if(i == position){
                        hangHoaDonList.get(i).setSoLuong(soLuong);
                        break;
                    }
                }

                hoaDonTaoActivity.setHangHoaDonList(hangHoaDonList);
            }
        });

        imgGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong = danhSachSoLuong.get(position);
                soLuong--;
                if(soLuong < 1){
                    soLuong = 1;
                }

                danhSachSoLuong.set(position, soLuong);
                txtSoLuong.setText(String.valueOf(danhSachSoLuong.get(position)));

                HoaDonTaoActivity hoaDonTaoActivity = (HoaDonTaoActivity) context;
                List<HangHoaDon> hangHoaDonList = hoaDonTaoActivity.getHangHoaDonList();

                for(int i = 0; i < hangHoaDonList.size(); i++){
                    if(i == position){
                        hangHoaDonList.get(i).setSoLuong(soLuong);
                        break;
                    }
                }

                hoaDonTaoActivity.setHangHoaDonList(hangHoaDonList);
            }
        });

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danhSachSoLuong.remove(position);

                HoaDonTaoActivity hoaDonTaoActivity = (HoaDonTaoActivity) context;
                List<HangHoaDon> hangHoaDonList = hoaDonTaoActivity.getHangHoaDonList();

                for(int i = 0; i < hangHoaDonList.size(); i++){
                    if(i == position){
                        hangHoaDonList.remove(i);
                        break;
                    }
                }

                for(int j = 0; j < hangHoaDonList.size(); j++){
                    int soThuTu = hangHoaDonList.get(j).getSoThuTu();
                    if(soThuTu > (position + 1)){
                        soThuTu--;
                        hangHoaDonList.get(j).setSoThuTu(soThuTu);
                    }
                }

                hoaDonTaoActivity.setHangHoaDonList(hangHoaDonList);
                hoaDonTaoActivity.setSoThuTu(hangHoaDonList.size());

                remove(getItem(position));
            }
        });

        return itemHangHoaDon;
    }
}
