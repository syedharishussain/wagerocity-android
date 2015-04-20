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
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
        overUnder.setText("Over\n| " + game.getTeamAOdd().getTotalMid() + " |\nUnder");

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

        pointSpreadTeamA.setText(game.getTeamAOdd().getPointSpreadString());
        pointSpreadTeamB.setText(game.getTeamBOdd().getPointSpreadString());

        moneyLineTeamA.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getMoney()));
        moneyLineTeamB.setText(AndroidUtils.getSignedOddValue(game.getTeamBOdd().getMoney()));

        overTeamA.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getOver()));
        overTeamB.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getUnder()));

        if (Double.parseDouble(game.getTeamAOdd().getMoney()) == 0.0) moneyLineTeamA.setEnabled(false);
        if (Double.parseDouble(game.getTeamBOdd().getMoney()) == 0.0) moneyLineTeamB.setEnabled(false);

        if (Double.parseDouble(game.getTeamAOdd().getPoint()) == 0.0) pointSpreadTeamA.setEnabled(false);
        if (Double.parseDouble(game.getTeamBOdd().getPoint()) == 0.0) pointSpreadTeamB.setEnabled(false);

        if (Double.parseDouble(game.getTeamAOdd().getOver()) == 0.0) overTeamA.setEnabled(false);
        if (Double.parseDouble(game.getTeamAOdd().getUnder()) == 0.0) overTeamB.setEnabled(false);

        final String teamVsTeam = game.getTeamAFullname() + " vs " + game.getTeamBFullname();

        pointSpreadTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBettingDialog(
                        teamVsTeam,
                        "Point Spread",
                        game.getTeamAOdd().getPointSpreadString(),
                        Double.parseDouble(game.getTeamAOdd().getPoint()),
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
                        game.getTeamBOdd().getPointSpreadString(),
                        Double.parseDouble(game.getTeamBOdd().getPoint()),
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
                        AndroidUtils.getSignedOddValue(game.getTeamAOdd().getMoney()),
                        Double.parseDouble(game.getTeamAOdd().getMoney()),
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
                        AndroidUtils.getSignedOddValue(game.getTeamBOdd().getMoney()),
                        Double.parseDouble(game.getTeamBOdd().getMoney()),
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
                        AndroidUtils.getSignedOddValue(game.getTeamAOdd().getOver()),
                        Double.parseDouble(game.getTeamAOdd().getOver()),
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
                        AndroidUtils.getSignedOddValue(game.getTeamAOdd().getUnder()),
                        Double.parseDouble(game.getTeamAOdd().getUnder()),
                        BetType.BetTypeUnder,
                        true);
            }
        });

    }

    private void showBettingDialog(
            final String teamNames,
            String betTypeString,
            final String betOddSting,
            final double betOddValue,
            final BetType betType,
            final Boolean isTeamA) {

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

                if (string.length() > 0) {

                    betAmountEditText.removeTextChangedListener(betAmountTextWatcher);

                    Double value = Double.parseDouble(s.toString());

                    result = AndroidUtils.getToWinAmount(value, betOddValue);

                    winAmountEditText.setText(String.valueOf(result));

                    betAmountEditText.addTextChangedListener(betAmountTextWatcher);

                }
            }
        };


        betAmountEditText.addTextChangedListener(betAmountTextWatcher);


        builder.setPositiveButton("Bet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Odd odd = (isTeamA) ? game.getTeamAOdd() : game.getTeamBOdd();

                final String userID = new WagerocityPref(getActivity()).user().getUserId();
                final String oddID = odd.getId();
                String oddVal = "";
                String position = "";
                final String matchDetail = teamNames;
                final String oddType = "ao";
                final String stake = betAmountEditText.getText().toString();
                final String matchId = (isTeamA) ? game.getTeamANumber() : game.getTeamBNumber();
                final String teamName = game.getTeamNameFromTeamNumber((isTeamA) ? game.getTeamANumber() : game.getTeamBNumber());
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
                        oddVal = game.getTeamAOdd().getOver();
                        position = "over";
                        bet_ot = "4";
                        break;
                    }

                    case BetTypeUnder: {
                        oddVal = game.getTeamAOdd().getUnder();
                        position = "under";
                        break;
                    }

                }

                final String finalOddVal = oddVal;
                final String finalPosition = position;

                final String finalBet_ot = bet_ot;

                Log.e("Dialog Output", betAmountEditText.getText().toString());

                final SweetAlertDialog pDialog = AndroidUtils.showDialog(
                        getString(R.string.loading),
                        null,
                        SweetAlertDialog.PROGRESS_TYPE,
                        getActivity()
                );

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
                        new Callback<ArrayList<Pick>>() {
                            @Override
                            public void success(ArrayList<Pick> picks, Response response) {
                                pDialog.dismiss();
                                Uri uri = Uri.parse(getString(R.string.uri_open_my_picks_fragment));
                                mListener.onBetOnGameFragmentInteraction(uri, picks);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                pDialog.dismiss();
                                AndroidUtils.showErrorDialog(error, getActivity());
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

        public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks);
    }

}
