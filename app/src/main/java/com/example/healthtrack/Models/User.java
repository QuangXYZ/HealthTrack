package com.example.healthtrack.Models;

import java.util.Date;
import java.util.List;

public class User {
    String name;

    String password;
    String email;
    String gender;
    List<String> badges;
    List<String> friends;
    Date dateOfBirth;
    List<String> idChallenges;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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