package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.google.gson.Gson;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.activities.DashboardActivity;
import com.plego.wagerocity.android.adapters.BetSlipAdapter;
import com.plego.wagerocity.android.model.*;
import com.plego.wagerocity.utils.AndroidUtils;
import com.plego.wagerocity.utils.UiUtils;
import com.sromku.simple.fb.entities.Feed;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.BetOnGameFragment.OnBetOnGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BetOnGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BetOnGameFragment extends Fragment {

    public static final  String POINT_SPREAD = "PointSpread";
    public static final  String SINGLE       = "single";
    public static final  String MONEY_LINE   = "MoneyLine";
    public static final  String OVER         = "Over";
    public static final  String UNDER        = "Under";
    public static final  String PARLAY       = "parlay";
    public static final  String TEASER       = "teaser";
    public static final  String TAG          = BetOnGameFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAME    = "selected_game";
    private static final String ARGS_POOL_ID = "Pool_ID";
    boolean hasParlay = false;

    @Inject
    ServiceModel   serviceModel;
    @Inject
    UiUtils        uiUtils;
    @Inject
    WagerocityPref prefs;
    private ArrayList<OddHolder>                   oddHolders;
    private String                                 poolId;
    private OnBetOnGameFragmentInteractionListener mListener;
    private OddHolder                              shareOdd;
    private SweetAlertDialog                       progressAlert;


    public BetOnGameFragment () {
        // Required empty public constructor
    }

    public static BetOnGameFragment newInstance (ArrayList<OddHolder> oddHolders, String poolId) {
        BetOnGameFragment fragment = new BetOnGameFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList( ARGS_GAME, oddHolders );
        args.putString( ARGS_POOL_ID, poolId );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        if (getArguments() != null) {
            oddHolders = getArguments().getParcelableArrayList( ARGS_GAME );
            poolId = getArguments().getString( ARGS_POOL_ID );
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fragment_bet_on_game, container, false );
        ButterKnife.bind( this, view );
        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.unbind( this );
    }

    @Override
    public void onViewCreated (final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated( view, savedInstanceState );
        WagerocityApplication.component( getActivity() ).inject( this );
        uiUtils.setContext( getActivity() );

        if (oddHolders.size() == 0) {
            mListener.onBetOnGameGoBackFragmentInteraction();
        }

        if (oddHolders.size() > 1) {
            createParlayOdd();
            if (oddHolders.get(0).getLeagueName().equals("nfl") || oddHolders.get(0).getLeagueName().equals("nba"))
                if (oddHolders.size() != 2 &&
                        !((oddHolders.get(0).getBetOT().equals("1") && oddHolders.get(1).getBetOT().equals("3")) ||
                                (oddHolders.get(0).getBetOT().equals("3") && oddHolders.get(1).getBetOT().equals("1")) ||
                                (oddHolders.get(0).getBetOT().equals("1") && oddHolders.get(1).getBetOT().equals("4")) ||
                                (oddHolders.get(0).getBetOT().equals("4") && oddHolders.get(1).getBetOT().equals("1"))))
                    createTeaserOdd();
        }

        final ListView betSlipListView = (ListView) view.findViewById(R.id.listview_betslips);

        final BetSlipAdapter betSlipAdapter = new BetSlipAdapter(oddHolders, view.getContext());

        betSlipListView.setAdapter( betSlipAdapter );
    }

    @OnClick(R.id.button_cell_betongame_place_bet)
    public void placeBet () {
        for (OddHolder oddHolder : oddHolders) {
            if (oddHolder.getIsChecked()) {
                shareOdd = oddHolder;
            }
        }

        if (shareOdd != null) {
            final OddHolder finalOdd = shareOdd;
            new SweetAlertDialog( getActivity(), SweetAlertDialog.WARNING_TYPE )
                    .setTitleText( "Share This With Your Friends!" )
                    .setContentText( "Earn $250 and Share This on Facebook With Your Friends." )
                    .setConfirmText( "Yes do it!" )
                    .setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick (SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            startBetProcessor();
                            ((DashboardActivity) getActivity()).shouldShare = true;
                            buyCreditsAPI( (float) 250.00 );
                        }
                    } )
                    .setCancelText( "Cancel" )
                    .setCancelClickListener( new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick (SweetAlertDialog dialog) {
                            dialog.cancel();
                            startBetProcessor();
                        }
                    } )
                    .showCancelButton( true )
                    .show();
        } else {
            startBetProcessor();
        }
    }

    private String md5Secret (String... strings) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append( s );
        }
        MessageDigest md5 = MessageDigest.getInstance( "MD5" );
        md5.update( builder.toString().getBytes(), 0, builder.length() );
        byte[] digest = md5.digest();

        return new BigInteger( 1, digest ).toString( 16 );
    }

    private void sahreBet(OddHolder oddHolder) {

        String name = oddHolder.getTeamName();
        String caption = "I have put my stakes " + "$" + oddHolder.getRiskValue() + " on " + oddHolder.getTeamName() + " " + oddHolder.getBetTypeString() + " " + oddHolder.getOddValue();


        Feed feed = new Feed.Builder()
//                            .setMessage("message: " + caption)
                .setName(name)
//                            .setCaption(caption)
                .setDescription(caption)
                .setPicture("https://www.wagerocity.com/user_data/images/logo1.png")
                .setLink("https://www.wagerocity.com")
                .build();
        mListener.onBetOnGameShareFragmentInteraction(feed);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void createParlayOdd() {

        if (oddHolders.size() == 2)
            if (oddHolders.get(0).getTeamId().equals(oddHolders.get(1).getTeamId()))
                if ((oddHolders.get(0).getBetOT().equals("1") && oddHolders.get(1).getBetOT().equals("3")) ||
                        (oddHolders.get(0).getBetOT().equals("3") && oddHolders.get(1).getBetOT().equals("1")))

                    return;

        if (oddHolders.size() > 1) {

            String teamId = null;
            String oddId = null;
            String teams = "";

            double parlayValue = 1.0;
            for (OddHolder oddHolder : oddHolders) {
                oddId = oddHolder.getOddId();
                teamId = oddHolder.getTeamId();
                teams = teams + " - " + oddHolder.getTeamName();
                double oddValue = Double.parseDouble(oddHolder.getOddValue());
                if (oddValue > 0) {
                    Double multipliyer = (oddValue + 100.0) / 100.00;
                    parlayValue = parlayValue * multipliyer;
                } else {
                    Double multipliyer = (100.0 + Math.abs(oddValue)) / Math.abs(oddValue);
                    parlayValue = parlayValue * multipliyer;
                }
            }

            parlayValue = parlayValue - 1;

            DecimalFormat f = new DecimalFormat("##.00");

            parlayValue = Double.parseDouble(f.format(parlayValue));

            OddHolder oddHolder = new OddHolder();
            oddHolder.setTeamId(teamId);
            oddHolder.setOddId(oddId);
            oddHolder.setTeamName("Parlay");
            oddHolder.setTeamVsteam(teams + " ( " + oddHolders.size() + " teams )");
            oddHolder.setOddValue("");
            oddHolder.setBetTypeSPT(PARLAY);
            oddHolder.setBetOT("1");
            oddHolder.setBetTypeString("");
            oddHolder.setPointSpreadString("");
            oddHolder.setIsChecked(false);
            oddHolder.setRiskValue("0");
            oddHolder.setParlayValue(parlayValue);
            oddHolder.setLeagueName(oddHolders.get(0).getLeagueName());

            oddHolders.add(oddHolder);

            hasParlay = true;
        }
    }

    private void createTeaserOdd() {

        ArrayList<Map> teasersValuesForSport = getTeasersValuesForSport();

        int count = 0;

        for (OddHolder oddHolder : oddHolders) {
            if (oddHolder.getBetOT().equals("3") || oddHolder.getBetOT().equals("4")) {
                count++;
            }
        }

        if (count > 0 && count < 7) {


            Map map = teasersValuesForSport.get(count - 2);

            String firstTeaserValue = ((Integer) map.get(oddHolders.get(0).getLeagueName().equals("nfl") ? 6.0 : 4.0)).toString();

            OddHolder oddHolder = new OddHolder();
            oddHolder.setTeamId(oddHolders.get(0).getTeamId());
            oddHolder.setOddId(oddHolders.get(0).getOddId());
            oddHolder.setTeamName("Teaser");
            oddHolder.setTeamVsteam( oddHolders.get( 0 ).getLeagueName()
                    .equals( "nfl" ) ? "+6.0 pts " : "+4.0 pts " + firstTeaserValue + "( " + count + " teams )" );
            oddHolder.setOddValue( "" );
            oddHolder.setBetTypeSPT( TEASER );
            oddHolder.setBetOT( "1" );
            oddHolder.setBetTypeString( "" );
            oddHolder.setPointSpreadString( "" );
            oddHolder.setIsChecked( false );
            oddHolder.setRiskValue( "0" );
            oddHolder.setTeaserString( oddHolders.get( 0 ).getLeagueName()
                    .equals( "nfl" ) ? "+6.0 pts " : "+4.0 pts " + firstTeaserValue + "( " + count + " teams )" );
            oddHolder.setOddValue( firstTeaserValue );
            oddHolder.setLeagueName( oddHolders.get( 0 ).getLeagueName() );
            oddHolder.setTeaser1( (Integer) map.get( oddHolders.get( 0 ).getLeagueName().equals( "nfl" ) ? 6.0 : 4.0 ) );
            oddHolder.setTeaser2( (Integer) map.get( oddHolders.get( 0 ).getLeagueName().equals( "nfl" ) ? 6.5 : 4.5 ) );
            oddHolder.setTeaser3( (Integer) map.get( oddHolders.get( 0 ).getLeagueName().equals( "nfl" ) ? 7.0 : 5.0 ) );

            oddHolders.add(oddHolder);
        }

    }

    private ArrayList<Map> getTeasersValuesForSport() {
        ArrayList<Map> arraylist = new ArrayList<>();

        if (oddHolders.get(0).getLeagueName().equals("nfl")) {

            Map<Double, Integer> map = new HashMap<>();
            map.put(6.0, -110);
            map.put(6.5, -120);
            map.put(7.0, -130);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(6.0, 180);
            map.put(6.5, 160);
            map.put(7.0, 140);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(6.0, 300);
            map.put(6.5, 250);
            map.put(7.0, 200);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(6.0, 450);
            map.put(6.5, 400);
            map.put(7.0, 350);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(6.0, 600);
            map.put(6.5, 550);
            map.put(7.0, 500);

            arraylist.add(map);
        } else if (oddHolders.get(0).getLeagueName().equals("nba")) {
            Map<Double, Integer> map = new HashMap<>();
            map.put(4.0, -110);
            map.put(4.5, -120);
            map.put(5.0, -130);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(4.0, 180);
            map.put(4.5, 160);
            map.put(5.0, 140);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(4.0, 300);
            map.put(4.5, 250);
            map.put(5.0, 200);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(4.0, 450);
            map.put(4.5, 400);
            map.put(5.0, 350);

            arraylist.add(map);

            map = new HashMap<>();
            map.put(4.0, 750);
            map.put(4.5, 700);
            map.put(5.0, 650);

            arraylist.add(map);
        }

        return arraylist;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBetOnGameFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBetOnGameFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void startBetProcessor () {
        progressAlert = uiUtils.showProgressAlert( "Validating Bet.." );
        BetProcessor betProcessor = new BetProcessor( oddHolders );
        if (betProcessor.validateBet()) {
            progressAlert.dismissWithAnimation();
            progressAlert = uiUtils.showProgressAlert( "Placing Bet.." );
            betProcessor.process();
        }
    }

    void processBet () {
        final RestClient restClient = new RestClient();

        final SweetAlertDialog pDialog = new SweetAlertDialog( getActivity(), SweetAlertDialog.PROGRESS_TYPE );
        pDialog.getProgressHelper().setBarColor( Color.parseColor( "#A5DC86" ) );
        pDialog.setTitleText( "Placing Bet.." );
        pDialog.setCancelable( false );

        final ArrayList<String> arrayList = new ArrayList<>();

        for (final OddHolder oddHolder : oddHolders) {
            if (!oddHolder.getRiskValue().equals( "0" ) && oddHolder.getIsChecked()) {

                Float credits = new WagerocityPref( this.getActivity() ).user().getCredits();

                if (poolId != null) {
                    if (poolId.equals( "0" ) || poolId.equals( "" )) {
                        if (credits < Float.parseFloat( oddHolder.getRiskValue() )) {
                            AndroidUtils
                                    .showDialog( "Not Enough Credits", "You do not have enough credits to place this bet.", SweetAlertDialog.ERROR_TYPE, this
                                            .getActivity() );
                            continue;
                        }
                    } else {
                        if (oddHolder.getPoolCredits() < Float.parseFloat( oddHolder.getRiskValue() )) {
                            AndroidUtils
                                    .showDialog( "Not Enough Pool Credits", "You do not have enough pool credits to place this bet.", SweetAlertDialog.ERROR_TYPE, this
                                            .getActivity() );
                            continue;
                        }
                    }
                }


                if (!pDialog.isShowing()) pDialog.show();

                final String userId = new WagerocityPref( getActivity() ).user().getUserId();
                final String oddId = oddHolder.getOddId();
                final String oddValue = oddHolder.getBetTypeSPT().equals( PARLAY ) ? oddHolder.getParlayValue()
                        .toString() : oddHolder.getOddValue();
//                final String position = oddHolder.getBetTypeSPT().equals(SINGLE) ? oddHolder.getBetTypeString().equals(OVER) || oddHolder.getBetTypeString().equals(UNDER) ? oddHolder.getBetTypeString().toLowerCase() : "-" : "-";
                final String position = oddHolder.isTeamA() ? "over" : "under";
                final String matchDetail = oddHolder.getTeamVsteam();
                final String oddType = "ao";
                final String stake = oddHolder.getRiskValue();
                final String teamId = oddHolder.getTeamId();
                final String teamName = oddHolder.getTeamName();
                final String leagueName = oddHolder.getLeagueName();
                final String betType = oddHolder.getBetTypeSPT();
                final String betOt = oddHolder.getBetOT();
                final String poolID = (poolId == null || poolId.equals( "" )) ? "" : poolId;
                String parentId = "";

                arrayList.add( "http://api.wagerocity.com/betOnGame" );

                restClient.getApiService().getBetParent( new Callback<BetParent>() {
                    @Override
                    public void success (BetParent betParent, Response response) {
                        restClient.getApiService().betOnGames(
                                userId,                     // userId
                                oddId,                      // oddId
                                oddValue,                   // oddValue
                                position,                   // position: over | under
                                matchDetail,                // matchDetail
                                oddType,                    // oddType: ao
                                stake,                      // stake
                                teamId,                     // teamId
                                teamName,                   // teamname
                                leagueName,                 // leagueName
                                betType,                    // betType: Single, Parlay, Teaser
                                betOt,                      // betOT: 1 moneyline, 3 pointspread, 4 over|under
                                betParent.getBetParent(),   // betParent
                                poolID,   // isPoolBet
                                "pending",
                                "pending",
                                new Callback<ArrayList<Pick>>() {
                                    @Override
                                    public void success (ArrayList<Pick> picks, Response response) {

                                        arrayList.remove( "http://api.wagerocity.com/betOnGame" );

//                                        pDialog.getProgressHelper().setProgress(pDialog.getProgressHelper().getProgress() + progressSize);

                                        if (arrayList
                                                .size() == 0) {// (pDialog.getProgressHelper().getProgress() >= 97.0) {


                                            restClient.getApiService()
                                                    .getMyPicks( userId, new Callback<ArrayList<Pick>>() {
                                                        @Override
                                                        public void success (ArrayList<Pick> picks, Response response) {
                                                            pDialog.dismiss();

                                                            oddHolders.removeAll( oddHolders );
                                                            Collections.sort( picks, new Pick() );
                                                            Uri uri = Uri
                                                                    .parse( getString( R.string.uri_open_my_picks_fragment ) );
                                                            mListener.onBetOnGameFragmentInteraction( uri, picks );
                                                            boolean shouldShare = ((DashboardActivity) getActivity()).shouldShare;

                                                            if (shareOdd != null) {
                                                                if (shouldShare) {
                                                                    sahreBet( shareOdd );
                                                                    ((DashboardActivity) getActivity()).shouldShare = false;
                                                                    shareOdd = null;
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void failure (RetrofitError error) {
                                                            pDialog.dismiss();
                                                            Log.e( "Picks Posting Error", error.toString() );
                                                        }
                                                    } );

                                            Log.e( "Picks Posting", "Completed" );

                                        }
                                        Log.e( "Picks Posting", String
                                                .valueOf( pDialog.getProgressHelper().getProgress() ) );
                                    }

                                    @Override
                                    public void failure (RetrofitError error) {
                                        pDialog.dismiss();
                                    }
                                }
                        );

                        if (poolId != null) {
                            if (poolId.equals( "0" ) || poolId.equals( "" )) {
                                asyncConsumeCredits( stake );
                            }
                        }
                    }

                    @Override
                    public void failure (RetrofitError error) {

                    }
                } );
            }
        }
    }

    private void asyncConsumeCredits (String stake) {
        serviceModel.consumeCredits( new WagerocityPref( getActivity() ).user().getUserId(),
                Float.parseFloat( stake ),
                new Callback<User>() {
                    @Override
                    public void success (User user, Response response) {
                        Log.e( "Credits Consumed", user.getCredits().toString() );
                        new WagerocityPref( getActivity() ).setUser( user );

                        AndroidUtils.updateStats( getActivity() );
                    }

                    @Override
                    public void failure (RetrofitError error) {

                    }
                } );
    }

    public void buyCreditsAPI(Float credits) {

        final WagerocityPref pref = new WagerocityPref(getActivity());
        final User user = pref.user();

        new RestClient().getApiService().buyCredits(user.getUserId(), credits, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                pref.setUser(user);
                AndroidUtils.updateStats(getActivity());
            }

            @Override
            public void failure(RetrofitError error) {
                AndroidUtils.showErrorDialog(error, getActivity());
            }
        });
    }

    public interface OnBetOnGameFragmentInteractionListener {

        public void onBetOnGameFragmentInteraction (Uri uri, ArrayList<Pick> picks);

        public void onBetOnGameGoBackFragmentInteraction ();

        public void onBetOnGameShareFragmentInteraction (Feed feed);
    }

    private class BetProcessor implements Callback<Response> {

        private List<OddHolder> oddHolders;
        private int currentIndex = 0;
        private float riskValue;

        public BetProcessor (List<OddHolder> oddHolders) {
            this.oddHolders = oddHolders;
            calculateStakeAmount();
        }

        private void calculateStakeAmount () {
            riskValue = 0;
            if (oddHolders == null) return;
            for (OddHolder oddHolder : oddHolders) {
                riskValue += Float.parseFloat( oddHolder.getRiskValue() );
            }
        }

        private boolean validateBet () {
            Float credits = prefs.user().getCredits();
            if (credits < riskValue) {
                if (progressAlert != null && progressAlert.isShowing()) {
                    progressAlert.dismiss();
                }
                uiUtils.showDialog( "Not Enough Credits", "You do not have enough credits to place this bet.", SweetAlertDialog.ERROR_TYPE );
                return false;
            }

            if (!AndroidUtils.isEmpty( poolId )) {
                for (OddHolder oddHolder : oddHolders) {
                    if (oddHolder.getPoolCredits() < Float.parseFloat( oddHolder.getRiskValue() )) {
                        if (progressAlert != null && progressAlert.isShowing()) {
                            progressAlert.dismiss();
                        }
                        uiUtils.showDialog( "Not Enough Pool Credits", "You do not have enough pool credits to place this " +
                                "bet.", SweetAlertDialog.ERROR_TYPE );
                        return false;
                    }
                }
            }

            return true;
        }

        private void process () {
            final String userId = new WagerocityPref( getActivity() ).user().getUserId();
            final Gson gson = new Gson();
            int numBets = oddHolders.size();
            OddHolder oddHolder = oddHolders.get( currentIndex );
            final BetRequest betRequest = new BetRequest();
            betRequest.setId( oddHolder.getProcessedOddId() );
            betRequest.setPoolId( poolId );
            betRequest.setMatchId( oddHolder.getProcessedMatchId() );
            betRequest.setIsPoolBet( AndroidUtils.isEmpty( poolId ) ? "0" : "1" );
            betRequest.setPoolName( "" );
            betRequest.setPos( oddHolder.getProcessedPos() );
            betRequest.setOddType( oddHolder.getProcessedOddType() );
            betRequest.setMatchDetail( oddHolder.getTeamVsteam() );
            betRequest.setInputStake( "no" );
            betRequest.setIsBetChecked( oddHolder.getIsChecked() ? "yes" : "no" );
            betRequest.setOddsValue( oddHolder.getBetTypeSPT().equals( PARLAY ) ? oddHolder.getParlayValue()
                    .toString() : oddHolder.getOddValue() );
            betRequest.setStake( oddHolder.getRiskValue() );
            betRequest.setDetailedTeamName( oddHolder.getTeamName() + " " + betRequest.getOddsValue() );
            betRequest.setOddTypeInt( oddHolder.getProcessedBetOT() );
            betRequest.setMatchCondition( "" );
            betRequest.setSportsName( oddHolder.getLeagueName() );
            betRequest.setUserId( userId );
            betRequest.setPlaceBetType( "single_bet" );
            betRequest.setNumBets( String.valueOf( numBets ) );

            serviceModel.betOnGames( betRequest, this );
            currentIndex += 1;
            Log.d( TAG, "Request--> " + gson.toJson( betRequest ) );
        }

        @Override
        public void success (Response response, Response response2) {
            Log.d( TAG, "Successfully processed bet @ " + currentIndex + " with response " +
                    response.getReason() + " <------->" +
                    response.getStatus() + " <------->" + response.getBody() );
            if (currentIndex < oddHolders.size()) {
                process();
            } else {
                Log.d( TAG, "All bets processed" );
                String userId = prefs.user().getUserId();
                serviceModel.getMyPicks( userId, new Callback<ArrayList<Pick>>() {
                    @Override
                    public void success (ArrayList<Pick> picks, Response response) {
                        if (progressAlert != null && progressAlert.isShowing()) {
                            progressAlert.dismiss();
                        }

                        oddHolders.clear();
                        Collections.sort( picks, new Pick() );
                        Uri uri = Uri
                                .parse( getString( R.string.uri_open_my_picks_fragment ) );
                        mListener.onBetOnGameFragmentInteraction( uri, picks );
                        boolean shouldShare = ((DashboardActivity) getActivity()).shouldShare;

                        if (shareOdd != null) {
                            if (shouldShare) {
                                sahreBet( shareOdd );
                                ((DashboardActivity) getActivity()).shouldShare = false;
                                shareOdd = null;
                            }
                        }
                    }

                    @Override
                    public void failure (RetrofitError error) {
                        if (progressAlert != null && progressAlert.isShowing()) {
                            progressAlert.dismiss();
                        }
                        uiUtils.showErrorDialog( error );
                        Log.e( "Picks Posting Error", error.toString() );
                    }
                } );
            }
            asyncConsumeCredits( oddHolders.get( currentIndex - 1 ).getRiskValue() );
        }

        @Override
        public void failure (RetrofitError error) {
            if (progressAlert != null && progressAlert.isShowing()) {
                progressAlert.dismiss();
            }
            uiUtils.showErrorDialog( error );
            Log.e( TAG, "Failed to process bet @ " + currentIndex, error );
        }
    }
}

