package com.example.healthtrack.Network.Respone;

import com.google.gson.annotations.SerializedName;

public class LoginBodyResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginBodyResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
