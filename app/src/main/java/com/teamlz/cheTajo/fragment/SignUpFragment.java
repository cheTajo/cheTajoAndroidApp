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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.User;

public class SignUpFragment extends Fragment {
    private AppCompatEditText signUpEmail;
    private AppCompatEditText signUpFirstName;
    private AppCompatEditText signUpLastName;
    private AppCompatEditText signUpPassword;
    private ProgressDialog authProgressDialog;

    private SignUpFragment signUpFragment;

    private Firebase myFirebase;
    private boolean flag;
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

        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        flag = true;

        authProgressDialog = new ProgressDialog(getActivity());
        authProgressDialog.setTitle("Attendi");
        authProgressDialog.setMessage("Registrazione in corso...");
        authProgressDialog.setCancelable(false);

        AppCompatButton signUpButton = (AppCompatButton) view.findViewById(R.id.manual_sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = signUpEmail.getText().toString().trim();
                final String password = signUpPassword.getText().toString();
                final String firstName = signUpFirstName.getText().toString().trim();
                final String lastName = signUpLastName.getText().toString().trim();

                if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Tutti i campi sono obbligatori", Toast.LENGTH_LONG).show();
                    return;
                }

                authProgressDialog.show();

                myFirebase.createUser(email, password, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        flag = true;
                        Log.i("REGISTRAZIONE", "SUCCESSO");

                        myFirebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Log.i("LOGIN", "SUCCESSO");
                                String uid = authData.getUid();
                                User newUser = new User(email, firstName, lastName);
                                myFirebase.child("users").child(uid).setValue(newUser);
                                authProgressDialog.hide();

                                Intent i = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                startActivity(i);
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Log.i("LOGIN", "FALLITA:" + firebaseError.toString());
                                authProgressDialog.hide();
                                signUpFragment = SignUpFragment.newInstance();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.log_in_or_sign_up_frame, signUpFragment)
                                        .commit();
                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        authProgressDialog.hide();
                        Log.i("REGISTRAZIONE", "FALLITO");
                        flag = false;
                        Toast.makeText(getActivity(), "Registrazione fallita", Toast.LENGTH_LONG).show();
                    }
                });
                if (!flag) return;
            }

        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authProgressDialog.dismiss();
    }
}