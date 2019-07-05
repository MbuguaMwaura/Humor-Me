package com.example.humorme.ui.FirebaseSavedActivities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseDadJokeViewholder;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.DadJoke;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedDadJokeActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewDadJoke)
    RecyclerView recyclerViewDadJoke;
    @BindView(R.id.anotherBtnDadJoke)
    Button anotherBtnDadJoke;

    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<DadJoke, FirebaseDadJokeViewholder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        ButterKnife.bind(this);
        setTitle("SAVED DAD JOKES");
        anotherBtnDadJoke.setVisibility(anotherBtnDadJoke.INVISIBLE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DADJOKE).child(uid);
        setUpFireBaseAdapter();
    }

    private void setUpFireBaseAdapter() {
        FirebaseRecyclerOptions<DadJoke> options = new FirebaseRecyclerOptions.Builder<DadJoke>()
                .setQuery(reference, DadJoke.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DadJoke, FirebaseDadJokeViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseDadJokeViewholder holder, int position, @NonNull DadJoke model) {
                holder.bindDadJoke(model);
            }

            @NonNull
            @Override
            public FirebaseDadJokeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
                return new FirebaseDadJokeViewholder(view);
            }
        };
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(SavedDadJokeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDadJoke.setLayoutManager(horizontalLayoutManagaer);
        recyclerViewDadJoke.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseRecyclerAdapter!= null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }
}
