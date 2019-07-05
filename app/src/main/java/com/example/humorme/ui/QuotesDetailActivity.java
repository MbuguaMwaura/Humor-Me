package com.example.humorme.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.humorme.R;
import com.example.humorme.adapters.QuotesPagerAdapter;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.Quotes;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotesDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private QuotesPagerAdapter quotesPagerAdapter;
    ArrayList<Quotes> mQuotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_detail);
        ButterKnife.bind(this);

        mQuotes  = Parcels.unwrap(getIntent().getParcelableExtra("quotes"));
        quotesPagerAdapter = new QuotesPagerAdapter(getSupportFragmentManager(),mQuotes);
        int startingPosition = getIntent().getIntExtra("position",0);
        mViewPager.setAdapter(quotesPagerAdapter);
        mViewPager.setCurrentItem(startingPosition,false);

    }
}
