package com.example.humorme.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChuckAdapter extends  RecyclerView.Adapter<ChuckAdapter.ChuckViewHolder> {
    private ArrayList<Chuck> mChucks = new ArrayList<>();
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

    public class ChuckViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.chuck)
        TextView mChuck;
        private Context mContext;

        public ChuckViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mContext = view.getContext();
        }
        public void bindChucks(Chuck chuck){
            mChuck.setText(chuck.getValue());
        }
    }

}
