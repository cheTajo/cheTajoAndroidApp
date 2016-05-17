package com.teamlz.cheTajo.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.User;

public class UserProfileFragment extends Fragment {
    private CollapsingToolbarLayout toolbar;
    private AppCompatTextView emailTextView;

    private User myUser;

    public UserProfileFragment() {
        // Required empty public constructor
    }
    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.user_profile_toolbar);
        emailTextView = (AppCompatTextView) view.findViewById(R.id.user_profile_card_email);

        Firebase myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        Firebase userFirebase = myFirebase.child("users").child(MainActivity.id);
        userFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myUser = snapshot.getValue(User.class);
                String title = myUser.getFirstName() + " " + myUser.getLastName();
                toolbar.setTitle(title);
                emailTextView.setText(myUser.getEmail());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        FloatingActionButton fab_settings = (FloatingActionButton) view.findViewById(R.id.fab_settings);
        fab_settings.setImageDrawable(new IconicsDrawable(this.getActivity(), "gmd-create")
                .sizeDp(24)
                .color(getResources().getColor(R.color.white)));

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}