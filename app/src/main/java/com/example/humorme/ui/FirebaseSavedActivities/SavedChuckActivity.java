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
import com.example.humorme.adapters.FirebaseAdapters.FirebaseChuckViewHolder;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.Chuck;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedChuckActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewChuck)
    RecyclerView recyclerViewChuck;
    @BindView(R.id.anotherBtnChuck)
    Button anotherBtnChuck;

    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Chuck, FirebaseChuckViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck);
        ButterKnife.bind(this);

        setTitle("SAVED CHUCK JOKES");
        anotherBtnChuck.setVisibility(anotherBtnChuck.INVISIBLE);

        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHUCK);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Chuck> options = new FirebaseRecyclerOptions.Builder<Chuck>()
                .setQuery(reference,Chuck.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Chuck, FirebaseChuckViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseChuckViewHolder holder, int position, @NonNull Chuck model) {
                holder.bindChuck(model);
            }

            @NonNull
            @Override
            public FirebaseChuckViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_item,viewGroup,false);
                return new FirebaseChuckViewHolder(view);
            }
        };
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(SavedChuckActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewChuck.setLayoutManager(horizontalLayoutManagaer);
        recyclerViewChuck.setAdapter(firebaseRecyclerAdapter);
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
