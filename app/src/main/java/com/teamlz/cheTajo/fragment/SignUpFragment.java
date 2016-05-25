package com.teamlz.cheTajo.fragment;

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
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;

public class SignUpFragment extends Fragment {
    private AppCompatEditText signUpEmail;
    private AppCompatEditText signUpFirstName;
    private AppCompatEditText signUpLastName;
    private AppCompatEditText signUpPassword;
    private ProgressDialog authProgressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public SignUpFragment() {}

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpEmail = (AppCompatEditText) view.findViewById(R.id.sign_up_email);
        signUpFirstName = (AppCompatEditText) view.findViewById(R.id.sign_up_first_name);
        signUpLastName = (AppCompatEditText) view.findViewById(R.id.sign_up_last_name);
        signUpPassword = (AppCompatEditText) view.findViewById(R.id.sign_up_password);

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) return;

                user.updateProfile( new UserProfileChangeRequest.Builder()
                        .setDisplayName(firstName + " " + lastName)
                        .build());

                authProgressDialog.hide();
                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        };

        authProgressDialog = new ProgressDialog(getActivity());
        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Registrazione in corso...");
        authProgressDialog.setCancelable(false);

        AppCompatButton signUpButton = (AppCompatButton) view.findViewById(R.id.manual_sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = signUpEmail.getText().toString().trim();
                password = signUpPassword.getText().toString();
                firstName = signUpFirstName.getText().toString().trim();
                lastName = signUpLastName.getText().toString().trim();

                if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Tutti i campi sono obbligatori", Toast.LENGTH_LONG).show();
                    return;
                }

                authProgressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) return;
                            Toast.makeText(getActivity(), "Registrazione fallita", Toast.LENGTH_LONG).show();
                            authProgressDialog.hide();
                        }
                    });
            }
        });
        return view;
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
}

