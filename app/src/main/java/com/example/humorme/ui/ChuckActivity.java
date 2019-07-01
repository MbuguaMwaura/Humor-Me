package com.example.humorme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.humorme.R;
import com.example.humorme.adapters.ChuckAdapter;
import com.example.humorme.models.Chuck;
import com.example.humorme.services.ChuckService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChuckActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<Chuck> chucks;
    private ChuckAdapter chuckAdapter;
    @BindView(R.id.anotherBtnChuck)
    Button anotherBtnChuck;
    @BindView(R.id.recyclerViewChuck)
    RecyclerView recyclerViewChuck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck);
        ButterKnife.bind(this);

        getChuck();
        anotherBtnChuck.setOnClickListener(this);
    }
    private void getChuck(){
        final ChuckService chuckService = new ChuckService();
        chuckService.findRandomChuck(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                chucks = chuckService.processResults(response);

                ChuckActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chuckAdapter = new ChuckAdapter(chucks,getApplicationContext());
                        recyclerViewChuck.setAdapter(chuckAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChuckActivity.this);
                        recyclerViewChuck.setLayoutManager(layoutManager);
                        recyclerViewChuck.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == anotherBtnChuck){
            getChuck();
        }
    }
}
