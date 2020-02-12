package com.timilehinjegede.vimub.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.timilehinjegede.vimub.R;

public class SplashActivity extends AppCompatActivity {

    TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Creating a handler for the splash screen
        //Duration of the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                splashTextView = findViewById(R.id.splashTextView);
//                Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.zoom_out);
//                animation.setDuration(2000);
//                splashTextView.startAnimation(animation);

                //creating an intent that moves user to the main activity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2500);
    }
}
