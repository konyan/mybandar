package com.mmpitech.mybandar.app;

import android.content.Context;
import android.util.DisplayMetrics;

import com.mmpitech.mybandar.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zirate on 3/9/16.
 */
public class AppConstant {

    public static final String RATE = "rates";

    public static final String CURRENCY_URL = "http://forex.cbm.gov.mm/api/latest";


    //Type = 1
    public static final int[] EXPENSES = {R.mipmap.ic_beer, R.mipmap.ic_bus, R.mipmap.ic_car, R.mipmap.ic_shopping,
            R.mipmap.ic_coffee, R.mipmap.ic_jewelry, R.mipmap.ic_food, R.mipmap.ic_gas, R.mipmap.ic_gift,
            R.mipmap.ic_love, R.mipmap.ic_home, R.mipmap.ic_entertainment, R.mipmap.ic_phone, R.mipmap.ic_taxi};

    public static final String[] STRING_EXPENSES = {"Beer", "Bus", "Car", "Shopping", "Coffee", "Jewelry", "Food", "Gas",
            "Gift", "Love", "Home", "Movie", "Phone", "Taxi"};

        //Type = 0
    public static final int[] INCOME = {R.mipmap.ic_salary, R.mipmap.ic_bonus, R.mipmap.ic_work};
    public static final String[] STRING_INCOME = {"Salary", "Bonus", "Work"};

    public static final SimpleDateFormat formatter = new SimpleDateFormat(
            "MMMM dd,yyyy", Locale.ENGLISH);

    public static final SimpleDateFormat formatter2 = new SimpleDateFormat(
            "dd MMM ", Locale.ENGLISH);

    public static final SimpleDateFormat formatToMonth = new SimpleDateFormat(
            "M", Locale.ENGLISH);

    public static final SimpleDateFormat formatToMonthFullName = new SimpleDateFormat(
            "MMMM", Locale.ENGLISH);

    public static String[] MONTHNAMES = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    //INTENT EXTRA
    public static final String EXTRA_TYPE = "type";


    //HashMap Key
    public static final String ID = "id";


    //Pref Name
    public static final String PREF_NAME = "MyCurrency";
    public static final String PREF_CURRENCY = "currency";
    public static final String PREF_DATE = "date";

    //Toolbar Title
    public static final String TOOLBAR_TITLE_EXCHANGE = "Exchange";
    public static final String TOOLBAR_TITLE_ = "Exchange";

}
