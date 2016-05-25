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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;

import java.io.InputStream;
import java.net.URL;

@SuppressWarnings("deprecation")
public class UserProfileFragment extends Fragment{

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert (user != null);

        AppCompatImageView profileImageView = (AppCompatImageView) view.findViewById(R.id.user_profile_image);
        String id = user.getProviderData().get(1).getUid();
        String url = "https://graph.facebook.com/" + id + "/picture?type=large";
        Drawable image = null;
        try {
            InputStream is = (InputStream) new URL(url)
                    .getContent();
            image = Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileImageView.setImageDrawable(image);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.user_profile_toolbar);
        toolbar.setTitle(user.getDisplayName());

        AppCompatTextView emailTextView = (AppCompatTextView) view.findViewById(R.id.user_profile_card_email);
        emailTextView.setText(user.getEmail());

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