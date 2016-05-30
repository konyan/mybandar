package com.mmpitech.mybandar.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zirate on 3/16/16.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bandar";

    //Table Name
    public static final String TABLE_BANDAR = "bandar";

    //COLUMN NAME BANDAR
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";

    //COLUMN MONTH
    public static final String COLUMN_MONTH = "month";

    //Query
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_BANDAR + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TYPE + " INT," +
            COLUMN_CATEGORY + " INT," +
            COLUMN_AMOUNT + " INT," + COLUMN_NOTE + " TEXT," +
            COLUMN_DATE + " DATE" + ")";

//    private String CREATE_MONTH_TABLE = "CREATE TABLE " + TABLE_MONTH + "(" + COLUMN_ID +
//            " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MONTH + " INT )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE);

        onCreate(db);
    }
}
