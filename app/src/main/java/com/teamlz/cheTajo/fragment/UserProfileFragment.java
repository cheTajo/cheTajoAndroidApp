package com.teamlz.cheTajo.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.User;

import java.io.InputStream;
import java.net.URL;

@SuppressWarnings("deprecation")
public class UserProfileFragment extends Fragment{
    private Toolbar toolbar;
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.user_profile_toolbar);
        emailTextView = (AppCompatTextView) view.findViewById(R.id.user_profile_card_email);

        DatabaseReference myFirebase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userFirebase = myFirebase.child("users").child(MainActivity.id);
        userFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myUser = snapshot.getValue(User.class);
                String title = myUser.getFirstName() + " " + myUser.getLastName();
                toolbar.setTitle(title);
                emailTextView.setText(myUser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        AppCompatImageView profileImageView = (AppCompatImageView) view.findViewById(R.id.user_profile_image);
        String url = "https://graph.facebook.com/<facebook_user_id>/picture?type=large";
        String id = MainActivity.id.replace("facebook:", "");
        url = url.replace("<facebook_user_id>", id);
        Drawable image = null;
        try {
            InputStream is = (InputStream) new URL(url)
                    .getContent();
            image = Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileImageView.setImageDrawable(image);

        FloatingActionButton fab_settings = (FloatingActionButton) view.findViewById(R.id.fab_settings);
        fab_settings.setImageDrawable(new IconicsDrawable(this.getActivity(), "gmd-create")
                .sizeDp(18)
                .color(getResources().getColor(R.color.white)));

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}