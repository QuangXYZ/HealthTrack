package com.example.healthtrack.Utils.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "YourAppPrefs";
    private static final String TOKEN_KEY = "token";

    // Lưu token vào SharedPreferences
    public static void saveToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    // Lấy token từ SharedPreferences
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    // Xóa token khỏi SharedPreferences
    public static void clearToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
}
