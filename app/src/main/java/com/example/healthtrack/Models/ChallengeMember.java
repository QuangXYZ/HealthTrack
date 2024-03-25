package com.example.healthtrack.Models;

import java.io.Serializable;

public class ChallengeMember implements Serializable {
    private String userId;
    private String userName;
    private boolean accept;

    public ChallengeMember(String userID, String userName, boolean accept) {
        this.userId = userID;
        this.userName = userName;
        this.accept = accept;
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

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
