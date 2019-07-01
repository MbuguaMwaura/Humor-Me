package com.example.humorme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.models.Quotes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private ArrayList<Quotes> mQuotes = new ArrayList<>();
    private Context mContext;

    public QuotesAdapter (ArrayList<Quotes> quotes, Context context){
        mContext = context;
        mQuotes = quotes;
    }

    @Override
    public void onBindViewHolder(QuotesAdapter.QuotesViewHolder holder, int position){
        holder.bindQuotes(mQuotes.get(position));
    }

    @Override
    public QuotesAdapter.QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item,parent,false);
        QuotesViewHolder viewHolder = new QuotesViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount(){
        return mQuotes.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.quote)
        TextView mQuote;
        private Context context;

        public QuotesViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
        }

        public void bindQuotes(Quotes quotes){
            mQuote.setText(quotes.getmQuote());

        }
    }
}
