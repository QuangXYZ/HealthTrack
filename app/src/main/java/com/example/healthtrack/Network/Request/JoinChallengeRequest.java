package com.example.healthtrack.Network.Request;

import com.example.healthtrack.Models.User;
import com.example.healthtrack.Utils.DataLocalManager;

public class JoinChallengeRequest {
    String userId;
    String userName;
    String idChallenge;

    public JoinChallengeRequest(String idChallenge) {
        User user = DataLocalManager.getUser();
        this.userId = user.get_id();
        this.userName = user.getName();
        this.idChallenge = idChallenge;
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

    public String getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(String idChallenge) {
        this.idChallenge = idChallenge;
    }
}
