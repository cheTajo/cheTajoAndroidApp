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

import com.facebook.login.LoginManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.object.HairDresser;

public class HairDresserSignUpFragment extends Fragment {
    private View view;
    private AppCompatEditText signUpEmail;
    private AppCompatEditText signUpShopName;
    private AppCompatEditText signUpAddress;
    private AppCompatEditText signUpPassword;
    private ProgressDialog authProgressDialog;

    private DatabaseReference myFirebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String email;
    private String password;
    private String shopName;
    private String address;

    public HairDresserSignUpFragment() {}

    public static HairDresserSignUpFragment newInstance() {
        return new HairDresserSignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hair_dresser_sign_up, container, false);

        signUpEmail = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_email);
        signUpShopName = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_shop_name);
        signUpAddress = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_address);
        signUpPassword = (AppCompatEditText) view.findViewById(R.id.hd_sign_up_password);

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        myFirebase = FirebaseDatabase.getInstance().getReference().child("hairDressers");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) return;

                user.updateProfile( new UserProfileChangeRequest.Builder()
                        .setDisplayName(shopName)
                        .build());

                //Todo RETRIEVE COORDINATES FROM ADDRESS
                HairDresser hd = new HairDresser(user.getUid(), shopName);
                hd.setLatitude(41.8919300);
                hd.setLongitude(12.5113300);
                myFirebase.child(user.getUid()).setValue(hd);

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

        AppCompatButton hdSignUpButton = (AppCompatButton) view.findViewById(R.id.hd_sign_up_button);
        hdSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(view);

                email = signUpEmail.getText().toString().trim();
                password = signUpPassword.getText().toString();
                shopName = signUpShopName.getText().toString().trim();
                address = signUpAddress.getText().toString().trim();

                if (email.equals("") || shopName.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Compilare tutti i campi obbligatori", Toast.LENGTH_LONG).show();
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

    public void hideKeyboard(View view) {
        if(getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}