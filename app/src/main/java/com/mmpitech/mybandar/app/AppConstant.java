package com.mmpitech.mybandar.app;

import com.mmpitech.mybandar.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zirate on 3/9/16.
 */
public class AppConstant {

    public static final String RATE = "rates";

    public static final String CURRENCY_URL = "http://forex.cbm.gov.mm/api/latest";

    public static final int[] EXPENSES = {R.mipmap.ic_beer, R.mipmap.ic_bus, R.mipmap.ic_car, R.mipmap.ic_shopping,
            R.mipmap.ic_coffee, R.mipmap.ic_jewelry, R.mipmap.ic_food, R.mipmap.ic_gas, R.mipmap.ic_gift,
            R.mipmap.ic_love, R.mipmap.ic_home, R.mipmap.ic_entertainment, R.mipmap.ic_phone, R.mipmap.ic_bus};

    public static final int[] INCOME = {R.mipmap.ic_salary, R.mipmap.ic_bonus, R.mipmap.ic_work};

    public static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
}
