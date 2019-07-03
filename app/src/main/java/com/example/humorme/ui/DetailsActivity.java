package com.example.humorme.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.humorme.R;
import com.example.humorme.adapters.DadJokePagerAdapter;
import com.example.humorme.models.DadJoke;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private DadJokePagerAdapter dadJokePagerAdapter;
    ArrayList<DadJoke> mDadJokes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        mDadJokes = Parcels.unwrap(getIntent().getParcelableExtra("dadJoke"));
        int startingPosition = getIntent().getIntExtra("position",0);

        dadJokePagerAdapter = new DadJokePagerAdapter(getSupportFragmentManager(), mDadJokes);

        mViewPager.setAdapter(dadJokePagerAdapter);
        mViewPager.setCurrentItem(startingPosition);

    }
}
