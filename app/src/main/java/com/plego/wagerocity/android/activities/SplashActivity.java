package com.plego.wagerocity.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.plego.wagerocity.R;
import com.plego.wagerocity.utils.AndroidUtils;


import roboguice.activity.RoboFragmentActivity;


public class SplashActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AndroidUtils.printFBKeyHash(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
