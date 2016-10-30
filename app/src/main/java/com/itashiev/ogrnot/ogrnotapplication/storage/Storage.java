package com.itashiev.ogrnot.ogrnotapplication.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Storage {

    private static String keyForAuthKey = "authKey";
    private static String keyForNumber = "number";
    private static String keyForPassword = "password";

    public static void setAuthKey(Context context, String authKey) {
        setValueByKey(context, keyForAuthKey, authKey);
    }

    public static void setNumber(Context context, String number) {
        setValueByKey(context, keyForNumber, number);
    }

    public static void setPassword(Context context, String password) {
        setValueByKey(context, keyForPassword, password);
    }

    public static String getAuthKey(Context context) {
        return getValueByKey(context, keyForAuthKey);
    }

    public static String getNumber(Context context) {
        return getValueByKey(context, keyForNumber);
    }

    public static String getPassword(Context context) {
        return getValueByKey(context, keyForPassword);
    }

    private static void setValueByKey(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getValueByKey(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(key, null);
    }

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
