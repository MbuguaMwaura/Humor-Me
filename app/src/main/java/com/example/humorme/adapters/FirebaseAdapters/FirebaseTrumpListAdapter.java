package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.QuotesDetailActivity;
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

public class FirebaseTrumpListAdapter extends FirebaseRecyclerAdapter<Quotes, FirebaseTrumpViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Quotes> mQuotes = new ArrayList<>();


    public FirebaseTrumpListAdapter(FirebaseRecyclerOptions<Quotes> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mQuotes.add(dataSnapshot.getValue(Quotes.class));
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
    protected void onBindViewHolder(@NonNull final FirebaseTrumpViewHolder holder, int position, @NonNull Quotes model) {
        holder.bindTrump(model);
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
                Intent intent = new Intent(mContext, QuotesDetailActivity.class);
                intent.putExtra("position",holder.getAdapterPosition());
                intent.putExtra("quote", Parcels.wrap(mQuotes));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseTrumpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
        return new FirebaseTrumpViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mQuotes,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Quotes quotes : mQuotes) {
            int index = mQuotes.indexOf(quotes);
            DatabaseReference ref = getRef(index);
            quotes.setIndex(Integer.toString(index));
            ref.setValue(quotes);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening(); mRef.removeEventListener(mChildEventListener);
    }
}
