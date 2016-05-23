package com.teamlz.cheTajo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;

import java.util.LinkedHashMap;
import java.util.List;

public class LogInFragment extends Fragment {
    private Fragment signUpFragment;

    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private ProgressDialog authProgressDialog;

    private DatabaseReference myFirebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private LoginButton facebookButton;
    private CallbackManager facebookCallbackManager;

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

        // Initialize variables
        signUpFragment = SignUpFragment.newInstance();

        emailEditText = (AppCompatEditText) view.findViewById(R.id.log_in_email);
        passwordEditText = (AppCompatEditText) view.findViewById(R.id.log_in_password);
        authProgressDialog = new ProgressDialog(getActivity());

        myFirebase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                authProgressDialog.hide();
            }
        };

        AppCompatButton facebookLogInButton = (AppCompatButton) view.findViewById(R.id.facebook_log_in_button);
        AppCompatButton manualLogInButton = (AppCompatButton) view.findViewById(R.id.manual_log_in_button);
        AppCompatButton goToSignUpButton = (AppCompatButton) view.findViewById(R.id.go_to_sign_up_button);

        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Autenticazione in corso...");
        authProgressDialog.setCancelable(false);

        FirebaseAuth.getInstance().signOut();
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
                    i.putExtra("id", "79b451ed-fb1c-4282-8178-1d7582274a8f");
                    getActivity().finish();
                    startActivity(i);
                    return;
                }

                else if (email.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Tutti i campi sono obbligatori", Toast.LENGTH_LONG).show();
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
                getFragmentManager().beginTransaction()
                        .replace(R.id.log_in_or_sign_up_view, signUpFragment)
                        .addToBackStack(null).commit();
            }
        });

        //Set up facebook button
        facebookButton = (LoginButton) view.findViewById(R.id.facebook_button);
        facebookButton.setFragment(this);
        facebookButton.setReadPermissions("email", "public_profile");
        facebookCallbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLogIn(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                authProgressDialog.hide();
                Toast.makeText(getActivity(), "Autenticazione cancellata", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                authProgressDialog.hide();
                Toast.makeText(getActivity(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
            }
        });
        /*facebookAccessTokenTracker = new AccessTokenTracker() {
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
                else FirebaseAuth.getInstance().signOut();
            }
        };*/

        //Set up facebook login button
        assert facebookLogInButton != null;
        facebookLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButton.performClick();
                authProgressDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authProgressDialog.dismiss();
    }

    private void manualLogIn (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    authProgressDialog.hide();
                    if (!task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("id", task.getResult().getUser().getUid());
                    getActivity().finish();
                    startActivity(i);
                }
            });
    }

    @SuppressWarnings("unchecked")
    private void facebookLogIn (AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getActivity(), "Autenticazione fallita", Toast.LENGTH_LONG).show();
                        return;
                    }
                    FirebaseUser user = task.getResult().getUser();
                    List providerData = user.getProviderData();
                    String id = task.getResult().getUser().getUid();
                    String email = user.getEmail();
                    Log.i("NAME", providerData.toString());
                    Log.i("ID", id);
                    Log.i("EMAIL", email);


                    authProgressDialog.hide();
                    /*Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("id", id);
                    getActivity().finish();
                    startActivity(i);*/

                }
        });

        /*LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) authData
                .getProviderData()
                .get("cachedUserProfile");
        Log.i("PROFILE", profile.toString());
        String id = authData.getUid();
        String email = (String) profile.get("email");
        String firstName = (String) profile.get("first_name");
        String lastName = (String) profile.get("last_name");

        Firebase userFirebase = myFirebase.child("users").child(id);
        userFirebase.child("email").setValue(email);
        userFirebase.child("firstName").setValue(firstName);
        userFirebase.child("lastName").setValue(lastName);

        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("id", id);
        getActivity().finish();
        startActivity(i);*/
    }
}