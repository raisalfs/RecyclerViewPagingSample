package com.rafslab.pagination.model;

import java.io.Serializable;

public class Data implements Serializable {
    private String title;
    private String desc;
    private String profile;

    public Data(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
