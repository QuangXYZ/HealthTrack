package com.example.healthtrack.Utils;

import android.content.Context;


import com.example.healthtrack.Models.AmountWater;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Models.HeartRate;
import com.example.healthtrack.Models.User;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_USER = "PREF_USER";
    private static final String PREF_HEALTH = "PREF_HEALTH";

    private static final String TOKEN_USER = "TOKEN_USER";
    private static final String TEMP_STEP = "TEMP_STEP";

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


    public static void setTempStep(int value) {
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(CommonUtils.getKeyToday() + "TENP_STEP", value);

    }

    public static int getTempStep() {
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(CommonUtils.getKeyToday() + "TENP_STEP");
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_USER, strJsonUser);

    }

    public static User getUser() {
        String strUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(strUser, User.class);
        return user;
    }

    public static void setHealthActivity(HealthActivity healthActivity) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(healthActivity);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CommonUtils.getKeyToday() + "HealthActivity", strJsonUser);

    }

    public static HealthActivity getHealthActivity() {
        String strUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(CommonUtils.getKeyToday() + "HealthActivity");
        Gson gson = new Gson();
        HealthActivity healthActivity = gson.fromJson(strUser, HealthActivity.class);
        return healthActivity;
    }

    public static void saveAmountDrinkingList(List<AmountWater> amountWaters) {
        Gson gson = new Gson();
        String json = gson.toJson(amountWaters);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CommonUtils.getKeyToday() + "WaterDrinking", json);

    }

    public static List<AmountWater> getAmountDrinkingList() {
        Gson gson = new Gson();
        String amountDrinking = DataLocalManager.getInstance().mySharedPreferences.getStringValue(CommonUtils.getKeyToday() + "WaterDrinking");

        Type type = new TypeToken<ArrayList<AmountWater>>() {
        }.getType();
        return gson.fromJson(amountDrinking, type);
    }

    public static void setHeartRateList(List<HeartRate> heartRates) {
        Gson gson = new Gson();
        String json = gson.toJson(heartRates);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CommonUtils.getKeyToday() + "heartRates", json);

    }

    public static List<HeartRate> getHeartRateList() {
        Gson gson = new Gson();
        String heartRates = DataLocalManager.getInstance().mySharedPreferences.getStringValue(CommonUtils.getKeyToday() + "heartRates");

        Type type = new TypeToken<ArrayList<HeartRate>>() {
        }.getType();
        return gson.fromJson(heartRates, type);
    }

}
