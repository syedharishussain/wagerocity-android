package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.BetSlipAdapter;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Odd;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.BetOnGameFragment.OnBetOnGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BetOnGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BetOnGameFragment extends Fragment {

    public static final String POINT_SPREAD = "PointSpread";
    public static final String SINGLE = "single";
    public static final String MONEY_LINE = "MoneyLine";
    public static final String OVER = "Over";
    public static final String UNDER = "Under";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAME = "selected_game";
    public static final String PARLAY = "parlay";
    boolean hasParlay = false;

    private ArrayList<OddHolder> oddHolders;
    private OnBetOnGameFragmentInteractionListener mListener;

    public static BetOnGameFragment newInstance(ArrayList<OddHolder> oddHolders) {
        BetOnGameFragment fragment = new BetOnGameFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_GAME, oddHolders);
        fragment.setArguments(args);
        return fragment;
    }

    public BetOnGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oddHolders = getArguments().getParcelableArrayList(ARGS_GAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bet_on_game, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        createParlayOdd();

        Button placeBet = (Button) view.findViewById(R.id.button_cell_betongame_place_bet);
        placeBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBet();
            }
        });

        final ListView betSlipListView = (ListView) view.findViewById(R.id.listview_betslips);

        final BetSlipAdapter betSlipAdapter = new BetSlipAdapter(oddHolders, view.getContext());

        betSlipListView.setAdapter(betSlipAdapter);
    }

    private void createParlayOdd() {

        if (oddHolders.size() > 1) {

            double parlayValue = 1.0;
            for (OddHolder oddHolder : oddHolders) {
                double oddValue = Double.parseDouble(oddHolder.getOddValue());
                if (oddValue > 0) {
                    Double multipliyer = (oddValue + 100.0) / 100.00;
                    parlayValue = parlayValue * multipliyer;
                } else {
                    Double multipliyer = (100.0 + Math.abs(oddValue)) / Math.abs(oddValue);
                    parlayValue = parlayValue * multipliyer;
                }
            }

//            Double stake;
//            String teamId;
//            String oddId;
//            String teamName;
//            String teamVsteam;
//            String oddValue;
//            String betTypeSPT;
//            String betOT;
//            String betTypeString;
//            String pointSpreadString;
//            Boolean isChecked;
//            String leagueName;
//            String riskValue;
//            Double parlayValue;

            OddHolder oddHolder = new OddHolder();
            oddHolder.setTeamId("");
            oddHolder.setOddId("");
            oddHolder.setTeamName("Parlay");
            oddHolder.setTeamVsteam("( " + oddHolders.size() + " teams )");
            oddHolder.setOddValue("");
            oddHolder.setBetTypeSPT(PARLAY);
            oddHolder.setBetOT("1");
            oddHolder.setBetTypeString("");
            oddHolder.setPointSpreadString("");
            oddHolder.setIsChecked(false);
            oddHolder.setRiskValue("0");
            oddHolder.setParlayValue(parlayValue);

            oddHolders.add(oddHolder);

            hasParlay = true;
        }
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

    public interface OnBetOnGameFragmentInteractionListener {

        public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks);
    }

    void postBet() {
        final ArrayList<OddHolder> oddHolders1 = new ArrayList<>();
        final RestClient restClient = new RestClient();

//                @Part("userID") String userID,
//                @Part("oddID") String oddID,
//                @Part("oddVal") String oddVal,
//                @Part("position") String position,
//                @Part("matchDetail") String matchDetail,
//                @Part("oddType") String oddType,
//                @Part("stake") String stake,
//                @Part("matchID") String matchID,
//                @Part("teamName") String teamName,
//                @Part("sportsName") String sportsName,
//                @Part("bet_type") String bet_type,
//                @Part("bet_ot") String bet_ot,

        OddHolder parlayOdd = null;
        for (OddHolder oddHolder:oddHolders) {
            if (oddHolder.getBetTypeSPT().equals(PARLAY)) parlayOdd = oddHolder;
        }

        if (parlayOdd != null)
        oddHolders.remove(parlayOdd);
        parlayOdd = null;

        int size = oddHolders.size();//hasParlay ? oddHolders.size() - 1 : oddHolders.size();
        final float progressSize = (float) (100 / size);

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Placing Bet..");
        pDialog.setCancelable(false);

        for (final OddHolder oddHolder : oddHolders) {

            if (!oddHolder.getBetTypeSPT().equals(PARLAY))
                if (oddHolder.getRiskValue().equals("0") || oddHolder.getIsChecked())

                    if (!pDialog.isShowing())
                        pDialog.show();

            final String userId = new WagerocityPref(getActivity()).user().getUserId();

            restClient.getApiService().betOnGames(
                    userId,
                    oddHolder.getOddId(),
                    oddHolder.getOddValue(),
                    oddHolder.getBetTypeString().equals(OVER) || oddHolder.getBetTypeString().equals(UNDER) ? oddHolder.getBetTypeString().toLowerCase() : "-",
                    oddHolder.getTeamVsteam(),
                    "ao",
                    oddHolder.getRiskValue(),
                    oddHolder.getTeamId(),
                    oddHolder.getTeamName(),
                    oddHolder.getLeagueName(),
                    SINGLE,
                    oddHolder.getBetOT(),
                    new Callback<ArrayList<Pick>>() {
                        @Override
                        public void success(ArrayList<Pick> picks, Response response) {
                            pDialog.getProgressHelper().setProgress(pDialog.getProgressHelper().getProgress() + progressSize);
                            if (pDialog.getProgressHelper().getProgress() >= 97.0) {
                                pDialog.dismiss();

                                restClient.getApiService().getMyPicks(userId, new Callback<ArrayList<Pick>>() {
                                    @Override
                                    public void success(ArrayList<Pick> picks, Response response) {

                                        oddHolders.removeAll(oddHolders);

                                        Uri uri = Uri.parse(getString(R.string.uri_open_my_picks_fragment));
                                        mListener.onBetOnGameFragmentInteraction(uri, picks);

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.e("Picks Posting Error", error.toString());
                                    }
                                });

                                Log.e("Picks Posting", "Completed");

                            }
                            Log.e("Picks Posting", String.valueOf(pDialog.getProgressHelper().getProgress()));
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            pDialog.dismiss();
                        }
                    }
            );
        }

    }

}

