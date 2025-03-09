package com.warmer.web.entity;

import lombok.Data;

@Data
public class Rescode {
    private String res_cd;


    public void setRes_cd(String res_cd) {
        this.res_cd = res_cd;
    }

    public String getRes_cd() {
        return res_cd;
    }
}
