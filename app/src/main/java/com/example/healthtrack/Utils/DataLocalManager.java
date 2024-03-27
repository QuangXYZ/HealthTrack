package com.example.healthtrack.Utils;

import android.content.Context;


import com.example.healthtrack.Models.User;
import com.google.gson.Gson;

public class DataLocalManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_USER = "PREF_USER";

    private static final String TOKEN_USER = "TOKEN_USER";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();

        }
        return instance;
    }

    public static void setWalkingStep(String key, int value) {
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(key, value);

    }
    public static int getWalkingStep(String key) {
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(key);
    }

    public static void clearWalkingStep(String key) {
        DataLocalManager.getInstance().mySharedPreferences.clearValue(key);
    }

    public static void setToken(String token) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TOKEN_USER, token);

    }
    public static String getToken() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TOKEN_USER);
    }




    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_USER,strJsonUser);

    }

    public static User getUser(){
        String strUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(strUser,User.class);
        return user;
    }

}
