package com.plego.wagerocity.android.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.GamesListAdapter;
import com.plego.wagerocity.android.fragments.BetOnGameFragment;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.ExpertsFragment;
import com.plego.wagerocity.android.fragments.GamesListFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.LeaderBoardListFragment;
import com.plego.wagerocity.android.fragments.MyPoolsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.PoolsFragment;
import com.plego.wagerocity.android.fragments.SportsListFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.constants.StringConstants;

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
        SportsListFragment.OnSportsListFragmentInteractionListener,
        GamesListFragment.OnGamesListFragmentInteractionListener,
        GamesListAdapter.OnGamesListAdapterFragmentInteractionListener,
        BetOnGameFragment.OnBetOnGameFragmentInteractionListener {

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();

        String facebokoID = new WagerocityPref(getApplicationContext()).facebookID();
        if (facebokoID!= null) {
            Log.e("FACEBOOKID", facebokoID);
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
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment, StringConstants.TAG_FRAG_NAVIGATION);
        transaction.commit();
    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {

    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment, StringConstants.TAG_FRAG_STATS);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment, StringConstants.TAG_FRAG_DASHBOARD);
        transaction.commit();
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }

        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {

            final MaterialDialog progress = new MaterialDialog.Builder(this)
                    .title(getString(R.string.loading))
                    .content(getString(R.string.please_wait))
                    .progress(true, 0)
                    .show();

            RestClient restClient = new RestClient();
            restClient.getApiService().getLeaderboards(new Callback<ArrayList<LeaderboardPlayer>>() {
                @Override
                public void success(ArrayList<LeaderboardPlayer> leaderboardPlayers, retrofit.client.Response response) {
                    progress.dismiss();
                    replaceFragment(LeaderBoardListFragment.newInstance(leaderboardPlayers), StringConstants.TAG_FRAG_LEADERBOARD_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    progress.dismiss();
                    Log.e("getLeaderboards", String.valueOf(error));
                }
            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {

            final MaterialDialog progress = new MaterialDialog.Builder(this)
                    .title(getString(R.string.loading))
                    .content(getString(R.string.please_wait))
                    .progress(true, 0)
                    .show();

            RestClient restClient = new RestClient();
            restClient.getApiService().getAllPools(new Callback<ArrayList<Pool>>() {
                @Override
                public void success(ArrayList<Pool> pools, retrofit.client.Response response) {
                    progress.dismiss();
                    replaceFragment(PoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_POOLS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    progress.dismiss();
                    Log.e("getAllPools", String.valueOf(error));
                }

            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_experts_fragment))) {

            final MaterialDialog progress = new MaterialDialog.Builder(this)
                    .title(getString(R.string.loading))
                    .content(getString(R.string.please_wait))
                    .progress(true, 0)
                    .show();

            RestClient restClient = new RestClient();
            restClient.getApiService().getExperts(new Callback<ArrayList<ExpertPlayer>>() {
                @Override
                public void success(ArrayList<ExpertPlayer> expertPlayers, retrofit.client.Response response) {

                    progress.dismiss();

                    replaceFragment(ExpertsFragment.newInstance(expertPlayers), StringConstants.TAG_FRAG_EXPERTS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    progress.dismiss();
                    Log.e("getExperts", String.valueOf(error));
                }
            });

        }

        if (uri.toString().equals(getString(R.string.uri_open_sports_list_fragment))) {
            replaceFragment(new SportsListFragment(), StringConstants.TAG_FRAG_SPORTS_LIST);
        }
    }

    private void replaceGetDollarsFragment() {
        replaceFragment(new GetDollarsFragment(), StringConstants.TAG_FRAG_GET_DOLLARS);
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_dashboard, fragment, TAG);
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
            replaceFragment(new MyPoolsFragment(), StringConstants.TAG_FRAG_MY_POOLS_LIST);
        }
    }

    @Override
    public void onMyPoolsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExpertsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSportsListFragmentInteraction(Uri uri, ArrayList<Game> games, String sportsNameValueForParam) {
        if (uri.toString().equals(getString(R.string.uri_open_games_list_fragment))) {
            replaceFragment(GamesListFragment.newInstance(games, sportsNameValueForParam), StringConstants.TAG_FRAG_GAMES_LIST);
        }
    }

    @Override
    public void onGamesListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGamesListAdapterFragmentInteraction(Uri uri, Game game) {
        if (uri.toString().equals(getString(R.string.uri_selected_game_for_betting))) {
            Log.e("Select Game", game.getTeamAName());
            replaceFragment(BetOnGameFragment.newInstance(game), StringConstants.TAG_FRAG_BET_ON_GAME);
        }
    }

    @Override
    public void onBetOnGameFragmentInteraction(Uri uri) {

    }
}
