package com.example.humorme.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.Quotes;
import com.example.humorme.ui.FirebaseSavedActivities.SavedTrumpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuotesDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.quotesFragmentTV)
    TextView quotesFragmentTV;
    @BindView(R.id.saveQuoteTextView) TextView saveQuoteTV;

    private Quotes mQuotes;

    public static QuotesDetailFragment newInstance(Quotes quotes){
        QuotesDetailFragment quotesDetailFragment = new QuotesDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("quotes", Parcels.wrap(quotes));
        quotesDetailFragment.setArguments(args);
        return quotesDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuotes = Parcels.unwrap(getArguments().getParcelable("quotes"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes_detail,container,false);
        ButterKnife.bind(this,view);
        quotesFragmentTV.setText(mQuotes.getQuote());
        saveQuoteTV.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == saveQuoteTV){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_TRUMP).child(uid);
            DatabaseReference pushRef = reference.push();
            String pushID = pushRef.getKey();
            mQuotes.setPushId(pushID);
            pushRef.setValue(mQuotes);
            Intent intent  = new Intent(getContext(), SavedTrumpActivity.class);
            startActivity(intent);
        }
    }
}
