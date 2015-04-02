package com.plego.wagerocity.android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.plego.wagerocity.R;
import com.facebook.Session;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.ServiceModel;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.http.Query;
import roboguice.activity.RoboFragmentActivity;

public class LoginActivity extends RoboFragmentActivity {

    private static final String TAG = "LoginActivity";
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidUtils.printFBKeyHash(this);
        setContentView(R.layout.activity_login);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.e(TAG, "Logged in...");

            final MaterialDialog progress = new MaterialDialog.Builder(this)
                    .title(getString(R.string.loading))
                    .content(getString(R.string.please_wait))
                    .progress(true, 0)
                    .show();

            Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(final GraphUser user, final Response response) {
                    progress.dismiss();
                    Log.e("FACEBOOK", "Response : " + response);

                    final WagerocityPref pref = new WagerocityPref(getApplicationContext());
                    pref.setFacebookID(user.getId());
                    pref.setFirstName(user.getFirstName());
                    pref.setLastName(user.getLastName());

                    final RestClient restClient = new RestClient();
                    restClient.getApiService().getUser(user.getId(), new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {
                            pref.setUser(user);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    finish();
                                }
                            }, 0);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (error.getResponse().getStatus() == 404) {
                                restClient.getApiService().createUser(
                                        user.getFirstName(),
                                        user.getLastName(),
                                        user.getId(),
                                        new Callback<User>() {
                                    @Override
                                    public void success(User user, retrofit.client.Response response) {
                                        pref.setUser(user);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                finish();
                                            }
                                        }, 0);

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        }
                    });
                }
            }).executeAsync();

        } else if (state.isClosed()) {
            Log.e(TAG, "Logged out...");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
}
