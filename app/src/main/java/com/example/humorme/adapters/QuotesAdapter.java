package com.example.humorme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.QuotesDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    private ArrayList<Quotes> mQuotes;
    private Context mContext;

    public QuotesAdapter (ArrayList<Quotes> quotes, Context context){
        mContext = context;
        mQuotes = quotes;
    }


    @Override
    public QuotesAdapter.QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item,parent,false);
        QuotesViewHolder viewHolder = new QuotesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuotesAdapter.QuotesViewHolder holder, int position){
        holder.bindQuotes(mQuotes.get(position));
    }



    @Override
    public int getItemCount(){
        return mQuotes.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.quote)
        TextView mQuote;
        private Context context;

        public QuotesViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindQuotes(Quotes quotes){
            mQuote.setText(quotes.getmQuote());

        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(context, QuotesDetailActivity.class);
            intent.putExtra("position",itemPosition);
            intent.putExtra("quotes", Parcels.wrap(mQuotes));
            context.startActivity(intent);
        }
    }
}
