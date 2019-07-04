package com.example.humorme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.ui.ChuckActivity;
import com.example.humorme.ui.ChuckDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChuckAdapter extends  RecyclerView.Adapter<ChuckAdapter.ChuckViewHolder> {
    private ArrayList<Chuck> mChucks;
    private Context context;

    public ChuckAdapter (ArrayList<Chuck> chucks, Context context){
        context = context;
        mChucks = chucks;
    }


    @Override
    public ChuckAdapter.ChuckViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chuck_item,viewGroup,false);
        ChuckViewHolder viewHolder = new ChuckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ChuckAdapter.ChuckViewHolder chuckViewHolder, int i) {
        chuckViewHolder.bindChucks(mChucks.get(i));
    }

    @Override
    public int getItemCount() {
        return mChucks.size();
    }

    public class ChuckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.chuck)
        TextView mChuck;
        private Context mContext;

        public ChuckViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mContext = view.getContext();
            view.setOnClickListener(this);
        }
        public void bindChucks(Chuck chuck){
            mChuck.setText(chuck.getValue());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ChuckDetailActivity.class);
            intent.putExtra("position",itemPosition);
            intent.putExtra("chuck", Parcels.wrap(mChucks));
            mContext.startActivity(intent);
        }
    }

}
