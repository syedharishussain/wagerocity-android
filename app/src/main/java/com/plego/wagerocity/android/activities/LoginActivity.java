package com.plego.wagerocity.android.activities;

import android.os.Bundle;
import android.util.Log;

import com.facebook.SessionState;
import com.plego.wagerocity.R;
import com.facebook.Session;
import com.plego.wagerocity.utils.AndroidUtils;

import roboguice.activity.RoboFragmentActivity;

public class LoginActivity extends RoboFragmentActivity implements Session.StatusCallback {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidUtils.printFBKeyHash(this);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {

    }
}
