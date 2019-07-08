package com.example.humorme.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Chuck {

    String index;
    String value;
    private String pushId;

    public Chuck(){}

    public Chuck(String value) {
        this.value = value;
        this.index = "not_specified";
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


}
