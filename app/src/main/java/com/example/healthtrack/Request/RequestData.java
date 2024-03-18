package com.example.healthtrack.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestData {

    public static JSONObject generateData(int numberStepGoals, int caloGoals, int distanceGoals, String timeGoals) {
        JSONObject newData = new JSONObject();
        try {
            newData.put("numberStepGoals", numberStepGoals);
            newData.put("caloGoals", caloGoals);
            newData.put("distanceGoals", distanceGoals);
            newData.put("timeGoals", timeGoals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("newData", newData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestData;
    }
}
