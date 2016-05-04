package com.teamlz.cheTajo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.teamlz.cheTajo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    public static int SPLASH_TIMER = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize sdk
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        Firebase.setAndroidContext(this);

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

                //start e new activity
                Intent intent = new Intent(SplashActivity.this, LoginOrSignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
                startActivity(intent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIMER);
    }

}
