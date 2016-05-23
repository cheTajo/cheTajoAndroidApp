package com.teamlz.cheTajo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.object.Utils;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private boolean authenticated;
    private String id;

    private DatabaseReference myFirebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static int SPLASH_TIMER = 2000;

    @SuppressWarnings("unchecked") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticated = false;

        //Initialize sdk
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //System.out.println(FacebookSdk.getApplicationSignature(this));

        setContentView(R.layout.activity_splash);

        myFirebase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) return;

                Log.i("USER", user.toString());
            }

            /*@Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    LinkedHashMap<String, String> profile = (LinkedHashMap<String, String>) authData
                            .getProviderData()
                            .get("cachedUserProfile");

                    id = authData.getUid();
                    String email = profile.get("email");
                    String firstName = profile.get("first_name");
                    String lastName = profile.get("last_name");

                    Firebase userFirebase = myFirebase.child("users").child(id);
                    userFirebase.child("email").setValue(email);
                    userFirebase.child("firstName").setValue(firstName);
                    userFirebase.child("lastName").setValue(lastName);
                    authenticated = true;
                }
            }*/
        };

        //Utils.addHairDresser(this);

        //add immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        //finish immersive mode

        Typeface roboto = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Regular.ttf");
        AppCompatTextView text = (AppCompatTextView) findViewById(R.id.title_splash);
        assert (text != null);
        text.setTypeface(roboto);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i;
                if (!authenticated) i = new Intent(SplashActivity.this, LogInOrSignUpActivity.class);
                else {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                    i.putExtra("id", id);
                }
                finish();
                startActivity(i);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIMER);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) return;

                Log.i("USER", user.toString());
            }

            /*@Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    LinkedHashMap<String, String> profile = (LinkedHashMap<String, String>) authData
                            .getProviderData()
                            .get("cachedUserProfile");

                    id = authData.getUid();
                    String email = profile.get("email");
                    String firstName = profile.get("first_name");
                    String lastName = profile.get("last_name");

                    Firebase userFirebase = myFirebase.child("users").child(id);
                    userFirebase.child("email").setValue(email);
                    userFirebase.child("firstName").setValue(firstName);
                    userFirebase.child("lastName").setValue(lastName);
                    authenticated = true;
                }
            }*/
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}