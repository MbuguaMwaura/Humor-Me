package com.example.humorme.ui.FirebaseSavedActivities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseDadJokeListAdapter;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseDadJokeViewholder;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.DadJoke;
import com.example.humorme.util.OnStartDragListener;
import com.example.humorme.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedDadJokeActivity extends AppCompatActivity implements OnStartDragListener {
    @BindView(R.id.recyclerViewDadJoke)
    RecyclerView recyclerViewDadJoke;
    @BindView(R.id.anotherBtnDadJoke)
    Button anotherBtnDadJoke;

    private FirebaseDadJokeListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<DadJoke, FirebaseDadJokeViewholder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);
        ButterKnife.bind(this);
        setTitle("SAVED DAD JOKES");
        anotherBtnDadJoke.setVisibility(anotherBtnDadJoke.INVISIBLE);


            setUpFireBaseAdapter();

    }

    private void setUpFireBaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DADJOKE).child(uid);

        FirebaseRecyclerOptions<DadJoke> options = new FirebaseRecyclerOptions.Builder<DadJoke>()
                .setQuery(reference, DadJoke.class)
                .build();

        mFirebaseAdapter = new FirebaseDadJokeListAdapter(options,reference,this, this);
        recyclerViewDadJoke.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDadJoke.setHasFixedSize(true);
        recyclerViewDadJoke.setAdapter(mFirebaseAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper  = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewDadJoke);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
