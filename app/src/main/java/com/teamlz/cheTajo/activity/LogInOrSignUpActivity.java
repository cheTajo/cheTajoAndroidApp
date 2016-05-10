package com.teamlz.cheTajo.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.fragment.LogInFragment;

public class LogInOrSignUpActivity extends AppCompatActivity {
    private Fragment logInFragment;
    //private AuthData facebookAuthData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_or_sign_up);

        //Initialize variables
        logInFragment = LogInFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.log_in_or_sign_up_frame, logInFragment).commit();
    }
}
