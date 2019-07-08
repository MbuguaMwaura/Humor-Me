package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class Quotes {
    String index;
    String quote;
    private String pushId;

    public Quotes(){}

    public Quotes(String quote){
        this.quote = quote;
        this.index = "not_specified";
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
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
