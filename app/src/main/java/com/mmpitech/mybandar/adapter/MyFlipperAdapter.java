package com.mmpitech.mybandar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.app.AppConstant;

/**
 * Created by nyanlintun on 3/21/16.
 */
public class MyFlipperAdapter extends BaseAdapter {

    private int[] mMonth;
    private LayoutInflater inflater;

    public MyFlipperAdapter(Context context, int[] month) {

        inflater = LayoutInflater.from(context);
        this.mMonth = month;
    }

    @Override
    public int getCount() {
        return mMonth.length;
    }

    @Override
    public Object getItem(int position) {
        return mMonth[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.flipper_item, parent, false);
            holder.txtMonth = (TextView) v.findViewById(R.id.txtMonth);

            v.setTag(holder);
        } else {

            holder = (ViewHolder) v.getTag();
        }

        int month = mMonth[position];
        holder.txtMonth.setText(AppConstant.MONTHNAMES[month-1] + "");

        return v;
    }

    private class ViewHolder {
        TextView txtMonth;
    }
}
