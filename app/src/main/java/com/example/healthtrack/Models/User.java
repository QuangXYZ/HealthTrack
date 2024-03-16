package com.example.healthtrack.Models;

import java.util.Date;
import java.util.List;

public class User {
    String _id;
    String name;

    String password;
    String email;
    String gender;
    String token;
    List<String> badges;
    List<String> friends;
    String dateOfBirth;
    List<String> idChallenges;

    public User(String _id, String name, String password, String email, String gender, String token, List<String> badges, List<String> friends, String dateOfBirth, List<String> idChallenges) {
        this._id = _id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.token = token;
        this.badges = badges;
        this.friends = friends;
        this.dateOfBirth = dateOfBirth;
        this.idChallenges = idChallenges;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getIdChallenges() {
        return idChallenges;
    }

    public void setIdChallenges(List<String> idChallenges) {
        this.idChallenges = idChallenges;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", badges=" + badges +
                ", friends=" + friends +
                ", dateOfBirth=" + dateOfBirth +
                ", idChallenges=" + idChallenges +
                '}';
    }
}