package com.example.humorme.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.ui.FirebaseSavedActivities.SavedChuckActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChuckDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.chuckFragTV)
    TextView chuckFragmentTV;
    @BindView(R.id.saveChuckTextView) TextView saveChuckTV;

    private Chuck mChuck;

    public static ChuckDetailFragment newInstance(Chuck chuck){
        ChuckDetailFragment chuckDetailFragment = new ChuckDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("chuck", Parcels.wrap(chuck));
        chuckDetailFragment.setArguments(args);
        return chuckDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChuck = Parcels.unwrap(getArguments().getParcelable("chuck"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chuck_detail,container,false);
        ButterKnife.bind(this,view);
        chuckFragmentTV.setText(mChuck.getValue());
        saveChuckTV.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
            if (v == saveChuckTV){
                DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHUCK);
                databaseReference.push().setValue(mChuck);
                Intent intent  = new Intent(getContext(), SavedChuckActivity.class);
                startActivity(intent);
            }
    }
}
