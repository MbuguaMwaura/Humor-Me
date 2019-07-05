package com.example.humorme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.humorme.models.Quotes;
import com.example.humorme.ui.QuotesDetailFragment;

import java.util.ArrayList;

public class QuotesPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Quotes> mQuotes;


    public QuotesPagerAdapter(FragmentManager fm, ArrayList<Quotes> quotes) {
        super(fm);
        this.mQuotes = quotes;
    }

    @Override
    public Fragment getItem(int i) {
        return QuotesDetailFragment.newInstance(mQuotes.get(i));
    }

    @Override
    public int getCount() {
        return mQuotes.size();
    }
}
