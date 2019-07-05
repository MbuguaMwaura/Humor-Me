package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class DadJoke {


    String joke;
    private String pushId;

    public DadJoke() {}


    public DadJoke(String joke) {
        this.joke = joke;
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

}
