package com.plego.wagerocity.android.activities;

import android.os.Bundle;

import com.plego.wagerocity.R;

import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

}
