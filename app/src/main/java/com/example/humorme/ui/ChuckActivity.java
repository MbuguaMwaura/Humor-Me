package com.example.humorme.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.humorme.R;
import com.example.humorme.adapters.ChuckAdapter;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.DadJoke;
import com.example.humorme.services.ChuckService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

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


    private Chuck chuck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck);
        ButterKnife.bind(this);

        getChuck();



        anotherBtnChuck.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chuck_search, menu);
        ButterKnife.bind(this);

        MenuItem menuItem  = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getSearchChucks(query);
                anotherBtnChuck.setVisibility(searchView.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Chuck> getSearchChucks(String query){
        final ChuckService chuckService = new ChuckService();
        chuckService.findSearchedChucks(query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                chucks = chuckService.processResultsSearched(response);
                ChuckActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chuckAdapter = new ChuckAdapter(chucks,getApplicationContext());
                        recyclerViewChuck.setAdapter(chuckAdapter);
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ChuckActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewChuck.setLayoutManager(horizontalLayoutManagaer);
                        recyclerViewChuck.setHasFixedSize(true);

                        if (chucks.size() == 0 ){
                            Toast.makeText(ChuckActivity.this, "Search for another term",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ChuckActivity.this, "Swipe for more -->",Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        });
        return chucks;
    }

    private  Chuck getChuck(){
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
        return chuck;
    }

    @Override
    public void onClick(View v) {
        if (v == anotherBtnChuck){
            getChuck();
        }

    }
}
