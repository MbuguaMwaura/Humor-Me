package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class DadJoke {

    String index;
    String joke;
    private String pushId;

    public DadJoke() {}


    public DadJoke(String joke) {
        this.joke = joke;
        this.index = "not_specified";
    }


    public String getJoke() {
        return joke;
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
