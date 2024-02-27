package com.example.healthtrack.Utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {
    public static String getKeyToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getStepNumber() {
        return DataLocalManager.getInstance().getWalkingStep(getKeyToday());
    }
}