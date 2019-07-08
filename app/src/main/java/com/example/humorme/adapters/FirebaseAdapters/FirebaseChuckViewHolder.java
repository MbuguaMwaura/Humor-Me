package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.ChuckDetailActivity;
import com.example.humorme.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseChuckViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    public CardView mValueCardView;
    public TextView mSavedItem;

    public FirebaseChuckViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindChuck(Chuck chuck){
        mValueCardView = (CardView) mView.findViewById(R.id.card1);
        mSavedItem = (TextView) mView.findViewById(R.id.savedItem);
        TextView quoteTextView = (TextView) mView.findViewById(R.id.savedItem);
        quoteTextView.setText(chuck.getValue());
    }


    @Override
    public void onItemSelected() {
        Log.d("Animation", "onItemSelected");
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.7f)
                .scaleY(0.7f)
                .setDuration(300);
    }

    @Override
    public void onItemClear() {
        Log.d("Animation", "onItemClear");
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }

}
