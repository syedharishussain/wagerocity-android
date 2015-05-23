package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.utils.AndroidUtils;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by haris on 06/04/15.
 */
public class MyPicksListAdapter extends BaseAdapter {

    ArrayList<Pick> picks;
    Context context;
    private OnMyPickShareInteractionListener mListener;
    public MyPicksListAdapter(Context context, ArrayList<Pick> picks) {
        this.picks = picks;
        this.context = context;

        try {
            mListener = (OnMyPickShareInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMyPickShareInteractionListener");
        }
    }

    @Override
    public int getCount() {
        return this.picks.size();
    }

    @Override
    public Pick getItem(int position) {
        return this.picks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        final Pick pick = picks.get(position);

        final String betTypeString = pick.getTeamName().equals("Parlay") || pick.getTeamName().equals("Teaser") ? pick.getTeamName() : AndroidUtils.getBetTypeFromBetOT(Integer.parseInt(pick.getBetOt()), pick.getPos());

//        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_my_picks_list, parent, false);

            viewHolder.textViewTeamA = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_a_name);
//            viewHolder.textViewTeamB = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_b_name);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_date_time);

            viewHolder.textViewTeamName = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_name);
            viewHolder.poolLabel = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_pool_label);
            viewHolder.poolName = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_pool_name);

            viewHolder.textViewBetType = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_bet_type);
            viewHolder.textViewBetTypeValue = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_bet_type_value);
            viewHolder.textViewStake = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_stake_value);
            viewHolder.textViewToWin = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_to_win_value);
            viewHolder.textViewResult = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_result);

            viewHolder.imageViewA = (ImageView) convertView.findViewById(R.id.imageview_cell_my_picks_team_a_flag);
            viewHolder.imageViewB = (ImageView) convertView.findViewById(R.id.imageview_cell_my_picks_team_b_flag);

            viewHolder.share = (Button) convertView.findViewById(R.id.button_cell_my_picks_share);

            viewHolder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    String shareBody = "Share your Pick!";
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, picks.get(position).getMatchDet());
//                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

                    String name = pick.getMatchDet();
                    String caption = "I have put my stakes " + "$" + pick.getStake() + " on " + pick.getTeamName() + " " + betTypeString + " " + pick.getOddsVal();


                    Feed feed = new Feed.Builder()
//                            .setMessage("message: " + caption)
                            .setName(name)
//                            .setCaption(caption)
                            .setDescription(caption)
                            .setPicture("https://www.wagerocity.com/user_data/images/logo1.png")
                            .setLink("https://www.wagerocity.com")
                            .build();
                    mListener.onMyPickShareInteraction(feed);

                }
            });

//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }



        if (pick != null) {

            if (pick.getIsPoolBet() != null && pick.getPoolName() != null)
                if (!pick.getIsPoolBet().equals("0")) {
                    viewHolder.poolLabel.setVisibility(View.VISIBLE);
                    viewHolder.poolName.setVisibility(View.VISIBLE);

                    viewHolder.poolName.setText(pick.getPoolName());
                }

            viewHolder.textViewTeamA.setText(pick.getMatchDet());
//            viewHolder.textViewTeamB.setText( pick.getTeamBName() );
            try {
                viewHolder.textViewDate.setText(pick.getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DecimalFormat f = new DecimalFormat("##.00");



            viewHolder.textViewTeamName.setText(pick.getTeamName());
            viewHolder.textViewBetType.setText(betTypeString);
            viewHolder.textViewBetTypeValue.setText(AndroidUtils.getSignedOddValue(pick.getOddsVal()));
            viewHolder.textViewStake.setText("$" + pick.getStake());
            viewHolder.textViewToWin.setText("$" + String.valueOf(f.format(AndroidUtils.getToWinAmount(Double.parseDouble(pick.getStake()), Double.parseDouble(pick.getOddsVal())))));
            viewHolder.textViewResult.setText(pick.getBetResult());

            if (pick.getTeamName().equals("Parlay") || pick.getTeamName().equals("Teaser")) {
                viewHolder.textViewBetType.setVisibility(View.GONE);
                viewHolder.textViewBetTypeValue.setVisibility(View.GONE);
            }

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(AndroidUtils.getDrawableFromLeagueName(pick.getLeagueName()))
                    .showImageForEmptyUri(AndroidUtils.getDrawableFromLeagueName(pick.getLeagueName()))
                    .build();

            ImageLoader.getInstance().displayImage(pick.getTeamALogo(), viewHolder.imageViewA, options);
            ImageLoader.getInstance().displayImage(pick.getTeamBLogo(), viewHolder.imageViewB, options);

        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewTeamA;
        TextView textViewTeamB;
        TextView textViewDate;

        TextView poolLabel;
        TextView poolName;

        TextView textViewTeamName;
        TextView textViewBetType;
        TextView textViewBetTypeValue;
        TextView textViewStake;
        TextView textViewToWin;
        TextView textViewResult;

        ImageView imageViewA;
        ImageView imageViewB;

        Button share;
    }

    public interface OnMyPickShareInteractionListener {
        public void onMyPickShareInteraction(Feed feed);
    }
}
