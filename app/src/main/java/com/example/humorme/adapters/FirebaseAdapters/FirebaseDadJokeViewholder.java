package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.DadJoke;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.DetailsActivity;
import com.example.humorme.ui.QuotesDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseDadJokeViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;



    public FirebaseDadJokeViewholder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindDadJoke(DadJoke dadJoke){
        TextView quoteTextView = (TextView) mView.findViewById(R.id.dadJoke);
        quoteTextView.setText(dadJoke.getJoke());
    }


    @Override
    public void onClick(View v) {
        final ArrayList<DadJoke> dadJokes = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DADJOKE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    dadJokes.add(snapshot.getValue(DadJoke.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("dadJoke", Parcels.wrap(dadJokes));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
