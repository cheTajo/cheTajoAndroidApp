package com.teamlz.cheTajo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.User;

import java.util.LinkedHashMap;

public class LogInFragment extends Fragment {
    private Fragment signUpFragment;
    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private ProgressDialog authProgressDialog;

    private Firebase myFirebase;
    private Firebase.AuthStateListener authStateListener;

    private LoginButton facebookButton;
    private CallbackManager facebookCallbackManager;
    private AccessTokenTracker facebookAccessTokenTracker;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        emailEditText = (AppCompatEditText) view.findViewById(R.id.log_in_email);
        passwordEditText = (AppCompatEditText) view.findViewById(R.id.log_in_password);
        authProgressDialog = new ProgressDialog(getActivity());
        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));

        AppCompatButton facebookLogInButton = (AppCompatButton) view.findViewById(R.id.facebook_log_in_button);
        AppCompatButton manualLogInButton = (AppCompatButton) view.findViewById(R.id.manual_log_in_button);
        AppCompatButton goToSignUpButton = (AppCompatButton) view.findViewById(R.id.go_to_sign_up_button);

        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Autenticazione in corso...");
        authProgressDialog.setCancelable(false);

        myFirebase.unauth();
        LoginManager.getInstance().logOut();

        //Set up manual login button
        assert manualLogInButton != null;
        manualLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.equals("test")) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    startActivity(i);
                    return;
                }

                else if (email.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Inserisci e-mail e password", Toast.LENGTH_LONG).show();
                    return;
                }

                authProgressDialog.show();
                manualLogIn (email, password);
            }
        });

        //Set up sign up button
        assert goToSignUpButton != null;
        goToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFragment = SignUpFragment.newInstance();
                getFragmentManager().beginTransaction()
                        .replace(R.id.log_in_or_sign_up_frame, signUpFragment)
                        .addToBackStack(null).commit();
            }
        });

        //Set up facebook button
        facebookButton = (LoginButton) view.findViewById(R.id.facebook_button);
        facebookButton.setFragment(this);
        facebookCallbackManager = CallbackManager.Factory.create();
        facebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    authProgressDialog.show();
                    myFirebase.authWithOAuthToken("facebook", currentAccessToken.getToken(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            facebookLogIn (authData);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            authProgressDialog.hide();
                            Toast.makeText(getActivity(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else myFirebase.unauth();
            }
        };
        //Set up facebook login button
        assert facebookLogInButton != null;
        facebookLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButton.performClick();
            }
        });

        authStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                authProgressDialog.hide();
                //facebookAuthData = authData;
            }
        };
        myFirebase.addAuthStateListener(authStateListener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        facebookAccessTokenTracker.stopTracking();
        myFirebase.removeAuthStateListener(authStateListener);
        authProgressDialog.dismiss();
    }

    private void manualLogIn (final String email, final String password) {
        myFirebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                User.myEmail = email;
                authProgressDialog.hide();
                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                authProgressDialog.hide();
                Toast.makeText(getActivity(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void facebookLogIn (AuthData authData) {
        LinkedHashMap <String, String> token = (LinkedHashMap<String, String>) authData.getAuth().get("token");
        String email = token.get("email");
        User.myEmail = email;

        myFirebase = myFirebase.child("users");
        //myFirebase.
        Log.i ("LOGIN_DATA", "\n" + token.get("name") + "\n");
        authProgressDialog.hide();
        Intent i = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(i);
    }
}
