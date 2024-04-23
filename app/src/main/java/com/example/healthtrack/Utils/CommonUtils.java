package com.example.healthtrack.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {
    public static final String STEP_NUMBER_KEY = "step";

    public static String getKeyToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getStepNumber() {
        return DataLocalManager.getInstance().getWalkingStep(STEP_NUMBER_KEY);
    }

    public static void clearStepNumber() {
        DataLocalManager.clearWalkingStep(STEP_NUMBER_KEY);
    }
}