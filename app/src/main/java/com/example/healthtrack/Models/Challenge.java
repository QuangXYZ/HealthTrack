package com.example.healthtrack.Models;

import com.example.healthtrack.Utils.DataLocalManager;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Challenge implements Serializable {
    private String _id;
    private String name;
    private String description;
    private String dateStart;
    private String dateEnd;
    private int target;
    private int exp;
    private List<ChallengeMember> listMember;
    private List<Record> userRecords;
    private String access;

    public Challenge(String name, String description, String dateStart, int target) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.target = target;
        this.listMember = new ArrayList<>();
        this.userRecords = new ArrayList<>();
        User user = DataLocalManager.getUser();
        this.listMember.add(new ChallengeMember(user.get_id(), user.getName(), true));
        this.userRecords.add(new Record(user.get_id(), user.getName()));
        access = "Private";
    }

    public Challenge(String _id, String name, String description, String dateStart, String dateEnd, int target, int exp, List<ChallengeMember> listMember, List<Record> userRecords, String access) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.target = target;
        this.exp = exp;
        this.listMember = listMember;
        this.userRecords = userRecords;
        this.access = access;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public List<ChallengeMember> getListMember() {
        return listMember;
    }

    public void setListMember(List<ChallengeMember> listMember) {
        this.listMember = listMember;
    }

    public List<Record> getUserRecords() {
        return userRecords;
    }

    public void setUserRecords(List<Record> userRecords) {
        this.userRecords = userRecords;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", target=" + target +
                ", exp=" + exp +
                ", listMember=" + listMember +
                ", userRecords=" + userRecords +
                ", access='" + access + '\'' +
                '}';
    }
}
