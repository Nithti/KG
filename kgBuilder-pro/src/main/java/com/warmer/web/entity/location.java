package com.warmer.web.entity;

import lombok.Data;

@Data
public class location {
    private String guid;
    private String ad_cd;
    private String ad_nm;
    private String pcode;
    private String ad_pre_nm;

    @Override
    public String toString() {
        return "location{" +
                "guid='" + guid + '\'' +
                ", ad_cd='" + ad_cd + '\'' +
                ", ad_nm='" + ad_nm + '\'' +
                ", pcode='" + pcode + '\'' +
                ", ad_pre_nm='" + ad_pre_nm + '\'' +
                '}';
    }
}
