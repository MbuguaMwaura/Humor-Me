package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class Quotes {
    String quote;
    private String pushId;

    public Quotes(){}

    public Quotes(String quote){
        this.quote = quote;
    }



    public String getQuote() {
        return quote;
    }


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
