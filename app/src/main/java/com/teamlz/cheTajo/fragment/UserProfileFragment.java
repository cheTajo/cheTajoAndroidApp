package com.teamlz.cheTajo.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.User;

public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }
    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
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

        User myUser = MainActivity.myUser;

        AppCompatTextView toolbarText = (AppCompatTextView) view.findViewById(R.id.collapsing_text);
        toolbarText.setText(myUser.getFirstName() + " " + myUser.getLastName());

        AppCompatTextView emailTextView = (AppCompatTextView) view.findViewById(R.id.user_profile_card_email);
        emailTextView.setText(myUser.getEmail());

        return view;
    }
}