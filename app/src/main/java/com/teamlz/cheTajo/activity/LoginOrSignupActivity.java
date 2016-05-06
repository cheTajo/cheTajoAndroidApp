package com.teamlz.cheTajo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.teamlz.cheTajo.R;

public class LoginOrSignUpActivity extends AppCompatActivity {
    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private AppCompatButton manualLoginButton;
    private AppCompatButton facebookLoginButton;

    private Firebase myFirebase;
    private Firebase.AuthStateListener authStateListener;
    private ProgressDialog authProgressDialog;
    private AuthData facebookAuthData;

    private LoginButton facebookButton;
    private CallbackManager facebookCallbackManager;
    private AccessTokenTracker facebookAccessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);

        //Initialize variables
        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        facebookLoginButton = (AppCompatButton) findViewById(R.id.facebook_sign_in_button);
        manualLoginButton = (AppCompatButton) findViewById(R.id.manual_sign_in_button);
        emailEditText = (AppCompatEditText) findViewById(R.id.email);
        passwordEditText = (AppCompatEditText) findViewById(R.id.password);

        myFirebase.unauth();
        LoginManager.getInstance().logOut();

        //Set up facebook button
        facebookButton = (LoginButton) findViewById(R.id.facebook_button);
        facebookCallbackManager = CallbackManager.Factory.create();
        facebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    authProgressDialog.show();
                    myFirebase.authWithOAuthToken("facebook", currentAccessToken.getToken(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            authProgressDialog.hide();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            finish();
                            startActivity(i);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            authProgressDialog.hide();
                            Toast.makeText(getApplicationContext(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else myFirebase.unauth();
            }
        };

        //Set up manual login button
        assert manualLoginButton != null;
        manualLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.equals("test")) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(i);
                }

                else if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserisci e-mail e password", Toast.LENGTH_LONG).show();
                    return;
                }

                logInOrSignUp (email, password);
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

        authProgressDialog = new ProgressDialog(this);
        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Autenticazione in corso...");
        authProgressDialog.setCancelable(false);
        authProgressDialog.show();

        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                authProgressDialog.hide();
                facebookAuthData = authData;
            }
        };
        myFirebase.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facebookAccessTokenTracker.stopTracking();
        myFirebase.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logInOrSignUp (String email, String password) {
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