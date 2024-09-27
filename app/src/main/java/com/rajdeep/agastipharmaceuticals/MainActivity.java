package com.rajdeep.agastipharmaceuticals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView agastiImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agastiImageView = findViewById(R.id.agasti_logo);
        Animation rotateAndScale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation_and_scale);
        Animation fadeInAndScale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_and_scale);

        agastiImageView.startAnimation(fadeInAndScale);     // initially, this combination of animation will start

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                agastiImageView.startAnimation(rotateAndScale);     // after execution of 1st thread, this animation will start and previous animation will stop by getting overridden.
            }
        }, 1300);


        new Handler().postDelayed(new Runnable() {
            Intent iNext;

            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);   // Variable of shared preference which will be remained in the non-volatile memory of the computer when the program is exited to store a data. This method is used when we want to store some little amount of data where it is not necessary to crate a new database.
                Boolean check = pref.getBoolean("flag", false);

//                Toast.makeText(MainActivity.this, String.valueOf(check), Toast.LENGTH_SHORT).show();
                if(check){
                    iNext = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(iNext);
                    finish();       // finish() this will clear the backtrace of the SplashActivity (MainActivity) Activity from the stack
                }
                else{
                    iNext = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(iNext);
                    finish();       // finish() this will clear the backtrace of the SplashActivity (MainActivity) Activity from the stack
                }


            }
        }, 3000);

    }
}