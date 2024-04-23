package com.example.healthtrack.Utils.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUser {
    private static final String idUser = "idUserPrefs";
    private static final String NAME = "NamePrefs";
    private static final String EMAIL = "EmailPrefs";
    private static final String PHONE = "PhonePrefs";
    private static final String idUser_KEY = "idUser";
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String PHONE_KEY = "phone";

    public static void SaveId(Context context, String origin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(idUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(idUser_KEY, origin);
        editor.apply();
    }

    public static void SaveName(Context context, String origin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, origin);
        editor.apply();
    }

    public static void SaveEmail(Context context, String destination) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, destination);
        editor.apply();
    }

    public static void savePhone(Context context, String date) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_KEY, date);
        editor.apply();
    }

    public static String getId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(idUser, Context.MODE_PRIVATE);
        return sharedPreferences.getString(idUser_KEY, null);
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME_KEY, null);
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public static String getPhone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_KEY, null);
    }

    public static void clearId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(idUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(idUser_KEY);
        editor.apply();
    }

    public static void clearName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(NAME_KEY);
        editor.apply();
    }

    public static void clearEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(EMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL_KEY);
        editor.apply();
    }

    public static void clearPhone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PHONE_KEY);
        editor.apply();
    }
}
