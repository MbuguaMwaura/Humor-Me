package com.example.humorme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.adapters.DadJokeAdapter;
import com.example.humorme.models.DadJoke;
import com.example.humorme.services.DadJokeService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DadJokeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recyclerViewDadJoke)
    RecyclerView recyclerViewDadJoke;
    @BindView(R.id.anotherBtnDadJoke)
    Button buttonAnotherDadJoke;

    ArrayList<DadJoke> mDadjokes;

    private DadJokeAdapter dadJokeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        ButterKnife.bind(this);


    getJoke();
    buttonAnotherDadJoke.setOnClickListener(this);
    }

    public void getJoke(){
        final DadJokeService dadJokeService = new DadJokeService();
        dadJokeService.findDadJoke(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mDadjokes = dadJokeService.processResults(response);

                DadJokeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dadJokeAdapter = new DadJokeAdapter(mDadjokes,getApplicationContext());
                        recyclerViewDadJoke.setAdapter(dadJokeAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DadJokeActivity.this);
                        recyclerViewDadJoke.setLayoutManager(layoutManager);
                        recyclerViewDadJoke.setHasFixedSize(true);

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAnotherDadJoke){
            getJoke();
        }
    }
}
