package com.plego.wagerocity.android.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.ExpertsFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.LeaderBoardListFragment;
import com.plego.wagerocity.android.fragments.MyPoolsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.PoolsFragment;
import com.plego.wagerocity.android.fragments.SportsListFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;

import java.util.ArrayDeque;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity
        extends RoboFragmentActivity
        implements NavigationBarFragment.OnNavigationBarFragmentInteractionListener,
        StatsFragment.OnStatsFragmentInteractionListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
        GetDollarsFragment.OnGetDollarsFragmentInteractionListener,
        LeaderBoardListFragment.OnLeaderboardListFragmentInteractionListener,
        PoolsFragment.OnPoolsFragmentInteractionListener,
        MyPoolsFragment.OnMyPoolsFragmentInteractionListener,
        ExpertsFragment.OnExpertsFragmentInteractionListener,
        SportsListFragment.OnSportsListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();

        String facebokoID = getSharedPreferences(getString(R.string.USER_FACEBOOK_ID), Context.MODE_PRIVATE).getString(getString(R.string.USER_FACEBOOK_ID),null);
        if (facebokoID!= null) {
            Log.v("FACEBOOKID", facebokoID);
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void addNavigationBarFragment() {
        NavigationBarFragment navigationBarFragment = new NavigationBarFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment);
        transaction.commit();
    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {

    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment);
        transaction.commit();
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }

        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {

            RestClient restClient = new RestClient();
            restClient.getApiService().getLeaderboards(new Callback<ArrayList<LeaderboardPlayer>>() {
                @Override
                public void success(ArrayList<LeaderboardPlayer> leaderboardPlayers, retrofit.client.Response response) {
                    replaceFragment(new LeaderBoardListFragment(leaderboardPlayers));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("getLeaderboards", String.valueOf(error));
                }
            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {

            RestClient restClient = new RestClient();
            restClient.getApiService().getAllPools(new Callback<ArrayList<Pool>>() {
                @Override
                public void success(ArrayList<Pool> pools, retrofit.client.Response response) {

                    replaceFragment(new PoolsFragment(pools));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("getAllPools", String.valueOf(error));
                }

            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_experts_fragment))) {

            RestClient restClient = new RestClient();
            restClient.getApiService().getExperts(new Callback<ArrayList<ExpertPlayer>>() {
                @Override
                public void success(ArrayList<ExpertPlayer> expertPlayers, retrofit.client.Response response) {
                    replaceFragment(new ExpertsFragment(expertPlayers));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("getExperts", String.valueOf(error));
                }
            });

        }

        if (uri.toString().equals(getString(R.string.uri_open_sports_list_fragment))) {
            replaceFragment(new SportsListFragment());
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

    @Override
    public void onLeaderboardListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPoolsFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(new MyPoolsFragment());
        }
    }

    @Override
    public void onMyPoolsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExpertsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSportsListFragmentInteraction(Uri uri) {

    }
}
