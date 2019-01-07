package com.irisdame.tradepro.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.irisdame.tradepro.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        /*SharedPreferences namesPreference = getSharedPreferences("SECONDTIME", Context.MODE_PRIVATE);

        SharedPreferences.Editor namesEditor = namesPreference.edit();
        namesEditor.clear();
        namesEditor.commit();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreenActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}
