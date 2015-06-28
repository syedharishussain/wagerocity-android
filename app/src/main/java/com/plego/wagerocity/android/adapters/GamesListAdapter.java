package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haris on 29/03/15.
 */
public class GamesListAdapter extends BaseAdapter {

    public static final String POINT_SPREAD = "PointSpread";
    public static final String SINGLE = "single";
    public static final String MONEY_LINE = "MoneyLine";
    public static final String OVER = "Over";
    public static final String UNDER = "Under";
    List<Game> games;
    String sportsName;
    Context context;

    private OnGamesListAdapterFragmentInteractionListener mListener;

    public GamesListAdapter(Context context, List<Game> games, String sportsName) {
        this.games = games;
        this.context = context;
        this.sportsName = sportsName;

        try {
            mListener = (OnGamesListAdapterFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGamesListAdapterFragmentInteractionListener");
        }
    }

    public int getCount() {
        return this.games.size();
    }

    @Override
    public Game getItem(int position) {
        return this.games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        final Game game = games.get(position);

//        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_games_list, parent, false);

            viewHolder.textViewTeamA = (TextView) convertView.findViewById(R.id.textview_cell_games_team_a);
            viewHolder.textViewTeamB = (TextView) convertView.findViewById(R.id.textview_cell_games_team_b);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textview_cell_games_date_time);
            viewHolder.imageViewA = (ImageView) convertView.findViewById(R.id.imageview_cell_games_list_team_a_flag);
            viewHolder.imageViewB = (ImageView) convertView.findViewById(R.id.imageview_cell_games_list_team_b_flag);

            viewHolder.tvPSA = (TextView) convertView.findViewById((R.id.button_cell_games_list_pointspread_team_a));
            viewHolder.tvPSB = (TextView) convertView.findViewById((R.id.button_cell_games_list_pointspread_team_b));
            viewHolder.tvMLA = (TextView) convertView.findViewById((R.id.button_cell_games_list_money_line_team_a));
            viewHolder.tvMLB = (TextView) convertView.findViewById((R.id.button_cell_games_list_money_line_team_b));
            viewHolder.tvOA = (TextView) convertView.findViewById((R.id.button_cell_games_list_over_team_a));
            viewHolder.tvUA = (TextView) convertView.findViewById((R.id.button_cell_games_list_over_team_b));
            viewHolder.tvOU = (TextView) convertView.findViewById(R.id.textview_cell_games_list_over_under);

            viewHolder.cbPLA = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_pointspread_a);
            viewHolder.cbPLB = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_pointspread_b);
            viewHolder.cbMLA = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_moneyline_a);
            viewHolder.cbMLB = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_moneyline_b);
            viewHolder.cbOA = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_over_a);
            viewHolder.cbUA = (CheckBox) convertView.findViewById(R.id.checkbox_cell_games_list_over_b);

            if (sportsName.equals("mlb")) {
                viewHolder.teamAPitcher = (TextView) convertView.findViewById(R.id.textview_cell_games_team_a_pitcher);
                viewHolder.teamBPitcher = (TextView) convertView.findViewById(R.id.textview_cell_games_team_b_pitcher);

                viewHolder.teamAPitcher.setVisibility(View.VISIBLE);
                viewHolder.teamBPitcher.setVisibility(View.VISIBLE);

            }

            if (game.getTeamAOdd() == null) {
                viewHolder.cbPLA.setEnabled(false);
                viewHolder.cbMLA.setEnabled(false);
                viewHolder.cbOA.setEnabled(false);
                viewHolder.cbUA.setEnabled(false);
            }

            if (game.getTeamBOdd() == null) {
                viewHolder.cbPLB.setEnabled(false);
                viewHolder.cbMLB.setEnabled(false);
            }

            viewHolder.cbPLA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }

                        game.getOddHolders().add( //(Double stake, String teamId, String oddId, String teamName, String teamVsteam, String oddValue, String betTypeSPT, String betOT, String betTypeString, String pointSpreadString)
                                new OddHolder(
                                        0.0,
                                        game.getTeamANumber(),
                                        game.getTeamAOdd().getId(),
                                        game.getTeamAFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamAOdd().getPoint(),
                                        SINGLE,
                                        "3",
                                        POINT_SPREAD,
                                        game.getTeamAOdd().getPointSpreadString(),
                                        sportsName,
                                        true
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                        removeObject(game.getTeamANumber(), game.getTeamAOdd().getId(), POINT_SPREAD, game.getOddHolders());
                        }
                    }

            });

            viewHolder.cbPLB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }
                        game.getOddHolders().add(
                                new OddHolder(
                                        0.0,
                                        game.getTeamBNumber(),
                                        game.getTeamBOdd().getId(),
                                        game.getTeamBFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamBOdd().getPoint(),
                                        SINGLE,
                                        "3",
                                        POINT_SPREAD,
                                        game.getTeamBOdd().getPointSpreadString(),
                                        sportsName,
                                        false
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                            removeObject(game.getTeamBNumber(), game.getTeamBOdd().getId(), POINT_SPREAD, game.getOddHolders());
                    }
                }
            });

            viewHolder.cbMLA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }
                        game.getOddHolders().add(
                                new OddHolder(
                                        0.0,
                                        game.getTeamANumber(),
                                        game.getTeamAOdd().getId(),
                                        game.getTeamAFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamAOdd().getMoney(),
                                        SINGLE,
                                        "1",
                                        MONEY_LINE,
                                        game.getTeamAOdd().getMoney(),
                                        sportsName,
                                        true
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                            removeObject(game.getTeamANumber(), game.getTeamAOdd().getId(), MONEY_LINE, game.getOddHolders());
                    }
                }
            });

            viewHolder.cbMLB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }
                        game.getOddHolders().add(
                                new OddHolder(
                                        0.0,
                                        game.getTeamBNumber(),
                                        game.getTeamBOdd().getId(),
                                        game.getTeamBFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamBOdd().getMoney(),
                                        SINGLE,
                                        "1",
                                        MONEY_LINE,
                                        game.getTeamBOdd().getMoney(),
                                        sportsName,
                                        false
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                            removeObject(game.getTeamBNumber(), game.getTeamBOdd().getId(), MONEY_LINE, game.getOddHolders());
                    }
                }
            });

            viewHolder.cbOA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }
                        game.getOddHolders().add(
                                new OddHolder(
                                        0.0,
                                        game.getTeamANumber(),
                                        game.getTeamAOdd().getId(),
                                        game.getTeamAFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamAOdd().getOver(),
                                        SINGLE,
                                        "4",
                                        OVER,
                                        game.getTeamAOdd().getPointSpreadString(),
                                        sportsName,
                                        true
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                            removeObject(game.getTeamANumber(), game.getTeamAOdd().getId(), OVER, game.getOddHolders());
                    }
                }
            });

            viewHolder.cbUA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (game.getOddHolders() == null) {
                            game.setOddHolders(new ArrayList<OddHolder>());
                        }
                        game.getOddHolders().add(
                                new OddHolder(
                                        0.0,
                                        game.getTeamANumber(),
                                        game.getTeamAOdd().getId(),
                                        game.getTeamBFullname(),
                                        game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
                                        game.getTeamAOdd().getUnder(),
                                        SINGLE,
                                        "4",
                                        UNDER,
                                        game.getTeamAOdd().getPointSpreadString(),
                                        sportsName,
                                        false
                                ));
                    } else {
                        if (game.getOddHolders() != null)
                            if (game.getOddHolders().size() > 0)
                            removeObject(game.getTeamANumber(), game.getTeamAOdd().getId(), UNDER, game.getOddHolders());
                    }
                }
            });

