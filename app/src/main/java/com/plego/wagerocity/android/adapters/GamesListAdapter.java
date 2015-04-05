package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;

import java.util.List;

/**
 * Created by haris on 29/03/15.
 */
public class GamesListAdapter extends BaseAdapter {

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

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_games_list, parent, false);

            viewHolder.textViewTeamA = (TextView) convertView.findViewById(R.id.textview_cell_games_team_a);
            viewHolder.textViewTeamB = (TextView) convertView.findViewById(R.id.textview_cell_games_team_b);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textview_cell_games_date_time);
            viewHolder.imageViewA = (ImageView) convertView.findViewById(R.id.imageview_cell_games_list_team_a_flag);
            viewHolder.imageViewB = (ImageView) convertView.findViewById(R.id.imageview_cell_games_list_team_b_flag);
            viewHolder.button = (Button) convertView.findViewById(R.id.button_cell_games_list_bet);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (games.get(position).getTeamAOdds().size() > 0) {

                        Uri uri = Uri.parse(context.getString(R.string.uri_selected_game_for_betting));
                        Game game = games.get(position);
                        game.setSportsName(sportsName);
                        mListener.onGamesListAdapterFragmentInteraction(uri, game);

                    } else {

                        new MaterialDialog.Builder(context)
                                .title(context.getString(R.string.no_betting_information))
                                .content(context.getString(R.string.no_betting_information_message))
                                .positiveText(context.getString(R.string.ok))
                                .show();

                    }
                }
            });

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Game game = games.get(position);

        if (game != null) {
            viewHolder.textViewTeamA.setText( game.getTeamAFullname() );
            viewHolder.textViewTeamB.setText(game.getTeamBFullname());
            viewHolder.textViewDate.setText(game.getCstStartTime());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(R.drawable.sports)
                    .showImageForEmptyUri(R.drawable.sports)
                    .build();

            ImageLoader.getInstance().displayImage(game.getTeamALogo(), viewHolder.imageViewA, options);
            ImageLoader.getInstance().displayImage(game.getTeamBLogo(), viewHolder.imageViewB, options);

        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewTeamA;
        TextView textViewTeamB;
        TextView textViewDate;
        ImageView imageViewA;
        ImageView imageViewB;
        Button button;

    }

    public interface OnGamesListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGamesListAdapterFragmentInteraction(Uri uri, Game game);
    }

}
