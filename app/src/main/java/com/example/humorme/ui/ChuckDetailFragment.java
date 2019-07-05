package com.example.humorme.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humorme.Constants;
import com.example.humorme.R;
import com.example.humorme.models.Chuck;
import com.example.humorme.ui.FirebaseSavedActivities.SavedChuckActivity;
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
public class ChuckDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.chuckFragTV)
    TextView chuckFragmentTV;
    @BindView(R.id.saveChuckTextView) TextView saveChuckTV;
    @BindView(R.id.sendBTN)
    ImageView sendBtn;

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
        sendBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
            if (v == saveChuckTV){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHUCK).child(uid);
                DatabaseReference pushRef = databaseReference.push();
                String pushID = pushRef.getKey();
                mChuck.setPushId(pushID);
                pushRef.setValue(mChuck);
                Intent intent  = new Intent(getContext(), SavedChuckActivity.class);
                startActivity(intent);
            }
            if (v == sendBtn){
                try
                {
                    // Check if the Twitter app is installed on the phone.
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text="+mChuck.getValue()+""));
                    startActivity(browserIntent);


                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Twitter is not installed on this device",Toast.LENGTH_LONG).show();

                }
            }
    }
}
