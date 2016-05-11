package com.teamlz.cheTajo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.teamlz.cheTajo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private Firebase myFirebase;
    private Firebase.AuthStateListener authStateListener;
    private boolean authenticated;

    public static int SPLASH_TIMER = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticated = false;

        //Initialize sdk
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        Firebase.setAndroidContext(this);

        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));
        myFirebase.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //authenticated = true;
                }
            }
        });

        setContentView(R.layout.activity_splash);

        //add immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        //finish immersive mode

        Typeface roboto = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Regular.ttf");
        TextView text = (TextView) findViewById(R.id.title_splash);
        text.setTypeface(roboto);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i;
                if (!authenticated) i = new Intent(SplashActivity.this, LogInOrSignUpActivity.class);
                else i = new Intent(SplashActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(i);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIMER);
    }

}
