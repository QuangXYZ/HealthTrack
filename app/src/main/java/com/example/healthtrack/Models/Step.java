package com.example.healthtrack.Models;

public class Step {
    private String _id;
    private String idUser;
    private int numberStep;
    private int calo;
    private float distance;
    private String time;
    private String date;

    public Step() {
    }

    public Step(String _id, String idUser, int numberStep, int calo, float distance, String time, String date) {
        this._id = _id;
        this.idUser = idUser;
        this.numberStep = numberStep;
        this.calo = calo;
        this.distance = distance;
        this.time = time;
        this.date = date;
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

    public int getNumberStep() {
        return numberStep;
    }

    public void setNumberStep(int numberStep) {
        this.numberStep = numberStep;
    }

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public float getDistance() {
        return (float) distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
