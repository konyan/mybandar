package com.mmpitech.mybandar.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.Data;
import com.mmpitech.mybandar.utils.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zirate on 3/17/16.
 */
public class DBAdapter {


    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper  = new DBHelper(context);
    }


    public long addData(boolean type,Data data){

        long id ;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(DBHelper.COLUMN_CATEGORY,data.getCategory());
        values.put(DBHelper.COLUMN_AMOUNT,data.getAmount());
        values.put(DBHelper.COLUMN_NOTE,data.getNote());
        values.put(DBHelper.COLUMN_DATE, AppConstant.formatter.format(data.getDate()));

        if(type){
            id = db.insert(DBHelper.TABLE_INCOME,null,values);
        }else {
            id = db.insert(DBHelper.TABLE_EXPENSE,null,values);
        }

        return id;

    }
}
