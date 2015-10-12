package com.plego.wagerocity.android.activities;

import android.content.*;
import android.net.Uri;
import android.os.*;
import android.support.v4.app.*;
import android.util.Log;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.android.vending.billing.IInAppBillingService;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.*;
import com.plego.wagerocity.android.fragments.*;
import com.plego.wagerocity.android.gcm.RegistrationIntentService;
import com.plego.wagerocity.android.model.*;
import com.plego.wagerocity.android.util.*;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;
import com.sromku.simple.fb.*;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.RoboGuice;
import roboguice.activity.RoboFragmentActivity;
import roboguice.event.EventManager;
import roboguice.inject.RoboInjector;

import java.util.ArrayList;
import java.util.Collections;

public class DashboardActivity
        extends RoboFragmentActivity
        implements BillingProcessor.IBillingHandler,
        NavigationBarFragment.OnNavigationBarFragmentInteractionListener,
        StatsFragment.OnStatsFragmentInteractionListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
        GetDollarsFragment.OnGetDollarsFragmentInteractionListener,
        LeaderBoardListFragment.OnLeaderboardListFragmentInteractionListener,
        PoolsFragment.OnPoolsFragmentInteractionListener,
        InteractionListener,
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
        PicksOfPlayerFragment.OnPicksOfPlayerFragmentInteractionListener,
        PicksOfPlayerAdapter.OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener,
        MyPoolsListAdapter.OnMyPoolsListAdapterFragmentInteractionListener,
        MyPoolDetailFragment.OnMyPoolDetailFragmentInteractionListener,
        MyPicksListAdapter.OnMyPickShareInteractionListener {

	public static final String TAG = DashboardActivity.class.getSimpleName();
	public boolean shouldShare;
	SweetAlertDialog     pDialog;
	SimpleFacebook       simpleFacebook;
	OnPublishListener    onPublishListener;
	BillingProcessor     bp;
	ArrayList<Pick>      showPurchasePicks;
	EventManager         eventManager;
	IabHelper            mHelper;
	IInAppBillingService mService;
	ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected (ComponentName name) {
			mService = null;
		}

		@Override
		public void onServiceConnected (ComponentName name,
										IBinder service) {
			mService = IInAppBillingService.Stub.asInterface( service );
		}
	};

	private static int REQUEST_GOOGLE_PLAY_RESOLVE = 100;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_dashboard );

		bp = new BillingProcessor( this, this.getString( R.string.in_app_billing_public_key ), this );

		addNavigationBarFragment();
		addStatsFragment();
		addDashboardFragment();

		String facebokoID = new WagerocityPref( getApplicationContext() ).facebookID();
		if (facebokoID != null) {
			Log.e( "FACEBOOKID", facebokoID );
		}

		Permission[] permissions = new Permission[]{
				Permission.BASIC_INFO,
				Permission.USER_ABOUT_ME,
				Permission.EMAIL,
				Permission.PUBLISH_ACTION
		};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId( String.valueOf( R.string.app_id ) )
				.setNamespace( "Wagerocity" )
				.setPermissions( permissions )
				.build();

		SimpleFacebook.setConfiguration( configuration );

		onPublishListener = new OnPublishListener() {
			@Override
			public void onComplete (String postId) {
				Log.i( "Facebook Post Tag", "Published successfully. The new post id = " + postId );
			}

			@Override
			public void onException (Throwable throwable) {
				super.onException( throwable );
			}

			@Override
			public void onFail (String reason) {
				Log.e( "Facebook Post Failed!", "Publish failed = " + reason );
				super.onFail( reason );
			}
		};

		final RoboInjector injector = RoboGuice.getInjector( this );
		eventManager = injector.getInstance( EventManager.class );

		mHelper = new IabHelper( this, getString( R.string.in_app_billing_public_key ) );

		mHelper.startSetup( new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished (IabResult result) {
				if (!result.isSuccess()) {
					Log.d( "IabHelper Setup", "In-app Billing setup failed: " +
											  result );

				} else {
					Log.d( "IabHelper Setup", "In-app Billing is set up OK" );
					mHelper.queryInventoryAsync( mReceivedInventoryListener );
				}
			}
		} );


		Intent serviceIntent = new Intent( "com.android.vending.billing.InAppBillingService.BIND" );
		serviceIntent.setPackage( "com.android.vending" );
		bindService( serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE );

		Log.d( TAG, "Check google play services" );
		if (checkPlayServices()) {
			Log.d( TAG, "Starting registeration service" );
			Intent intent = new Intent( this, RegistrationIntentService.class );
			startService( intent );
		}
	}

	private boolean checkPlayServices () {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable( this );
		if (resultCode != ConnectionResult.SUCCESS) {
			if (apiAvailability.isUserResolvableError( resultCode )) {
				apiAvailability.getErrorDialog(this, resultCode, REQUEST_GOOGLE_PLAY_RESOLVE)
							   .show();
			} else {
				Log.i( TAG, "This device is not supported." );
				AndroidUtils.showDialog( "Google play services not found",
										 "Push notification and other dependent services might not work properly",
										 SweetAlertDialog.WARNING_TYPE, this );
				finish();
			}
			return false;
		}
		return true;
	}

	private void checkForPurchases () {

	}

	@Override
	protected void onResume () {
		super.onResume();
		simpleFacebook = SimpleFacebook.getInstance( this );
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {

		if (!mHelper.handleActivityResult(requestCode,
										  resultCode, data)) {
			super.onActivityResult( requestCode, resultCode, data );
		}

		simpleFacebook.onActivityResult(this, requestCode, resultCode, data);


		if (!bp.handleActivityResult(requestCode, resultCode, data))
			super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i("In App Billing", "On Activity Result in Activity Request Code:" + requestCode);
//        eventManager.fire(new OnActivityResultEvent(requestCode, resultCode, data));
//
//        super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		if (bp != null)
			bp.release();

		if (mHelper != null)
			mHelper.dispose();

		if (mService != null) {
			unbindService(mServiceConn);
		}

		mHelper = null;

		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			getSupportFragmentManager().popBackStack();

//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//                hideBackButton();
//            }

		} else {
			super.onBackPressed();
		}
	}

	private void showBackButton() {
		NavigationBarFragment fragment = (NavigationBarFragment) AndroidUtils.getFragmentByTag(this, StringConstants.TAG_FRAG_NAVIGATION);

		fragment.showBackButton( true );
	}

	private void hideBackButton() {
		NavigationBarFragment fragment = (NavigationBarFragment) AndroidUtils.getFragmentByTag(this, StringConstants.TAG_FRAG_NAVIGATION);

		fragment.showBackButton( false );
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
	public void onNavigationBarGoHomeFragmentInteraction() {

		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
	}

	@Override
	public void onNavigationBarGoBackFragmentInteraction() {
		this.onBackPressed();
	}

	@Override
	public void onDashboardFragmentInteraction(Uri uri) {
		if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
			replaceGetDollarsFragment();
		}

		if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {
			replaceFragment(SportsListFragment.newInstance(true, ""), StringConstants.TAG_FRAG_SPORTS_LIST);
		}

		if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {
			replaceFragment( PoolsFragment.newInstance(), StringConstants.TAG_FRAG_POOLS_LIST );
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


					Collections.sort(picks, new Pick());

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
			replaceFragment(SportsListFragment.newInstance(false, ""), StringConstants.TAG_FRAG_SPORTS_LIST);
		}

		if (uri.toString().equals(getString(R.string.uri_open_setting_fragment))) {
			replaceFragment(new SettingsFragment(), StringConstants.TAG_FRAG_SETTING);
		}
	}

	@Override
	public  void onDashboardFragmentClearRecordInteraction() {
		new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Clear Records for $1.99?")
				.setContentText("You will be charged $1.99 to clear you record.?")
				.setConfirmText("Yes do it!")
				.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sDialog) {
						sDialog.dismissWithAnimation();

						mHelper.launchPurchaseFlow(DashboardActivity.this, StringConstants.IAB_CLEAR_RECORD, 10001,
												   mPurchaseFinishedListener, StringConstants.IAB_CLEAR_RECORD);
//
//                        bp.purchase(this, StringConstants.IAB_CLEAR_RECORD);
//                        bp.consumePurchase(StringConstants.IAB_CLEAR_RECORD);

					}
				})
				.setCancelText("Cancel")
				.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog dialog) {
						dialog.cancel();
					}
				})
				.showCancelButton(true)
				.show();
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
	public void onLeaderboardListFragmentInteraction(Uri uri) {

	}

	@Override
	public void onPoolsFragmentInteraction(Uri uri, ArrayList<MyPool> pools) {
		if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
			replaceFragment(MyPoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_MY_POOLS_LIST);
		} else if (uri.toString().equals( getString( R.string.uri_open_create_pool ) )) {

			replaceFragment( CreatePoolFragment.newInstance(), StringConstants.TAG_FRAG_CREATE_POOL );
		}
	}

    @Override
    public void onPoolsFragmentInteraction (Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(MyPoolsFragment.newInstance(), StringConstants.TAG_FRAG_MY_POOLS_LIST);
        }
    }

	}
    @Override
    public void goBack () {
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void closeCurrentAndDisplayMyPool (Uri uri) {
        getSupportFragmentManager().popBackStackImmediate();
        if (uri.toString().equals( getString( R.string.uri_open_my_pools_fragment ) )) {
            replaceFragment( MyPoolsFragment.newInstance(), StringConstants.TAG_FRAG_MY_POOLS_LIST );
        }
    }

	@Override
	public void onExpertsFragmentInteraction(Uri uri) {

	}

	@Override
	public void onSportsListFragmentInteraction(Uri uri, ArrayList<Game> games, String sportsNameValueForParam, String poolId) {
		if (uri.toString().equals(getString(R.string.uri_open_games_list_fragment))) {
			replaceFragment(GamesListFragment.newInstance(games, sportsNameValueForParam, poolId), StringConstants.TAG_FRAG_GAMES_LIST);
		}
	}

	@Override
	public void onSportsListLeaderbaordsFragmentInteraction(Uri uri, ArrayList<LeaderboardPlayer> leaderboardPlayers, String sportsNameValueForParam) {
		if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {
			replaceFragment(LeaderBoardListFragment.newInstance(leaderboardPlayers), StringConstants.TAG_FRAG_LEADERBOARD_LIST);
		}
	}

	@Override
	public void onGamesListFragmentInteraction(Uri uri, ArrayList<OddHolder> oddHolders, String poolId) {

		if (uri.toString().equals(getString(R.string.uri_selected_game_for_betting))) {

			replaceFragment(BetOnGameFragment.newInstance(oddHolders, poolId), StringConstants.TAG_FRAG_BET_ON_GAME);
		}
	}

	@Override
	public void onGamesListAdapterFragmentInteraction(Uri uri, Game game) {
		if (uri.toString().equals(getString(R.string.uri_selected_game_for_betting))) {
//            Log.e("Select Game", game.getTeamAName());
//            replaceFragment(BetOnGameFragment.newInstance(game), StringConstants.TAG_FRAG_BET_ON_GAME);
		}
	}

	@Override
	public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks) {
		if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
			replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
		}
	}

	@Override
	public void onBetOnGameGoBackFragmentInteraction() {
		this.onBackPressed();
	}

	@Override
	public void onBetOnGameShareFragmentInteraction(Feed feed) {
		simpleFacebook.publish(feed, true, onPublishListener);
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
	public void onExpertPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games) {
		if (uri.toString().equals(getString(R.string.uri_open_picks_of_player_fragment))) {
			replaceFragment(PicksOfPlayerFragment.newInstance(games), StringConstants.TAG_FRAG_PICKS_OF_PLAYER);
		}
	}

	@Override
	public void onPoolsListAdapterFragmentInteraction(Uri uri, ArrayList<MyPool> pools) {
		if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
			replaceFragment(MyPoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_MY_POOLS_LIST);
		}
	}

	@Override
	public void onSettingFragmentInteraction(Uri uri) {

	}

	@Override
	public void onPicksOfPlayerFragmentInteraction(Uri uri) {

	}

	@Override
	public void onPicksOfPlayerAdapterListAdapterFragmentInteraction(Uri uri, ArrayList<Pick> picks, boolean isPurchased) {
		if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
			if (isPurchased) {
				replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
			} else {
//                bp.purchase(this, StringConstants.IAB_PURCHASE_PICK);
//                bp.consumePurchase(StringConstants.IAB_PURCHASE_PICK);
				mHelper.launchPurchaseFlow(this, StringConstants.IAB_PURCHASE_PICK, 10001,
										   mPurchaseFinishedListener, StringConstants.IAB_PURCHASE_PICK);
				showPurchasePicks = new ArrayList<>(picks);
			}
		}
	}

	@Override
	public void onMyPoolsListAdapterFragmentInteraction(Uri uri, MyPool pool) {
		if (uri.toString().equals(getString(R.string.uri_open_my_pool_detail_fragment))) {
			replaceFragment(MyPoolDetailFragment.newInstance(pool), StringConstants.TAG_FRAG_MY_POOL_DETAILS);
		}
	}

	@Override
	public void onMyPoolDetailFragmentInteraction(Uri uri, ArrayList<Game> games, String leagueName, String poolId) {
		if (uri.toString().equals(getString(R.string.uri_open_games_list_fragment))) {
			replaceFragment(GamesListFragment.newInstance(games, leagueName, poolId), StringConstants.TAG_FRAG_GAMES_LIST);
		}
	}

	@Override
	public void onMyPickShareInteraction(Feed feed) {
		simpleFacebook.publish( feed, true, onPublishListener );
	}

	public void buyCreditsAPI (int credits) {

		final WagerocityPref pref = new WagerocityPref(this);
		final User user = pref.user();

		final SweetAlertDialog pDialog = AndroidUtils.showDialog(
				getString(R.string.loading),
				null,
				SweetAlertDialog.PROGRESS_TYPE,
				this
		);

		new RestClient().getApiService().buyCredits(user.getUserId(), (float) credits, new Callback<User>() {
			@Override
			public void success(User user, Response response) {
				pDialog.dismiss();
				pref.setUser(user);
				AndroidUtils.updateStats(DashboardActivity.this);
			}

			@Override
			public void failure(RetrofitError error) {
				pDialog.dismiss();
				AndroidUtils.showErrorDialog(error, DashboardActivity.this);
			}
		});
	}

	@Override
	public void onProductPurchased (String s, TransactionDetails transactionDetails) {
		Log.i( "bp Jelly", s );

		if (transactionDetails.productId.equals( StringConstants.IAB_PURCHASE_PICK )) {

			replaceFragment( MyPicksFragment
									 .newInstance( new ArrayList<>( showPurchasePicks ) ), StringConstants.TAG_FRAG_MY_PICKS );
			showPurchasePicks = null;
		}

		if (transactionDetails.productId.equals( StringConstants.IAB_TEST )) {
			buyCreditsAPI( 2000 );
//            if (bp.isPurchased(StringConstants.IAB_TEST)) {
			bp.consumePurchase( StringConstants.IAB_TEST );
//            } else {
//
//
//
//            }
		}
	}

	@Override
	public void onPurchaseHistoryRestored () {

	}

	@Override
	public void onBillingError (int i, Throwable throwable) {

	}

	@Override
	public void onBillingInitialized () {

	}

	@Override
	public void onGetDollarsFragmentInteraction (String purchaseID) {


		mHelper.launchPurchaseFlow( this, purchaseID, 10001,
									mPurchaseFinishedListener, purchaseID );
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		@Override
		public void onIabPurchaseFinished(IabResult result, Purchase info) {
			Log.e("IabHelper + Message", result.getMessage());
			Log.e("IabHelper + Info", String.valueOf(info));

			if (result.isFailure()) {
				// Handle error

				return;
			} else if (info.getSku().equals(StringConstants.IAB_ROOKIE)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);
				buyCreditsAPI(2000);
			} else if (info.getSku().equals(StringConstants.IAB_CHASER)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);
				buyCreditsAPI(6250);
			} else if (info.getSku().equals(StringConstants.IAB_PLAYER)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);
				buyCreditsAPI(30000);
			} else if (info.getSku().equals(StringConstants.IAB_GURU)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);
				buyCreditsAPI(87500);
			} else if (info.getSku().equals(StringConstants.IAB_BAWSE)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);
				buyCreditsAPI(200000);
			} else if (info.getSku().equals(StringConstants.IAB_PURCHASE_PICK)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						replaceFragment(MyPicksFragment.newInstance(new ArrayList<>(showPurchasePicks)), StringConstants.TAG_FRAG_MY_PICKS);
					}
				}, 100);

				showPurchasePicks = null;
			} else if (info.getSku().equals(StringConstants.IAB_CLEAR_RECORD)) {
				mHelper.consumeAsync(info, mConsumeFinishedListener);

				final WagerocityPref pref = new WagerocityPref(DashboardActivity.this);

				final SweetAlertDialog pDialog = AndroidUtils.showDialog(
						"Loading",
						null,
						SweetAlertDialog.PROGRESS_TYPE,
						DashboardActivity.this
				);

				final RestClient restClient = new RestClient();
				restClient.getApiService().clearRecord(pref.user().getUserId(), new Callback<Response>() {
					@Override
					public void success(Response response, Response response2) {
						restClient.getApiService().getUser(pref.facebookID(), new Callback<User>() {
							@Override
							public void success(User user, Response response) {
								pref.setUser(user);
								AndroidUtils.updateStats(DashboardActivity.this);
								pDialog.dismiss();
							}

							@Override
							public void failure(RetrofitError error) {

							}
						});
					}

					@Override
					public void failure(RetrofitError error) {

					}
				});

			}

		}
	};

	IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

			if (result.isFailure()) {
				// Handle failure
			} else {
				ArrayList<String> skus = new ArrayList<>();
				skus.add(StringConstants.IAB_ROOKIE);
				skus.add(StringConstants.IAB_CHASER);
				skus.add(StringConstants.IAB_PLAYER);
				skus.add(StringConstants.IAB_GURU);
				skus.add(StringConstants.IAB_BAWSE);
				skus.add(StringConstants.IAB_CLEAR_RECORD);
				skus.add(StringConstants.IAB_PURCHASE_PICK);

				for (String sku : skus) {

					Purchase purchase = inventory.getPurchase(sku);
					if (purchase != null) {
						mHelper.consumeAsync(purchase, mConsumeFinishedListener);
					}
				}
			}
		}
	};

	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			new IabHelper.OnConsumeFinishedListener() {
				public void onConsumeFinished(Purchase purchase,
											  IabResult result) {

					if (result.isSuccess()) {
						Log.i("Consumed Purchase", purchase.toString());
					} else {
						// handle error
					}
				}
			};


}
