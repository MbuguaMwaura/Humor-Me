package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.DadJoke;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.DetailsActivity;
import com.example.humorme.util.ItemTouchHelperAdapter;
import com.example.humorme.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseDadJokeListAdapter extends FirebaseRecyclerAdapter<DadJoke, FirebaseDadJokeViewholder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ArrayList<DadJoke> mDadJoke = new ArrayList<>();
    private ChildEventListener mChildEventListener;

    public FirebaseDadJokeListAdapter(@NonNull FirebaseRecyclerOptions<DadJoke> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mDadJoke.add(dataSnapshot.getValue(DadJoke.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setIndexInFirebase() {
        for (DadJoke dadJoke : mDadJoke) {
            int index = mDadJoke.indexOf(dadJoke);
            DatabaseReference ref = getRef(index);
            dadJoke.setIndex(Integer.toString(index));
            ref.setValue(dadJoke);
        }
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDadJoke,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mDadJoke.remove(position);
        getRef(position).removeValue();
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseDadJokeViewholder holder, int position, @NonNull DadJoke model) {
        holder.bindDadJoke(model);
        holder.mSavedItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("position",holder.getAdapterPosition());
                intent.putExtra("dadJoke", Parcels.wrap(mDadJoke));
                mContext.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public FirebaseDadJokeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
        return new FirebaseDadJokeViewholder(view);
    }
    @Override
    public void stopListening() {
        super.stopListening(); mRef.removeEventListener(mChildEventListener);
    }
}
