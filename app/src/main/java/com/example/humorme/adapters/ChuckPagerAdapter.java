package com.example.humorme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.humorme.models.Chuck;
import com.example.humorme.ui.ChuckDetailFragment;

import java.util.ArrayList;

public class ChuckPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Chuck> mChucks;

    public ChuckPagerAdapter(FragmentManager fm, ArrayList<Chuck>chucks) {
        super(fm);
        this.mChucks = chucks;
    }

    @Override
    public Fragment getItem(int i) {
        return ChuckDetailFragment.newInstance(mChucks.get(i));
    }

    @Override
    public int getCount() {
        return mChucks.size();
    }
}
