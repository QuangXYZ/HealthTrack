package com.example.healthtrack.Models;

import java.time.LocalDateTime;

public class HeartRate {
    int rate;
    String time;

    public HeartRate(int rate) {
        this.rate = rate;
        time = String.format("%02d:%02d", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
