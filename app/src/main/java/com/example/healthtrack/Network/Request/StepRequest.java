package com.example.healthtrack.Network.Request;

public class StepRequest {
    private String idUser;
    private int numberStep;
    private int weight;
    private String date;

    public StepRequest(String idUser, int numberStep, int weight, String date) {
        this.idUser = idUser;
        this.numberStep = numberStep;
        this.weight = weight;
        this.date = date;
    }

    public StepRequest() {

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
