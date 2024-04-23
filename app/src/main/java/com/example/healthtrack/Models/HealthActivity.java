package com.example.healthtrack.Models;

import com.example.healthtrack.Utils.DataLocalManager;

import java.time.LocalDate;

public class HealthActivity {
    String _id;
    String idUser;
    String date;
    BloodPressure bloodPressure;
    HeartRate hearthRate;
    BodyComposition bodyComposition;
    AmountWater amountWater;

    public HealthActivity(String idUser, String date, BloodPressure bloodPressure, HeartRate hearthRate, BodyComposition bodyComposition, AmountWater amountWater) {
        this.idUser = idUser;
        this.date = date;
        this.bloodPressure = bloodPressure;
        this.hearthRate = hearthRate;
        this.bodyComposition = bodyComposition;
        this.amountWater = amountWater;
    }

    public HealthActivity() {
        this.idUser = DataLocalManager.getUser().get_id();
        this.date = String.valueOf(LocalDate.now());
        this.bloodPressure = new BloodPressure(0, 0);
        this.hearthRate = new HeartRate(0);
        this.bodyComposition = new BodyComposition(0, 0);
        this.amountWater = new AmountWater(0);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(BloodPressure bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public HeartRate getHearthRate() {
        return hearthRate;
    }

    public void setHearthRate(HeartRate hearthRate) {
        this.hearthRate = hearthRate;
    }

    public BodyComposition getBodyComposition() {
        return bodyComposition;
    }

    public void setBodyComposition(BodyComposition bodyComposition) {
        this.bodyComposition = bodyComposition;
    }

    public AmountWater getAmountWater() {
        return amountWater;
    }

    public void setAmountWater(AmountWater amountWater) {
        this.amountWater = amountWater;
    }
}
