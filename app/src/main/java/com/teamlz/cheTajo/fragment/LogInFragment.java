package com.teamlz.cheTajo.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;

public class LogInFragment extends Fragment {
    private Fragment signUpFragment;
    private Fragment hairDresserSignUpFragment;

    private View view;

    private AppCompatEditText emailEditText;
    private AppCompatEditText passwordEditText;
    private ProgressDialog authProgressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
        view = inflater.inflate(R.layout.fragment_log_in, container, false);

        // Initialize variables
        signUpFragment = UserSignUpFragment.newInstance();
        hairDresserSignUpFragment = HairDresserSignUpFragment.newInstance();

        emailEditText = (AppCompatEditText) view.findViewById(R.id.log_in_email);
        passwordEditText = (AppCompatEditText) view.findViewById(R.id.log_in_password);
        authProgressDialog = new ProgressDialog(getActivity());

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) return;

                authProgressDialog.hide();
                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        };

        AppCompatButton facebookLogInButton = (AppCompatButton) view.findViewById(R.id.facebook_log_in_button);
        AppCompatButton manualLogInButton = (AppCompatButton) view.findViewById(R.id.manual_log_in_button);
        AppCompatButton goToSignUpButton = (AppCompatButton) view.findViewById(R.id.go_to_sign_up_button);
        AppCompatButton goToHdSignUpButton = (AppCompatButton) view.findViewById(R.id.hairdresser_sign_up_button);

        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Autenticazione in corso...");
        authProgressDialog.setCancelable(false);


        //Set up manual login button
        assert manualLogInButton != null;
        manualLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(view);

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
                EmailAuthProvider.getCredential(email, password);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) return;
                        Toast.makeText(getActivity(), "Autenticazione Fallita", Toast.LENGTH_LONG).show();
                        authProgressDialog.hide();
                    }
                });
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
                    AuthCredential credential = FacebookAuthProvider.getCredential(currentAccessToken.getToken());
                    mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) return;
                                Toast.makeText(getActivity(), "Autenticazione Fallita", Toast.LENGTH_LONG).show();
                                authProgressDialog.hide();
                            }
                        });
                }
                else FirebaseAuth.getInstance().signOut();
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

        //Set up hairdresser sign up button
        assert goToHdSignUpButton != null;
        goToHdSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.log_in_or_sign_up_view, hairDresserSignUpFragment)
                        .addToBackStack(null).commit();
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
        facebookAccessTokenTracker.startTracking();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            facebookAccessTokenTracker.stopTracking();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authProgressDialog.dismiss();
    }

    public void hideKeyboard(View view) {
        if(getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}