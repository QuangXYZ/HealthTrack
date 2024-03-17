package com.example.healthtrack.Models;

import java.io.Serializable;

public class SetGoals implements Serializable {

    private String _id;
    private String idUser;
    private int numberStepGoals;
    private int caloGoals;
    private int distanceGoals;
    private String timeGoals;

    public SetGoals() {
    }

    public SetGoals(String _id, String idUser, int numberStepGoals, int caloGoals, int distanceGoals, String timeGoals) {
        this._id = _id;
        this.idUser = idUser;
        this.numberStepGoals = numberStepGoals;
        this.caloGoals = caloGoals;
        this.distanceGoals = distanceGoals;
        this.timeGoals = timeGoals;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getTimeGoals() {
        return timeGoals;
    }

    public void setTimeGoals(String timeGoals) {
        this.timeGoals = timeGoals;
    }

    @Override
    public String toString() {
        return "SetGoals{" +
                "_id='" + _id + '\'' +
                ", idUser='" + idUser + '\'' +
                ", numberStepGoals=" + numberStepGoals +
                ", caloGoals=" + caloGoals +
                ", distanceGoals=" + distanceGoals +
                ", timeGoals=" + timeGoals +
                '}';
    }
}
