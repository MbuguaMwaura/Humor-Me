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
import com.example.humorme.models.Chuck;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.ChuckDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseChuckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;
    public CardView mValueCardView;

    public FirebaseChuckViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindChuck(Chuck chuck){
        mValueCardView = (CardView) mView.findViewById(R.id.card1);
        TextView quoteTextView = (TextView) mView.findViewById(R.id.savedItem);
        quoteTextView.setText(chuck.getValue());
    }


    @Override
    public void onClick(View v) {
        final ArrayList<Chuck> chucks = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHUCK).child(uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chucks.add(snapshot.getValue(Chuck.class));

                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, ChuckDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("chuck", Parcels.wrap(chucks));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
