package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class DadJoke {


    private String joke;

    public DadJoke(String joke) {
        this.joke = joke;
    }

    public DadJoke() {
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
