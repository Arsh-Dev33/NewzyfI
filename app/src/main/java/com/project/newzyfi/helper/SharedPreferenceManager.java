package com.project.newzyfi.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Chirag on 29/12/16.
 */

public class SharedPreferenceManager {

    private static SharedPreferenceManager ourInstance = new SharedPreferenceManager();
    private String defaultString = "";

    public static SharedPreferenceManager getInstance() {
        return ourInstance;
    }

    public SharedPreferenceManager() {
    }

    //Jwt Token
    public  static  void setJwtToken(Context c, String jwtToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JwtToken",jwtToken);
        editor.commit();
    }

    public  static String getJwtToken(Context c) {
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(c);
        return  sharedPreference.getString("JwtToken","");
    }

    //User Id
    public  static  void setUserId(Context c, String userId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId",userId);
        editor.commit();
    }

    public  static String getUserId(Context c) {
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(c);
        return  sharedPreference.getString("UserId","");
    }


    //Timestamp
    public  static  void setTimestamp(Context c, String timeStamp) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Timestamp",timeStamp);
        editor.commit();
    }

    public  static String getTimestamp(Context c) {
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(c);
        return  sharedPreference.getString("Timestamp","");
    }

    //Country
    public  static  void setCountry(Context c, String country) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Country",country);
        editor.commit();
    }

    public  static String getCountry(Context c) {
        SharedPreferences sharedPreference= PreferenceManager.getDefaultSharedPreferences(c);
        return  sharedPreference.getString("Country","");
    }


}
