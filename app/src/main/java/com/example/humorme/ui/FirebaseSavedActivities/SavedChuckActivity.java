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

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseChuckListAdapter;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseChuckViewHolder;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.Chuck;
import com.example.humorme.util.OnStartDragListener;
import com.example.humorme.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedChuckActivity extends AppCompatActivity implements OnStartDragListener {
    @BindView(R.id.recyclerViewChuck)
    RecyclerView recyclerViewChuck;
    @BindView(R.id.anotherBtnChuck)
    Button anotherBtnChuck;

    private FirebaseChuckListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Chuck, FirebaseChuckViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuck);
        ButterKnife.bind(this);

        setTitle("SAVED CHUCK JOKES");
        anotherBtnChuck.setVisibility(anotherBtnChuck.INVISIBLE);


        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHUCK).child(uid).orderByChild(Constants.FIREBASE_QUERY_INDEX);


        FirebaseRecyclerOptions<Chuck> options = new FirebaseRecyclerOptions.Builder<Chuck>()
                .setQuery(query,Chuck.class)
                .build();

        mFirebaseAdapter = new FirebaseChuckListAdapter(options,query,this,this);
        recyclerViewChuck.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewChuck.setAdapter(mFirebaseAdapter);
        recyclerViewChuck.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewChuck);
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
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening(); }
}
