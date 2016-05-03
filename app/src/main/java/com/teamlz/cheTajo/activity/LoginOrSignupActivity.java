package com.teamlz.cheTajo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.teamlz.cheTajo.R;

import java.util.Arrays;
import java.util.Map;

public class LoginOrSignUpActivity extends AppCompatActivity {
    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private LoginButton facebookButton;
    private AppCompatButton manualLoginButton;
    private AppCompatButton facebookLoginButton;
    private Firebase myFirebase;

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

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserisci e-mail e password", Toast.LENGTH_LONG).show();
                    return;
                }

                verifyCredentials(email, password);
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

    private void verifyCredentials (String email, String password) {
         /*myFirebase.child("message").setValue("Do you have data? You'll love Firebase.");
         myFirebase.child("user").setValue(true);*/

        myFirebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Intent i = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Errore di autenticazione", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}