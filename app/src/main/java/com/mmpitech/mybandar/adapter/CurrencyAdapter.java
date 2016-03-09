package com.mmpitech.mybandar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.model.Currency;

import java.util.ArrayList;

/**
 * Created by zirate on 3/9/16.
 */
public class CurrencyAdapter extends ArrayAdapter{

    private LayoutInflater inflater;
    private ArrayList<Currency>currencies;
    private Context context;


    public CurrencyAdapter(Context c,ArrayList<Currency> currencies) {
        super(c, 0,currencies);

        this.context = c;
        this.currencies = currencies;
        inflater = LayoutInflater.from(c);

    }

    @Override
    public Currency getItem(int position) {
        return currencies.get(position);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder;
        Currency c = currencies.get(position);
        Log.d("mylog","C : " + c.getPrice());
        if(v == null){

            v = inflater.inflate(R.layout.currency_row,parent,false);
            holder = new ViewHolder();

            holder.txtCountry = (TextView)v.findViewById(R.id.txtNation);
            holder.txtPrice = (TextView)v.findViewById(R.id.txtPrice);
            holder.imgCountry = (ImageView)v.findViewById(R.id.imgNation);

            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }

        holder.txtPrice.setText(c.getPrice()+"");
        holder.txtCountry.setText(c.getCountry()+"");
        holder.imgCountry.setImageResource(c.getFlags());

        return  v;
    }

    private class ViewHolder{
        private ImageView imgCountry;
        private TextView txtCountry;
        private TextView txtPrice;

    }
}
