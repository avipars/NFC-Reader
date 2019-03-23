package com.aviparshan.nfcreader.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

/**
 * Created by avipars on 3/23/2019 on com.aviparshan.nfcreader.Utils
 */
public class BaseApp extends Application {

    public final static String PREFS = "NFC_PREFS";
    public static final String PREFERENCE_ZONE = "KEY_ZONE";
    public static final String PREFERENCE_LOGGED = "KEY_LOGGED";
    public static final String PREFERENCE_TOKEN = "KEY_TOKEN";
    public static int zone;
    public static boolean logged;
    public static String token;
    static SharedPreferences shared;

    public static int getZone(Context context) {
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        zone = shared.getInt(PREFERENCE_ZONE, 0);
        return zone;
    }

    public static void setZone(Context context, int value) {
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(PREFERENCE_ZONE, value);
        editor.apply();
    }

    public static boolean getLogged(Context context) {
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        logged = shared.getBoolean(PREFERENCE_LOGGED, false);
        return logged;
    }

    public static void setLogged(Context context, boolean value) { //stored as string
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(PREFERENCE_LOGGED, value);
        editor.apply();
    }

    public static void clearSharedPrefs(Context context) { //stored as string
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }

    public static void setToken(Context context, String value) { //stored as string
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(PREFERENCE_TOKEN, value);
        editor.apply();
    }

    public static String getToken(Context context) {
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        token = shared.getString(PREFERENCE_TOKEN, "n/a");
        return token;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // Context context = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
