package com.example.tuan5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NationalAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<National> arrayNational;

    public NationalAdapter(Context myContext, int myLayout, List<National> arrayNational) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayNational = arrayNational;
    }

    @Override
    public int getCount() {
        return arrayNational.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayNational.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class viewHolder{
        ImageView img_anh;
        TextView txt_population, txt_ten;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout,null);
            holder = new viewHolder();
            holder.txt_ten =(TextView) convertView.findViewById(R.id.textviewtennational);
            holder.txt_population = (TextView) convertView.findViewById(R.id.textviewpopulation);
            holder.img_anh = (ImageView) convertView.findViewById(R.id.imagenational);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        National national=(National) getItem(position);
        holder.txt_ten.setText(national.getTen());
        holder.txt_population.setText(national.getPopulation());
        holder.img_anh.setImageResource(national.getHinh());
        return convertView;
    }
}
