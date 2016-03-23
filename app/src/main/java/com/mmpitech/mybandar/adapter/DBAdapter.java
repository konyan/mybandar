package com.mmpitech.mybandar.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.ChildData;
import com.mmpitech.mybandar.model.Data;
import com.mmpitech.mybandar.model.ParentData;
import com.mmpitech.mybandar.utils.DBHelper;
import com.mmpitech.mybandar.utils.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zirate on 3/17/16.
 */
public class DBAdapter {


    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }


    public long addData(Data data) {

        long id;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TYPE, data.getType());
        values.put(DBHelper.COLUMN_CATEGORY, data.getCategory());
        values.put(DBHelper.COLUMN_AMOUNT, data.getAmount());
        values.put(DBHelper.COLUMN_NOTE, data.getNote());
        values.put(DBHelper.COLUMN_DATE, AppConstant.formatter.format(data.getDate()));

        id = db.insert(DBHelper.TABLE_BANDAR, null, values);
        Log.d("mylog", "Id :" + id);
        db.close();
        return id;
    }

    public List<ParentData> getAllData() throws ParseException {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ParentData> parentDataList = new ArrayList<ParentData>();

        String query = "SELECT * FROM " + dbHelper.TABLE_BANDAR;
        //SELECT sum(OrderPrice) AS Total, Customer FROM Orders GROUP BY Customer;

//        String query = "SELECT sum(" + dbHelper.COLUMN_AMOUNT + ") AS totalAmount," + dbHelper.COLUMN_CATEGORY + "," + dbHelper.COLUMN_TYPE + " FROM " + dbHelper.TABLE_BANDAR;


        Cursor c = db.rawQuery(query, null);


        if (c.moveToFirst()) {
            do {
                ParentData p = new ParentData();

                p.setType(c.getInt(1));
                p.setCategory(c.getInt(2));
                p.setAmount(c.getInt(3));
                p.setNote(c.getString(4));
                p.setDate(AppConstant.formatter.parse(c.getString(5)));

                parentDataList.add(p);
            } while (c.moveToNext());

        }
        db.close();
        return parentDataList;

    }

    public List<ParentData> getParentData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ParentData> parentDataList = new ArrayList<ParentData>();

        String query = "SELECT SUM(" + dbHelper.COLUMN_AMOUNT + ") AS totalAmount,COUNT (" + dbHelper.COLUMN_CATEGORY + ") AS count," + dbHelper.COLUMN_CATEGORY + "," + dbHelper.COLUMN_TYPE + " FROM " + dbHelper.TABLE_BANDAR + " GROUP BY " + DBHelper.COLUMN_CATEGORY;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ParentData p = new ParentData();

                p.setAmount(c.getInt(0));
                p.setCount(c.getInt(1));
                p.setCategory(c.getInt(2));
                p.setType(c.getInt(3));

                parentDataList.add(p);
            } while (c.moveToNext());

        }

        db.close();
        return parentDataList;

    }

    public List<ParentData> getReportData() throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ParentData> parentDataList = new ArrayList<ParentData>();

        String query = "SELECT SUM(" + dbHelper.COLUMN_AMOUNT + ") AS totalAmount," + dbHelper.COLUMN_CATEGORY + "," + dbHelper.COLUMN_DATE + " FROM " + dbHelper.TABLE_BANDAR + " WHERE " + dbHelper.COLUMN_TYPE + "=1 GROUP BY " + DBHelper.COLUMN_CATEGORY;

        Cursor c = db.rawQuery(query, null);

//        Log.d("mylog", " Cursor COunt:" + c.getCount());
        if (c.moveToFirst()) {
            do {
                ParentData p = new ParentData();

                p.setAmount(c.getInt(0));
                p.setCategory(c.getInt(1));
                p.setDate(AppConstant.formatter.parse(c.getString(2)));

                parentDataList.add(p);
            } while (c.moveToNext());

        }

        db.close();
        return parentDataList;
    }


    public List<ChildData> getChildData(int category) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ChildData> childDatas = new ArrayList<ChildData>();

        String query = "SELECT " + dbHelper.COLUMN_AMOUNT + "," + dbHelper.COLUMN_NOTE + "," + dbHelper.COLUMN_DATE + " FROM " + dbHelper.TABLE_BANDAR + " WHERE " + dbHelper.COLUMN_CATEGORY + "=" + category;


        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ChildData child = new ChildData();

                child.setAmount(c.getInt(0));
                child.setNote(c.getString(1));
                child.setDate(AppConstant.formatter.parse(c.getString(2)));
                childDatas.add(child);
            } while (c.moveToNext());

        }

        db.close();


        return childDatas;
    }

    public int getTotalAmount(int type) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int result = 0;

        String query = "SELECT SUM(" + dbHelper.COLUMN_AMOUNT + ") AS totalExpense," + dbHelper.COLUMN_DATE + " FROM " + dbHelper.TABLE_BANDAR + " WHERE " + dbHelper.COLUMN_TYPE + "=" + type;// +" GROUP BY STRFTIME('%m',"+dbHelper.COLUMN_DATE+") = '03'";
        Cursor c = db.rawQuery(query, null);
        Log.d("mylog", "Cursor :" + c.getCount());
        if (c.moveToFirst()) {
            do {

                result = c.getInt(0);
                Log.d("mylog", "Result" + result);
                Log.d("mylog", "Result" + c.getString(1));

            } while (c.moveToNext());
        }
        return result;
    }

//    public String getLastDate() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String date = null;
//
//        // select current_test_id from someTable order by test_datetime desc limit 1
//        String query = "SELECT " + dbHelper.COLUMN_DATE + " FROM " + dbHelper.TABLE_BANDAR; //+ " WHERE strftime('%m', date) = '03'";
//        Log.d("mylog", "QUery :" + query);
//        Cursor c = db.rawQuery(query, null);
//        Log.d("mylog", "Cursor :" + c.getCount());
//
//        if (c.moveToFirst()) {
//            do {
//
//                date = c.getString(0);
//
////                Log.d("mylog", "Curso " + c.getString(1));
//                Log.d("mylog", "Date " + date);
//            } while (c.moveToNext());
//        }
//        db.close();
//        return date;
//    }


    private List<Integer> getAllDate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Integer> integers = new ArrayList<Integer>();

        String query = "SELECT * FROM " + dbHelper.TABLE_BANDAR;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {

                integers.add(c.getInt(1));
            } while (c.moveToNext());

        }
        db.close();
        return integers;

    }


    public List<Date> getGroupDate() throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Date> strings = new ArrayList<Date>();

//        String query =  "select strftime('%Y'," + dbHelper.COLUMN_DATE + ") from "
//                + dbHelper.TABLE_BANDAR +" group by strftime('%Y'," + dbHelper.COLUMN_DATE + ")";

//        String query = "SELECT STRFTIME('%Y',"+dbHelper.COLUMN_DATE+",'unixepoch') AS MONTH FROM " + dbHelper.TABLE_BANDAR +" WHERE MONTH = '2016' GROUP BY MONTH";
        String query = "SELECT " + dbHelper.COLUMN_DATE + " AS MONTH FROM " + dbHelper.TABLE_BANDAR + " GROUP BY MONTH";

        Cursor c = db.rawQuery(query, null);
        Log.d("mylog", "Cursor :" + c.getCount());
        if (c.moveToFirst()) {
            do {
                strings.add(AppConstant.formatter.parse(c.getString(0)));

            } while (c.moveToNext());

        }

        db.close();
        return strings;

    }
}
