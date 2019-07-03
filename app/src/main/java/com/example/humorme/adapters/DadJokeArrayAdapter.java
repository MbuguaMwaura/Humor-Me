package com.example.humorme.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.humorme.models.DadJoke;

import java.util.ArrayList;

public class DadJokeArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<DadJoke> mDadJokes;


    public DadJokeArrayAdapter(Context context, int resource, ArrayList<DadJoke>dadJokes){
        super(context,resource);
        this.mContext = context;
        this.mDadJokes = dadJokes;
    }

    @Override
    public Object getItem(int position){
        DadJoke dadJoke = mDadJokes.get(position);
        return dadJoke.getJoke();
    }

    @Override
    public int getCount() {
        return mDadJokes.size();
    }
}
