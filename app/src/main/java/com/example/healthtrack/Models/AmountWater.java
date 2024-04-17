package com.example.healthtrack.Models;

import java.time.LocalDateTime;

public class AmountWater {
    int amountDrinking;
    String time;

    public AmountWater(int amountDrinking) {
        this.amountDrinking = amountDrinking;
        time = String.format("%02d:%02d", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
    }

    public int getAmountDrinking() {
        return amountDrinking;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmountDrinking(int amountDrinking) {
        this.amountDrinking = amountDrinking;
    }
}
