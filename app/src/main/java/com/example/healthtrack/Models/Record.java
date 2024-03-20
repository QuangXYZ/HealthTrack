package com.example.healthtrack.Models;

public class Record {
    private String userId;
    private String userName;
    private int stepTotal;

    public Record(String userID, String userName) {
        this.userId = userID;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStepTotal() {
        return stepTotal;
    }

    public void setStepTotal(int stepTotal) {
        this.stepTotal = stepTotal;
    }
}
