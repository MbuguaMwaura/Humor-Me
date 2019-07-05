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
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.ChuckActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedTrumpActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView savedRV;
    @BindView(R.id.anotherBtn)
    Button anotherBtn;

    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Quotes, FirebaseTrumpViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        ButterKnife.bind(this);
        setTitle("SAVED TRUMP QUOTES");
        anotherBtn.setVisibility(anotherBtn.INVISIBLE);


        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_TRUMP);
        setUpFireBaseAdapter();

    }

    private void setUpFireBaseAdapter() {
        FirebaseRecyclerOptions<Quotes> options = new FirebaseRecyclerOptions.Builder<Quotes>()
                .setQuery(reference,Quotes.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Quotes, FirebaseTrumpViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseTrumpViewHolder holder, int position, @NonNull Quotes model) {
                holder.bindTrump(model);
            }

            @NonNull
            @Override
            public FirebaseTrumpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
                return new FirebaseTrumpViewHolder(view)

                ;
            }
        };
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(SavedTrumpActivity.this, LinearLayoutManager.VERTICAL, false);

        savedRV.setLayoutManager(horizontalLayoutManagaer);
        savedRV.setAdapter(firebaseRecyclerAdapter);
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
