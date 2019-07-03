package com.example.humorme.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.humorme.R;
import com.example.humorme.adapters.DadJokeArrayAdapter;
import com.example.humorme.models.DadJoke;
import com.example.humorme.services.DadJokeService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DadJokeActivity extends AppCompatActivity {

    ArrayList<DadJoke> mDadjokes;
    @BindView(R.id.jokeItemLV)
    ListView jokeItem;
    private ArrayAdapter adapter;
    private DadJokeArrayAdapter dadJokeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        ButterKnife.bind(this);


    getJoke();
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
                        dadJokeArrayAdapter = new DadJokeArrayAdapter(DadJokeActivity.this,android.R.layout.simple_list_item_1,mDadjokes);
                        jokeItem.setAdapter(dadJokeArrayAdapter);
                    }
                });
            }
        });
    }

}
