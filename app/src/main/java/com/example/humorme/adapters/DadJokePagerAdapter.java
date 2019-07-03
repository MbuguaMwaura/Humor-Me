package com.example.humorme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.humorme.models.DadJoke;
import com.example.humorme.ui.DetailsFragment;

import java.util.ArrayList;

public class DadJokePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<DadJoke> mDadjokes;

    public DadJokePagerAdapter(FragmentManager fm, ArrayList<DadJoke> dadJokes) {
        super(fm);
        this.mDadjokes = dadJokes;
    }

    @Override
    public Fragment getItem(int i) {
        return DetailsFragment.newInstance(mDadjokes.get(i));
    }

    @Override
    public int getCount() {
        return mDadjokes.size();
    }

}
