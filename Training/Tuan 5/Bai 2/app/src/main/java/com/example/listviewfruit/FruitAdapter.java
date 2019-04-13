package com.example.listviewfruit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<Fruit> arrayFruit;

    public FruitAdapter(Context myContext, int myLayout, List<Fruit> arrayFruit) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayFruit = arrayFruit;
    }

    @Override
    public int getCount() {
        return arrayFruit.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class viewHolder{
        ImageView img_anh;
        TextView txt_moTa, txt_ten;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout,null);
            holder = new viewHolder();
            holder.txt_ten = convertView.findViewById(R.id.txt_ten);
            holder.txt_moTa = convertView.findViewById(R.id.txt_moTa);
            holder.img_anh = (ImageView) convertView.findViewById(R.id.img_anh);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.txt_ten.setText(arrayFruit.get(position).Ten);
        holder.txt_moTa.setText(arrayFruit.get(position).MoTa);
        holder.img_anh.setImageResource(arrayFruit.get(position).Hinh);
        return convertView;
    }
}
