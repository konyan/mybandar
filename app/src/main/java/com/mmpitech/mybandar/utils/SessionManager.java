package com.mmpitech.mybandar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mmpitech.mybandar.app.AppConstant;

/**
 * Created by nyanlintun on 3/21/16.
 */
public class SessionManager {


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {

        pref = context.getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE);


    }

    public void setJson(String json){
        editor = pref.edit();
        editor.putString(AppConstant.PREF_CURRENCY,json);
        editor.commit();
    }

    public String getJson(){
        return pref.getString(AppConstant.PREF_CURRENCY,null);
    }

    public void setDate(String date){
        editor = pref.edit();
        editor.putString(AppConstant.PREF_DATE,date);
        editor.commit();
    }
    public String getDate(){
        return pref.getString(AppConstant.PREF_DATE,null);
    }

    public void setLogin(boolean a){
        editor = pref.edit();
        editor.putBoolean("AA",a);
        editor.commit();
    }

    public boolean getLogin(){
        return pref.getBoolean("AA",false);
    }


}
