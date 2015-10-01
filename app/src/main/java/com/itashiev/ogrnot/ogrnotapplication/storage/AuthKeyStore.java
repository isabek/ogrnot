package com.itashiev.ogrnot.ogrnotapplication.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthKeyStore {

    private static String keyForAuthKey = "authKey";

    private static final String SETTINGS_FILE = "OgrNotFile";

    public static void setAuthKey(Context context, String authKey) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(keyForAuthKey, authKey);
        editor.apply();
    }

    public static String getAuthKey(Context context) {
        SharedPreferences preferences = getPreferences(context);

        return preferences.getString(keyForAuthKey, null);
    }

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
