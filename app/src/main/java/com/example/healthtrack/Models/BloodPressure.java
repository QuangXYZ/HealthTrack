package com.example.healthtrack.Models;

public class BloodPressure {
    private int sys;
    private int dia;

    public BloodPressure(int sys, int dia) {
        this.sys = sys;
        this.dia = dia;
    }

    public int getSys() {
        return sys;
    }

    public void setSys(int sys) {
        this.sys = sys;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
