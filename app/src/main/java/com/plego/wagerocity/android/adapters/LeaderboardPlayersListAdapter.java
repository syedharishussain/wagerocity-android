package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
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
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.LeaderboardPlayer;

import java.util.ArrayList;

/**
 * Created by haris on 06/04/15.
 */
public class LeaderboardPlayersListAdapter extends BaseAdapter {

    private ArrayList<LeaderboardPlayer> leaderboardPlayers;
    private Context context;

    public LeaderboardPlayersListAdapter (ArrayList<LeaderboardPlayer> leaderboardPlayers, Context context) {
        this.leaderboardPlayers = leaderboardPlayers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.leaderboardPlayers.size();
    }

    @Override
    public LeaderboardPlayer getItem(int position) {
        return this.leaderboardPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_leaderboard_users, parent, false);

            viewHolder.textViewPlayerName = (TextView) convertView.findViewById(R.id.textview_leaderboard_user_name);
            viewHolder.textViewUserStats = (TextView) convertView.findViewById(R.id.textview_leaderboard_user_stats);
            viewHolder.imageViewUserImage = (ImageView) convertView.findViewById(R.id.imageview_leaderboard_user);
            viewHolder.button = (Button) convertView.findViewById(R.id.button_leaderboard_user_buy_picks);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LeaderboardPlayer leaderboardPlayer = this.leaderboardPlayers.get(position);

        if (leaderboardPlayer != null) {
            viewHolder.textViewPlayerName.setText(leaderboardPlayer.getDisplayname());
            viewHolder.textViewUserStats.setText(leaderboardPlayer.getWinPercentage() + " | " + "$" + leaderboardPlayer.getPoints());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(R.drawable.user1)
                    .showImageForEmptyUri(R.drawable.user1)
                    .build();

            ImageLoader.getInstance().displayImage(leaderboardPlayer.getImageUrl(), viewHolder.imageViewUserImage, options);

        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewPlayerName;
        TextView textViewUserStats;
        ImageView imageViewUserImage;
        Button button;

    }

}
