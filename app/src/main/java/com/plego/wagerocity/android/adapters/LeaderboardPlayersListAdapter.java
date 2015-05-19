package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 06/04/15.
 */
public class LeaderboardPlayersListAdapter extends BaseAdapter {

    private OnLeaderboardPlayerListAdapterFragmentInteractionListener mListner;
    private ArrayList<LeaderboardPlayer> leaderboardPlayers;
    private Context context;

    public LeaderboardPlayersListAdapter(ArrayList<LeaderboardPlayer> leaderboardPlayers, Context context) {
        this.leaderboardPlayers = leaderboardPlayers;
        this.context = context;

        try {
            mListner = (OnLeaderboardPlayerListAdapterFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGamesListAdapterFragmentInteractionListener");
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_leaderboard_users, parent, false);

            viewHolder.textViewPlayerName = (TextView) convertView.findViewById(R.id.textview_leaderboard_user_name);
            viewHolder.textViewPlayeRank = (TextView) convertView.findViewById(R.id.textView_leaderboard_cell_rank);
            viewHolder.textViewUserStats = (TextView) convertView.findViewById(R.id.textview_leaderboard_user_stats);
            viewHolder.imageViewUserImage = (ImageView) convertView.findViewById(R.id.imageview_leaderboard_user);
            viewHolder.button = (Button) convertView.findViewById(R.id.button_leaderboard_user_buy_picks);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LeaderboardPlayer player = leaderboardPlayers.get(position);

                    final SweetAlertDialog pDialog = AndroidUtils.showDialog(
                            "Loading",
                            null,
                            SweetAlertDialog.PROGRESS_TYPE,
                            context
                    );

                    RestClient restClient = new RestClient();

                    restClient.getApiService().getGamesOfPlayer(
                            player.getUsrId(),
                            new WagerocityPref(context).user().getUserId(),
                            new Callback<ArrayList<Game>>() {
                                @Override
                                public void success(ArrayList<Game> games, Response response) {
                                    pDialog.dismiss();
                                    Uri uri = Uri.parse(context.getString(R.string.uri_open_picks_of_player_fragment));
                                    mListner.onLeaderboardPlayerListAdapterFragmentInteraction(uri, games);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    pDialog.dismiss();
                                    AndroidUtils.showErrorDialog(error, context);
                                }
                            });

                }
            });

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LeaderboardPlayer leaderboardPlayer = this.leaderboardPlayers.get(position);

        if (leaderboardPlayer != null) {
            Integer rank = position + 1;
            viewHolder.textViewPlayerName.setText(leaderboardPlayer.getUsername());
            viewHolder.textViewUserStats.setText(leaderboardPlayer.getWinPercentage() + " | " + "$" + leaderboardPlayer.getPoints());
            viewHolder.textViewPlayeRank.setText(rank.toString());

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
        TextView textViewPlayeRank;
        TextView textViewUserStats;
        ImageView imageViewUserImage;
        Button button;

    }

    public interface OnLeaderboardPlayerListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLeaderboardPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games);
    }

}
