package com.example.humorme.models;

import org.parceler.Parcel;

@Parcel
public class Quotes {
    String quote;

    public Quotes(){}

    public Quotes(String quote){
        this.quote = quote;
    }



    public String getmQuote() {
        return quote;
    }
}
