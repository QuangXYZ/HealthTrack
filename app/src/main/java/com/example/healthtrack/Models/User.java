package com.example.healthtrack.Models;

import java.util.Date;
import java.util.List;

public class User {
    private String _id;
    private String name;
    private String password;
    private String email;
    private String profilePicture;
    private String gender;
    private String token;
    private List<String> badges;
    private List<String> friends;
    private String dateOfBirth;
    private List<String> idChallenges;
    private int level;
    private int exp;

    public User(String email, String name, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String _id, String name, String password, String email, String profilePicture, String gender, String token, List<String> badges, List<String> friends, String dateOfBirth, List<String> idChallenges, int level, int exp) {
        this._id = _id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.gender = gender;
        this.token = token;
        this.badges = badges;
        this.friends = friends;
        this.dateOfBirth = dateOfBirth;
        this.idChallenges = idChallenges;
        this.level = level;
        this.exp = exp;
    }


    public String getProfilePicture() {
        return profilePicture;
    }

    public void addChallengeId(String challengeId) {
        this.idChallenges.add(challengeId);
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", token='" + token + '\'' +
                ", badges=" + badges +
                ", friends=" + friends +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", idChallenges=" + idChallenges +
                ", level=" + level +
                ", exp=" + exp +
                '}';
    }
}