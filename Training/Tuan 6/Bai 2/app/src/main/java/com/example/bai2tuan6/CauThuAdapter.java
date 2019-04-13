package com.example.bai2tuan6;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CauThuAdapter extends BaseAdapter{
    Context myContext;
    int myLayout;

    public CauThuAdapter(Context myContext, int myLayout, List<CauThu> arrayCauThu) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayCauThu = arrayCauThu;
    }

    List<CauThu> arrayCauThu;
    @Override
    public int getCount() {
        return arrayCauThu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCauThu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    static class CauThuHolder {
        ImageView img;
        TextView txt1, txt2, txt3;     }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        CauThuHolder holder=null;
        if (row!=null){
            holder= (CauThuHolder) row.getTag();
        }
        else {
            holder= new CauThuHolder();
            LayoutInflater inflater= ((Activity)myContext).getLayoutInflater();
            row= inflater.inflate(R.layout.activity_listitem_cauthu,parent,false);
            holder.img= (ImageView) row.findViewById(R.id.imagecauthu);
            holder.txt1= (TextView) row.findViewById(R.id.textviewtencauthu);
            holder.txt2= (TextView) row.findViewById(R.id.textviewgiacauthu);
            holder.txt3= (TextView) row.findViewById(R.id.textviewteamcauthu);
            row.setTag(holder);
        }
        CauThu item= arrayCauThu.get(position);
        holder.img.setImageResource(R.drawable.messi);
        holder.txt1.setText("Name: "+item.getTencauthu());
        holder.txt2.setText("Price: "+item.getGiacauthu());
        holder.txt3.setText("Team "+item.getTeamcauthu());
        return row;
    }
}

