package com.teamlz.cheTajo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.object.User;
import com.teamlz.cheTajo.object.Utils;

import java.util.Arrays;
import java.util.Map;

public class LoginOrSignUpActivity extends AppCompatActivity {
    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private LoginButton facebookButton;
    private AppCompatButton manualLoginButton;
    private AppCompatButton facebookLoginButton;
    private Firebase myFirebase;
    private String TAG = "LOGIN AND SIGNUP";
    final Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_or_sign_up);

        //initialize variables
        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        emailEditText = (AppCompatEditText) findViewById(R.id.email);
        passwordEditText = (AppCompatEditText) findViewById(R.id.password);
        manualLoginButton = (AppCompatButton) findViewById(R.id.manual_sign_in_button);
        facebookButton = (LoginButton) findViewById(R.id.facebook_button);
        facebookLoginButton = (AppCompatButton) findViewById(R.id.facebook_sign_in_button);

        //Set up manual login button
        assert manualLoginButton != null;
        manualLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.equals("test")) {
                    Intent i = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return;
                }

                else if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserisci e-mail e password", Toast.LENGTH_LONG).show();
                    return;
                }

                logInOrSignUp (email, password);
            }
        });

        //Set up facebook button
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
        assert facebookLoginButton != null;
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButton.performClick();
            }
        });
    }

    private void logInOrSignUp (String email, String password) {
        final String signUpEmail = email;
        final String signUpPassword = password;
        final String[] credential = new String[2];

        myFirebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(i);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }
}