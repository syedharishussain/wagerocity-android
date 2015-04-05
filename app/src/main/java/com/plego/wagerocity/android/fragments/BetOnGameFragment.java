package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Odd;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import retrofit.Callback;
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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAME = "selected_game";
    private Game game;

    private OnBetOnGameFragmentInteractionListener mListener;
    private TextWatcher betAmountTextWatcher;

    public enum BetType {
        BetTypePointSpread,
        BetTypeMoneyLine,
        BetTypeOver,
        BetTypeUnder
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param game Parameter 1.
     * @return A new instance of fragment BetOnGameFragment.
     */

    public static BetOnGameFragment newInstance(Game game) {
        BetOnGameFragment fragment = new BetOnGameFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_GAME, game);
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
            game = getArguments().getParcelable(ARGS_GAME);
            game.setBettingValues();
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

        final View view1 = view;

        TextView teamNameA = (TextView) view.findViewById(R.id.textview_betongame_team_a_name);
        teamNameA.setText(game.getTeamAFullname());

        TextView teamNameB = (TextView) view.findViewById(R.id.textview_betongame_team_b_name);
        teamNameB.setText(game.getTeamBFullname());

        final ImageView teamFlagA = (ImageView) view.findViewById(R.id.imageview_betongame_team_a_flag);
        ImageView teamFlagB = (ImageView) view.findViewById(R.id.imageview_betongame_team_b_flag);

        TextView overUnder = (TextView) view.findViewById(R.id.textview_betongame_over_under);
        overUnder.setText("Over\n| " + Double.toString(game.getPointA()) + " |\nUnder");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnFail(R.drawable.sports)
                .showImageForEmptyUri(R.drawable.sports)
                .build();

        ImageLoader.getInstance().displayImage(game.getTeamALogo(), teamFlagA, options);
        ImageLoader.getInstance().displayImage(game.getTeamBLogo(), teamFlagB, options);

        Button pointSpreadTeamA = (Button) view.findViewById(R.id.button_betongame_pointspread_team_a);
        Button pointSpreadTeamB = (Button) view.findViewById(R.id.button_betongame_pointspread_team_b);

        Button moneyLineTeamA = (Button) view.findViewById(R.id.button_betongame_money_line_team_a);
        Button moneyLineTeamB = (Button) view.findViewById(R.id.button_betongame_money_line_team_b);

        Button overTeamA = (Button) view.findViewById(R.id.button_betongame_over_team_a);
        Button overTeamB = (Button) view.findViewById(R.id.button_betongame_over_team_b);

        pointSpreadTeamA.setText(game.getPointSpreadStringA());
        pointSpreadTeamB.setText(game.getPointSpreadStringB());

        moneyLineTeamA.setText(Double.toString(game.getMoneyLineA()));
        moneyLineTeamB.setText(Double.toString(game.getMoneyLineB()));

        overTeamA.setText(Double.toString(game.getOverA()));
        overTeamB.setText(Double.toString(game.getUnderA()));

        if (game.getMoneyLineA() == 0.0) moneyLineTeamA.setEnabled(false);
        if (game.getMoneyLineB() == 0.0) moneyLineTeamB.setEnabled(false);

        if (game.getPointSpreadA() == 0.0) pointSpreadTeamA.setEnabled(false);
        if (game.getPointSpreadB() == 0.0) pointSpreadTeamB.setEnabled(false);

        if (game.getOverA() == 0.0) overTeamA.setEnabled(false);
        if (game.getUnderA() == 0.0) overTeamB.setEnabled(false);

        final String teamVsTeam = game.getTeamAFullname() + game.getTeamBFullname();

        pointSpreadTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBettingDialog(
                        teamVsTeam,
                        "Point Spread",
                        game.getPointSpreadStringA(),
                        game.getPointSpreadA(),
                        game.getPointA(),
                        BetType.BetTypePointSpread,
                        true);

            }
        });

        pointSpreadTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBettingDialog(
                        teamVsTeam,
                        "Point Spread",
                        game.getPointSpreadStringB(),
                        game.getPointSpreadB(),
                        game.getPointB(),
                        BetType.BetTypePointSpread,
                        false);
            }
        });

        moneyLineTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBettingDialog(
                        teamVsTeam,
                        "Moneyline",
                        Double.toString(game.getMoneyLineA()),
                        game.getMoneyLineA(),
                        game.getPointA(),
                        BetType.BetTypeMoneyLine,
                        true);
            }
        });

        moneyLineTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBettingDialog(
                        teamVsTeam,
                        "Moneyline",
                        Double.toString(game.getMoneyLineB()),
                        game.getMoneyLineB(),
                        game.getPointB(),
                        BetType.BetTypeMoneyLine,
                        false);
            }
        });


        overTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBettingDialog(
                        teamVsTeam,
                        "Over",
                        Double.toString(game.getOverA()),
                        game.getOverA(),
                        game.getPointA(),
                        BetType.BetTypeOver,
                        true);
            }
        });

        overTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBettingDialog(
                        teamVsTeam,
                        "Under",
                        Double.toString(game.getUnderA()),
                        game.getUnderA(),
                        game.getPointA(),
                        BetType.BetTypeUnder,
                        true);
            }
        });

    }

    private void showBettingDialog(
            String teamNames,
            String betTypeString,
            final String betOddSting,
            final double betOddValue,
            double points,
            BetType betType,
            Boolean isTeamA) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.layout_dialog_bet_on_game, null);

        builder.setView(alertLayout);

        final TextView teamNamesTextView = (TextView) alertLayout.findViewById(R.id.textview_dialog_betongame_team_names);
        teamNamesTextView.setText(teamNames);

        final TextView betTypetextView = (TextView) alertLayout.findViewById(R.id.textview_dialog_betongame_bet_type);
        betTypetextView.setText(betTypeString);

        final TextView betTypeValuetextView = (TextView) alertLayout.findViewById(R.id.textview_dialog_betongame_bet_type_value);
        betTypeValuetextView.setText(betOddSting);

        final EditText betAmountEditText = (EditText) alertLayout.findViewById(R.id.edittext_dialog_betongame_credit_amount);
        betAmountEditText.requestFocus();

        final EditText winAmountEditText = (EditText) alertLayout.findViewById(R.id.edittext_dialog_betongame_to_win_amount);


        betAmountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                double result = 0.0;
                String string = s.toString();

                if (string.toString().length() > 0) {

                    betAmountEditText.removeTextChangedListener(betAmountTextWatcher);

                    Double value = Double.parseDouble(s.toString());

                    if (betOddValue > 0) {

                        double amountNeedToWinADollar = Math.abs(betOddValue) / (double) 100;

                        double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

                        double percentageBasedValue = value * percentage;

                        result = Math.ceil(value + percentageBasedValue);

                    } else {
                        double amountNeedToWinADollar = Math.abs(betOddValue) / (double) 100;

                        double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

                        double percentageBasedValue = value * percentage;

                        result = Math.ceil(value - percentageBasedValue);
                    }

                    winAmountEditText.setText(String.valueOf(result));

                    betAmountEditText.addTextChangedListener(betAmountTextWatcher);

                }
            }
        };


        betAmountEditText.addTextChangedListener(betAmountTextWatcher);

        Odd odd = getOddForBet(isTeamA, betType);

        final String userID = new WagerocityPref(getActivity()).user().getUserId();
        final String oddID = odd.getId();
        String oddVal = "";
        String position = "";
        final String matchDetail = teamNames;
        final String oddType = "ao";
        final String stake = betAmountEditText.getText().toString();
        final String matchId = odd.getTeamNumber();
        final String teamName = game.getTeamNameFromTeamNumber(odd.getTeamNumber());
        final String sportsName = game.getSportsName();
        final String betTypeSPT = "single";
        String bet_ot = "";


        switch (betType) {
            case BetTypePointSpread: {
                oddVal = odd.getMoney().toString();
                position = "-";
                bet_ot = "3";
                break;
            }

            case BetTypeMoneyLine: {
                oddVal = odd.getMoney().toString();
                position = "-";
                bet_ot = "1";
                break;
            }

            case BetTypeOver: {
                oddVal = odd.getOverMoney().toString();
                position = "over";
                bet_ot = "4";
                break;
            }

            case BetTypeUnder: {
                oddVal = odd.getUnderMoney().toString();
                position = "under";
                break;
            }

        }

        final String finalOddVal = oddVal;
        final String finalPosition = position;

        final String finalBet_ot = bet_ot;
        builder.setPositiveButton("Bet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.e("Dialog Output", betAmountEditText.getText().toString());

                RestClient restClient = new RestClient();

                restClient.getApiService().betOnGames(
                        userID,
                        oddID,
                        finalOddVal,
                        finalPosition,
                        matchDetail,
                        oddType,
                        stake,
                        matchId,
                        teamName,
                        sportsName,
                        betTypeSPT,
                        finalBet_ot,
                        new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {

                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        }
                );


                restClient.getApiService().consumeCredits(new WagerocityPref(getActivity()).user().getUserId().toString(),
                        Float.parseFloat(betAmountEditText.getText().toString()),
                        new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                Log.e("Credits Consumed", user.getCredits().toString());
                                new WagerocityPref(getActivity()).setUser(user);

                                AndroidUtils.updateStats(getActivity());
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });


            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBetOnGameFragmentInteractionListener {

        public void onBetOnGameFragmentInteraction(Uri uri);
    }

    private Odd getOddForBet(Boolean isTeamA, BetType betType) {
        Odd odd = new Odd();
        if (isTeamA) {
            for (Odd oddA : game.getTeamAOdds()) {
                if (oddA.getML().equals("") &&
                        (betType == BetType.BetTypeOver || betType == BetType.BetTypeUnder)) {

                    odd = oddA;

                } else if (oddA.getML().equals("false") && betType == BetType.BetTypePointSpread) {

                    odd = oddA;

                } else if (oddA.getML().equals("true") && betType == BetType.BetTypeMoneyLine) {

                    odd = oddA;

                }
            }
        } else {
            for (Odd oddB : game.getTeamAOdds()) {
                if (oddB.getML().equals("") &&
                        (betType == BetType.BetTypeOver || betType == BetType.BetTypeUnder)) {

                    odd = oddB;

                } else if (oddB.getML().equals("false") && betType == BetType.BetTypePointSpread) {

                    odd = oddB;

                } else if (oddB.getML().equals("true") && betType == BetType.BetTypeMoneyLine) {

                    odd = oddB;

                }
            }
        }

        return odd;
    }


}
