package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class DadJoke {


    String joke;

    public DadJoke() {}


    public DadJoke(String joke) {
        this.joke = joke;
    }


    public String getJoke() {
        return joke;
    }


}
