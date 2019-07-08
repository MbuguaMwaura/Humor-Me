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
import android.widget.LinearLayout;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpListAdapter;
import com.example.humorme.adapters.FirebaseAdapters.FirebaseTrumpViewHolder;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.ChuckActivity;
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

public class SavedTrumpActivity extends AppCompatActivity implements OnStartDragListener {
    @BindView(R.id.recyclerView)
    RecyclerView savedRV;
    @BindView(R.id.anotherBtn)
    Button anotherBtn;


    private FirebaseTrumpListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Quotes, FirebaseTrumpViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        ButterKnife.bind(this);
        setTitle("SAVED TRUMP QUOTES");
        anotherBtn.setVisibility(anotherBtn.INVISIBLE);


        setUpFireBaseAdapter();

    }

    private void setUpFireBaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_TRUMP).child(uid);
        FirebaseRecyclerOptions<Quotes> options = new FirebaseRecyclerOptions.Builder<Quotes>()
                .setQuery(reference,Quotes.class)
                .build();

        mFirebaseAdapter = new FirebaseTrumpListAdapter(options,reference,this,this);
        savedRV.setAdapter(mFirebaseAdapter);
        savedRV.setLayoutManager(new LinearLayoutManager(this));
        savedRV.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(savedRV);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening(); }
}
