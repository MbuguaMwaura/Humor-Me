package com.example.humorme.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.humorme.R;
import com.example.humorme.models.DadJoke;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.card1)
    CardView trump;
    @BindView(R.id.card2) CardView chuck;
    @BindView(R.id.card3) CardView dadJokes;


    private DadJoke dadJoke;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    getSupportActionBar().setTitle("Welcome, "+user.getDisplayName());
                }else {
                    getSupportActionBar().setTitle("Welcome!");
                }
            }
        };

        trump.setOnClickListener(this);
        chuck.setOnClickListener(this);
        dadJokes.setOnClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener!= null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == trump){
            Intent intent = new Intent(this, QuoteActivity.class);
            startActivity(intent);
        }
        if (v == chuck){
            Intent intent = new Intent(this, ChuckActivity.class);
            startActivity(intent);
        }
        if(v == dadJokes){
            Intent intent = new Intent(this, DadJokeActivity.class);
            Toast.makeText(getApplicationContext(),"working", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }

    }
}
