package com.example.humorme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.humorme.R;
import com.example.humorme.adapters.QuotesAdapter;
import com.example.humorme.models.Quotes;
import com.example.humorme.services.TronaldDumpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

                        public class QuoteActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<Quotes> mQuotes;
    private QuotesAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.anotherBtn) Button anotherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        ButterKnife.bind(this);

        getRandom();

        anotherBtn.setOnClickListener(this);


    }
    private void getRandom(){
        final TronaldDumpService tronaldDumpService = new TronaldDumpService();

        tronaldDumpService.findQuotes(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mQuotes = tronaldDumpService.processResults(response);

                QuoteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new QuotesAdapter(mQuotes,getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(QuoteActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == anotherBtn){
            getRandom();
        }
    }
}
