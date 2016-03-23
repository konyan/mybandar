package com.mmpitech.mybandar.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zirate on 11/19/15.
 */
public class L {

    public static void m(String message) {
        Log.d("mylog", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
