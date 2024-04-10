package com.example.healthtrack.Network.Respone;

import java.util.List;

public class SetGoalsResponse<T> {

    private String message;
    private List<T> data;

    public SetGoalsResponse(String message, List<T> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
