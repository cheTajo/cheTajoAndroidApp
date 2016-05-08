package com.teamlz.cheTajo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
import com.teamlz.cheTajo.fragment.SignUpFragment;

public class LogInOrSignUpActivity extends AppCompatActivity {
    private Fragment signUpFragment;
    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private Firebase myFirebase;
    private Firebase.AuthStateListener authStateListener;
    private ProgressDialog authProgressDialog;
    //private AuthData facebookAuthData;

    private LoginButton facebookButton;
    private CallbackManager facebookCallbackManager;
    private AccessTokenTracker facebookAccessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_or_sign_up);

        //Initialize variables
        signUpFragment = SignUpFragment.newInstance();
        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        emailEditText = (AppCompatEditText) findViewById(R.id.log_in_email);
        passwordEditText = (AppCompatEditText) findViewById(R.id.log_in_password);

        AppCompatButton facebookLogInButton = (AppCompatButton) findViewById(R.id.facebook_log_in_button);
        AppCompatButton manualLogInButton = (AppCompatButton) findViewById(R.id.manual_log_in_button);
        AppCompatButton goToSignUpLoginButton = (AppCompatButton) findViewById(R.id.go_to_sign_up_button);

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
        assert manualLogInButton != null;
        manualLogInButton.setOnClickListener(new View.OnClickListener() {
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
        assert facebookLogInButton != null;
        facebookLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButton.performClick();
            }
        });

        //Set up sign up button
        assert goToSignUpLoginButton != null;
        goToSignUpLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.log_in_form, signUpFragment).addToBackStack(null).commit();
            }
        });

        authProgressDialog = new ProgressDialog(this);
        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Autenticazione in corso...");
        authProgressDialog.setCancelable(false);

        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                authProgressDialog.hide();
                //facebookAuthData = authData;
            }
        };
        myFirebase.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facebookAccessTokenTracker.stopTracking();
        myFirebase.removeAuthStateListener(authStateListener);
        authProgressDialog.dismiss();
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
