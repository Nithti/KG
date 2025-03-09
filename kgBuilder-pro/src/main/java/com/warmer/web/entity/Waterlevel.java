package com.warmer.web.entity;

import lombok.Data;

@Data
public class Waterlevel {
    private String st_cd;
    private float rz;
    private String tm;

    public void setRz(float rz) {
        this.rz = rz;
    }

    public float getRz() {
        return rz;
    }

    public void setSt_cd(String st_cd) {
        this.st_cd = st_cd;
    }

    public String getSt_cd() {
        return st_cd;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTm() {
        return tm;
    }

    @Override
    public String toString() {
        return "location{" +
                "st_cd='" + st_cd + '\'' +
                ", rz='" + rz + '\'' +
                ", tm='" + tm + '\'' +
                '}';
    }
}
