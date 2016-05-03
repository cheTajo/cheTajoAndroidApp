package com.teamlz.cheTajo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.teamlz.cheTajo.R;

import java.util.Arrays;

public class LoginOrSignupActivity extends AppCompatActivity {

    private LoginButton facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize facebook sdk
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_login_or_signup);

        //Set up manual login button
        Button manualLoginButton = (Button) findViewById(R.id.manual_sign_in_button);
        assert manualLoginButton != null;
        manualLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //Set up facebook button
        facebookButton = (LoginButton) findViewById(R.id.facebook_button);
        facebookButton.setReadPermissions(Arrays.asList("email"));

        // Callback registration
        CallbackManager callbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //Set up facebook login button
        AppCompatButton facebookLoginButton = (AppCompatButton) findViewById(R.id.facebook_sign_in_button);
        assert facebookLoginButton != null;
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButton.performClick();
            }
        });
    }
}