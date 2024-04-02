package com.example.healthtrack.Models;

import java.io.Serializable;
import java.util.List;

public class SmallExercises implements Serializable {
    private String _id;
    private String titleSmall;
    private String timeSmall;
    private String exercisePicture;
    private List<String> listContent;

    public SmallExercises() {
    }

    public SmallExercises(String _id, String titleSmall, String timeSmall, String exercisePicture, List<String> listContent) {
        this._id = _id;
        this.titleSmall = titleSmall;
        this.timeSmall = timeSmall;
        this.exercisePicture = exercisePicture;
        this.listContent = listContent;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitleSmall() {
        return titleSmall;
    }

    public void setTitleSmall(String titleSmall) {
        this.titleSmall = titleSmall;
    }

    public String getTimeSmall() {
        return timeSmall;
    }

    public void setTimeSmall(String timeSmall) {
        this.timeSmall = timeSmall;
    }

    public String getExercisePicture() {
        return exercisePicture;
    }

    public void setExercisePicture(String exercisePicture) {
        this.exercisePicture = exercisePicture;
    }

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
        this.listContent = listContent;
    }
}
