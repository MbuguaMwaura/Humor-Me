package com.example.humorme.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.humorme.R;
import com.example.humorme.adapters.ChuckPagerAdapter;
import com.example.humorme.models.Chuck;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChuckDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private ChuckPagerAdapter chuckPagerAdapter;
    ArrayList<Chuck> mChucks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck_detail);
        ButterKnife.bind(this);

        mChucks = Parcels.unwrap(getIntent().getParcelableExtra("chuck"));
        chuckPagerAdapter = new ChuckPagerAdapter(getSupportFragmentManager(), mChucks);
        int startingPosition = getIntent().getIntExtra("position",0);

        mViewPager.setAdapter(chuckPagerAdapter);
        mViewPager.setCurrentItem(startingPosition,false);
    }
}
