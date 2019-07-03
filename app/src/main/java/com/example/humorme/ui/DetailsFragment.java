package com.example.humorme.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.DadJoke;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.fragmentTV)
    TextView fragmentTV;
    @BindView(R.id.saveText) TextView saveText;

    private DadJoke mDadJoke;


    public static DetailsFragment newInstance(DadJoke dadJoke) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("dadJoke", Parcels.wrap(dadJoke));
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDadJoke = Parcels.unwrap(getArguments().getParcelable("dadJoke"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,container,false);
        ButterKnife.bind(this,view);

        fragmentTV.setText(mDadJoke.getJoke());

        saveText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == saveText){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dadJoke").child("jokes");
            reference.setValue(mDadJoke);
        }
    }
}
