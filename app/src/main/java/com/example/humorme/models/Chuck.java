package com.example.humorme.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Chuck {


    String value;

    public Chuck(){}

    public Chuck(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
