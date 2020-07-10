package com.local.project.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.StringUtils;
import com.local.project.R;

public class PrefUtils {

    public static final String LOGIN_USER_INFO = "LOGIN_USER_INFO";//登录用户信息
    private static final String SHARE_PREFS_NAME = StringUtils.getString(R.string.app_name);

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
    }

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref = getSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context ctx, String key,boolean defaultValue) {
        SharedPreferences pref = getSharedPreferences(ctx);
        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = getSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = getSharedPreferences(ctx);
        return pref.getString(key, defaultValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences pref = getSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences pref = getSharedPreferences(ctx);
        return pref.getInt(key, defaultValue);
    }

    public static void remove(Context ctx, String key) {
        SharedPreferences pref = getSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(key);
        edit.apply();
    }
}
