package com.example.healthtrack.Network.Request;

public class LeaveChallengeRequest {
    String userId;
    String idChallenge;

    public LeaveChallengeRequest(String userId, String idChallenge) {
        this.userId = userId;
        this.idChallenge = idChallenge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(String idChallenge) {
        this.idChallenge = idChallenge;
    }

    @Override
    public String toString() {
        return "LeaveChallengeRequest{" +
                "userId='" + userId + '\'' +
                ", idChallenge='" + idChallenge + '\'' +
                '}';
    }
}
