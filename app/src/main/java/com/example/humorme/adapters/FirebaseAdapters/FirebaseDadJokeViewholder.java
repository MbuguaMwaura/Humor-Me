package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.DadJoke;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.DetailsActivity;
import com.example.humorme.ui.QuotesDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseDadJokeViewholder extends RecyclerView.ViewHolder {
    View mView;
    Context mContext;
    public CardView mValueCardView;
    public TextView mSavedItem;



    public FirebaseDadJokeViewholder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();

    }

    public void bindDadJoke(DadJoke dadJoke){
        mValueCardView = (CardView) mView.findViewById(R.id.card1);
        mSavedItem = (TextView) mView.findViewById(R.id.savedItem);
        TextView quoteTextView = (TextView) mView.findViewById(R.id.savedItem);
        quoteTextView.setText(dadJoke.getJoke());
    }



}
