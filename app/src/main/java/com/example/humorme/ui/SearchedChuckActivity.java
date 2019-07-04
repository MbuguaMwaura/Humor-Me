package com.example.humorme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchedChuckActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewChuckSearched)
    RecyclerView searchedChuckRV;
    private ArrayList<Chuck> mChucks;

    private Chuck chuck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_chuck);
        ButterKnife.bind(this);


    }

}
