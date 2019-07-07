package com.example.humorme.adapters.FirebaseAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.humorme.R;
import com.example.humorme.models.Quotes;
import com.example.humorme.util.ItemTouchHelperAdapter;
import com.example.humorme.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class FirebaseTrumpListAdapter extends FirebaseRecyclerAdapter<Quotes, FirebaseTrumpViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;


    public FirebaseTrumpListAdapter(FirebaseRecyclerOptions<Quotes> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseTrumpViewHolder holder, int position, @NonNull Quotes model) {
        holder.bindTrump(model);
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
    public FirebaseTrumpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
        return new FirebaseTrumpViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        return false;
    }

    @Override
    public void onItemDismiss(int position){

    }
}
