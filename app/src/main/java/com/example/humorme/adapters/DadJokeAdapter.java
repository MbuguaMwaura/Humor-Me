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
import com.example.humorme.models.DadJoke;
import com.example.humorme.ui.DetailsActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DadJokeAdapter extends RecyclerView.Adapter<DadJokeAdapter.DadJokeViewHolder> {
    private ArrayList<DadJoke> mDadJokes;
    private Context mContext;

    public DadJokeAdapter(ArrayList<DadJoke> dadJokes, Context context){
        mContext = context;
        mDadJokes = dadJokes;
    }

    @NonNull
    @Override
    public DadJokeAdapter.DadJokeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dad_joke_item,viewGroup,false);
        DadJokeViewHolder dadJokeViewHolder = new DadJokeViewHolder(view);
        return dadJokeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DadJokeAdapter.DadJokeViewHolder dadJokeViewHolder, int i) {
            dadJokeViewHolder.bindDadJokes(mDadJokes.get(i));
    }

    @Override
    public int getItemCount() {
        return mDadJokes.size();
    }

    public class DadJokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.dadJoke)
        TextView mDadJoke;
        private Context mContext;

        public DadJokeViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindDadJokes(DadJoke dadJoke) {
            mDadJoke.setText(dadJoke.getJoke());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("dadJoke", Parcels.wrap(mDadJokes));
            mContext.startActivity(intent);
        }
    }
}
