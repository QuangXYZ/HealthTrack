package com.example.healthtrack.Request;

public class UpdateSetGoalsRequest {
    private int numberStepGoals;
    private int caloGoals;
    private int distanceGoals;
    private int timeGoals;

    public UpdateSetGoalsRequest(int numberStepGoals, int caloGoals, int distanceGoals, int timeGoals) {
        this.numberStepGoals = numberStepGoals;
        this.caloGoals = caloGoals;
        this.distanceGoals = distanceGoals;
        this.timeGoals = timeGoals;
    }

    public UpdateSetGoalsRequest() {
    }

    public int getNumberStepGoals() {
        return numberStepGoals;
    }

    public void setNumberStepGoals(int numberStepGoals) {
        this.numberStepGoals = numberStepGoals;
    }

    public int getCaloGoals() {
        return caloGoals;
    }

    public void setCaloGoals(int caloGoals) {
        this.caloGoals = caloGoals;
    }

    public int getDistanceGoals() {
        return distanceGoals;
    }

    public void setDistanceGoals(int distanceGoals) {
        this.distanceGoals = distanceGoals;
    }

    public int getTimeGoals() {
        return timeGoals;
    }

    public void setTimeGoals(int timeGoals) {
        this.timeGoals = timeGoals;
    }
}
