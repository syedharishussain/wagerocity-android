package com.plego.wagerocity.android.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;

import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity
        extends RoboFragmentActivity
        implements NavigationBarFragment.OnNavigationBarFragmentInteractionListener,
        StatsFragment.OnStatsFragmentInteractionListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
GetDollarsFragment.OnGetDollarsFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();
    }

    private void addNavigationBarFragment() {
        NavigationBarFragment navigationBarFragment = new NavigationBarFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {

    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }
    }

    private void replaceGetDollarsFragment() {
        replaceFragment(new GetDollarsFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_dashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGetDollarsFragmentInteraction(Uri uri) {

    }
}