//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        if (game != null) {

            viewHolder.textViewTeamA.setText(game.getTeamAFullname());
            viewHolder.textViewTeamB.setText(game.getTeamBFullname());
            if (sportsName.equals("mlb")) {
                viewHolder.teamAPitcher.setText((game.getTeamAPitcher() == null) ? "" : game.getTeamAPitcher());
                viewHolder.teamBPitcher.setText((game.getTeamBPitcher() == null) ? "" : game.getTeamBPitcher());
            }
            try {
                viewHolder.textViewDate.setText(AndroidUtils.getFormatedDate(game.getCstStartTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(R.drawable.sports)
                    .showImageForEmptyUri(R.drawable.sports)
                    .build();

            ImageLoader.getInstance().displayImage(game.getTeamALogo(), viewHolder.imageViewA, options);
            ImageLoader.getInstance().displayImage(game.getTeamBLogo(), viewHolder.imageViewB, options);

            setBettingInfo(game, viewHolder);
        }

        return convertView;
    }

    private void setBettingInfo(Game game, ViewHolder view) {
        String plA = "-", plB = "-", mlA = "-", mlB = "-", oA = "-", uA = "-", ou = "-";

        if (game.getTeamAOdd() != null) {

            if (game.getTeamAOdd().getPoint() != null) plA = game.getTeamAOdd().getPoint();
            else view.cbPLA.setEnabled(false);

            if (game.getTeamAOdd().getMoney() != null) mlA = game.getTeamAOdd().getMoney();
            else view.cbMLA.setEnabled(false);

            if (game.getTeamAOdd().getOver() != null) oA = game.getTeamAOdd().getOver();
            else view.cbOA.setEnabled(false);

            if (game.getTeamAOdd().getUnder() != null) uA = game.getTeamAOdd().getUnder();
            else view.cbUA.setEnabled(false);

        }

        if (game.getTeamBOdd() != null) {
            if (game.getTeamBOdd().getPoint() != null) plB = game.getTeamBOdd().getPoint();
            else view.cbPLB.setEnabled(false);


            if (game.getTeamBOdd().getMoney() != null) mlB = game.getTeamBOdd().getMoney();
            else view.cbMLB.setEnabled(false);
        }

        if (game.getTeamAOdd() == null) view.tvPSA.setText("-");
        else {
            view.tvPSA.setText(game.getTeamAOdd().getPointSpreadString() == null ? "-" : game.getTeamAOdd().getPointSpreadString());
        }

        if (game.getTeamBOdd() == null) view.tvPSB.setText("-");
        else {
            view.tvPSB.setText(game.getTeamBOdd().getPointSpreadString() == null ? "-" : game.getTeamBOdd().getPointSpreadString());
        }

        view.tvMLA.setText(AndroidUtils.getSignedOddValue(mlA));
        view.tvMLB.setText(AndroidUtils.getSignedOddValue(mlB));
        view.tvOA.setText(AndroidUtils.getSignedOddValue(oA));
        view.tvUA.setText(AndroidUtils.getSignedOddValue(uA));

    }

    private class ViewHolder {
        TextView textViewTeamA;
        TextView textViewTeamB;
        TextView textViewDate;

        TextView teamAPitcher;
        TextView teamBPitcher;

        ImageView imageViewA;
        ImageView imageViewB;
//        Button button;

        TextView tvPSA;
        TextView tvPSB;

        TextView tvMLA;
        TextView tvMLB;

        TextView tvOA;
        TextView tvUA;

        TextView tvOU;

        CheckBox cbPLA;
        CheckBox cbPLB;

        CheckBox cbMLA;
        CheckBox cbMLB;

        CheckBox cbOA;
        CheckBox cbUA;
    }

    void removeObject(String teamId, String oddId, String betTypeString, ArrayList<OddHolder> oddHolders) {

        ArrayList<OddHolder> arrayList = new ArrayList<>();

        for ( int i = 0; i < oddHolders.size() ; i++ ) {
            OddHolder oddHolder = oddHolders.get(i);
            if (oddHolder.getOddId().equals(oddId) && oddHolder.getTeamId().equals(teamId) && oddHolder.getBetTypeString().equals(betTypeString)) {
                oddHolders.remove(oddHolder);
            }
        }
    }

    public interface OnGamesListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGamesListAdapterFragmentInteraction(Uri uri, Game game);
    }

}
