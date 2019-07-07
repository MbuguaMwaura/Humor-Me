package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.humorme.R;
import com.example.humorme.models.DadJoke;
import com.example.humorme.models.Quotes;
import com.example.humorme.util.ItemTouchHelperAdapter;
import com.example.humorme.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class FirebaseDadJokeListAdapter extends FirebaseRecyclerAdapter<DadJoke, FirebaseDadJokeViewholder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseDadJokeListAdapter(@NonNull FirebaseRecyclerOptions<DadJoke> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseDadJokeViewholder holder, int position, @NonNull DadJoke model) {
        holder.bindDadJoke(model);
        holder.mValueCardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @NonNull
    @Override
    public FirebaseDadJokeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
        return new FirebaseDadJokeViewholder(view);
    }
}
