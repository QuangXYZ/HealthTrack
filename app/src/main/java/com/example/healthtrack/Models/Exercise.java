package com.example.healthtrack.Models;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable {
    private String _id;
    private String title;
    private int time;
    private float calo;
    private List<SmallExercises> smallExercises;

    public Exercise() {
    }

    public Exercise(String _id, String title, int time, float calo, List<SmallExercises> smallExercises) {
        this._id = _id;
        this.title = title;
        this.time = time;
        this.calo = calo;
        this.smallExercises = smallExercises;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public List<SmallExercises> getSmallExercises() {
        return smallExercises;
    }

    public void setSmallExercises(List<SmallExercises> smallExercises) {
        this.smallExercises = smallExercises;
    }
}
