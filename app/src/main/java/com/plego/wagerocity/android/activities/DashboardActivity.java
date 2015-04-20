package com.plego.wagerocity.android.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.ExpertPlayerListAdapter;
import com.plego.wagerocity.android.adapters.GamesListAdapter;
import com.plego.wagerocity.android.adapters.LeaderboardPlayersListAdapter;
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.fragments.BetOnGameFragment;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.ExpertsFragment;
import com.plego.wagerocity.android.fragments.GamesListFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.LeaderBoardListFragment;
import com.plego.wagerocity.android.fragments.MyPicksFragment;
import com.plego.wagerocity.android.fragments.MyPoolsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.PicksOfPlayerFragment;
import com.plego.wagerocity.android.fragments.PoolsFragment;
import com.plego.wagerocity.android.fragments.SettingsFragment;
import com.plego.wagerocity.android.fragments.SportsListFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
        BetOnGameFragment.OnBetOnGameFragmentInteractionListener,
        MyPicksFragment.OnMyPicksFragmentInteractionListener,
        LeaderboardPlayersListAdapter.OnLeaderboardPlayerListAdapterFragmentInteractionListener,
        ExpertPlayerListAdapter.OnExpertPlayerListAdapterFragmentInteractionListener,
        PoolsListAdapter.OnPoolsListAdapterFragmentInteractionListener,
        SettingsFragment.OnSettingFragmentInteractionListener,
        PicksOfPlayerFragment.OnPicksOfPlayerFragmentInteractionListener {

    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();

        String facebokoID = new WagerocityPref(getApplicationContext()).facebookID();
        if (facebokoID != null) {
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

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment, StringConstants.TAG_FRAG_STATS);
        transaction.commit();
    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment, StringConstants.TAG_FRAG_DASHBOARD);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }

        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getLeaderboards(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<LeaderboardPlayer>>() {
                @Override
                public void success(ArrayList<LeaderboardPlayer> leaderboardPlayers, retrofit.client.Response response) {
                    pDialog.dismiss();
                    replaceFragment(LeaderBoardListFragment.newInstance(leaderboardPlayers), StringConstants.TAG_FRAG_LEADERBOARD_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    Log.e("getLeaderboards", String.valueOf(error));

                    AndroidUtils.showErrorDialog(error, getApplicationContext());

                }
            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getAllPools(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<Pool>>() {
                @Override
                public void success(ArrayList<Pool> pools, retrofit.client.Response response) {
                    pDialog.dismiss();
                    replaceFragment(PoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_POOLS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();
                    Log.e("getAllPools", String.valueOf(error));

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);
                }

            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_experts_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getExperts(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<ExpertPlayer>>() {
                @Override
                public void success(ArrayList<ExpertPlayer> expertPlayers, retrofit.client.Response response) {

                    pDialog.dismiss();

                    replaceFragment(ExpertsFragment.newInstance(expertPlayers), StringConstants.TAG_FRAG_EXPERTS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);

                }
            });

        }

        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getMyPicks(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<Pick>>() {
                @Override
                public void success(ArrayList<Pick> picks, Response response) {
                    pDialog.dismiss();

                    replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);
                }
            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_sports_list_fragment))) {
            replaceFragment(new SportsListFragment(), StringConstants.TAG_FRAG_SPORTS_LIST);
        }

        if (uri.toString().equals(getString(R.string.uri_open_setting_fragment))) {
            replaceFragment(new SettingsFragment(), StringConstants.TAG_FRAG_SETTING);
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
    public void onPoolsFragmentInteraction(Uri uri, ArrayList<Pool> pools) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(MyPoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_MY_POOLS_LIST);
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
    public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks) {
        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
            replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
        }
    }

    @Override
    public void onMyPicksFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLeaderboardPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games) {
        if (uri.toString().equals(getString(R.string.uri_open_picks_of_player_fragment))) {
            replaceFragment(PicksOfPlayerFragment.newInstance(games), StringConstants.TAG_FRAG_PICKS_OF_PLAYER);
        }
    }

    @Override
    public void onExpertPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Pick> picks) {
        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
            replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
        }
    }

    @Override
    public void onPoolsListAdapterFragmentInteraction(Uri uri, ArrayList<Pool> pools) {

    }

    @Override
    public void onSettingFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPicksOfPlayerFragmentInteraction(Uri uri) {

    }
}
