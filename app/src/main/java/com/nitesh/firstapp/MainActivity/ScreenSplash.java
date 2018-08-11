package com.nitesh.firstapp.MainActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nitesh.firstapp.R;

public class ScreenSplash extends AppCompatActivity {
    private static int SPLASH_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ScreenSplash.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_OUT);
    }
}
