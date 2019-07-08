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
import com.example.humorme.ui.ChuckDetailActivity;
import com.example.humorme.util.ItemTouchHelperAdapter;
import com.example.humorme.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseChuckListAdapter extends FirebaseRecyclerAdapter<Chuck, FirebaseChuckViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Chuck> mChuck = new ArrayList<>();

    public FirebaseChuckListAdapter(FirebaseRecyclerOptions<Chuck> options , Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mChuck.add(dataSnapshot.getValue(Chuck.class));
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

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseChuckViewHolder holder, int position, @NonNull Chuck model) {
        holder.bindChuck(model);
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
                Intent intent = new Intent(mContext, ChuckDetailActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.putExtra("chuck", Parcels.wrap(mChuck));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseChuckViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
        return new FirebaseChuckViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mChuck,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mChuck.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Chuck chuck: mChuck) {
            int index = mChuck.indexOf(chuck);
            DatabaseReference ref = getRef(index);
            chuck.setIndex(Integer.toString(index));
            ref.setValue(chuck);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening(); mRef.removeEventListener(mChildEventListener);
    }

}


