package com.plego.wagerocity.android.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;

import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
//        addStatsFragment();
    }

    private void addNavigationBarFragment() {
        NavigationBarFragment navigationBarFragment = new NavigationBarFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
