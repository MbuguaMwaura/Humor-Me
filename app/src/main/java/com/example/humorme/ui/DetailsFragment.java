package com.example.humorme.ui;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.models.DadJoke;
import com.example.humorme.ui.FirebaseSavedActivities.SavedDadJokeActivity;
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
public class DetailsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.fragmentTV)
    TextView fragmentTV;
    @BindView(R.id.saveText) TextView saveText;
    @BindView(R.id.copyClipboard) TextView copyClipboard;

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
        copyClipboard.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == saveText){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DADJOKE).child(uid)    ;
            DatabaseReference pushRef = reference.push();
            String pushID = pushRef.getKey();
            mDadJoke.setPushId(pushID);
            pushRef.setValue(mDadJoke);
            Intent intent = new Intent(getContext(), SavedDadJokeActivity.class);
            startActivity(intent);
        }
        if (v == copyClipboard){
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(mDadJoke.getJoke());
            Toast.makeText(getContext(),"Copied to Clipboard", Toast.LENGTH_LONG).show();
        }
    }
}
