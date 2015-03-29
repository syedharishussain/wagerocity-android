package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;

import java.util.List;

/**
 * Created by haris on 29/03/15.
 */
public class GamesListAdapter extends BaseAdapter {

    List<Game> games;
    Context context;

    public GamesListAdapter(Context context, List<Game> games) {
        this.games = games;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_games_list, parent, false);

            viewHolder.textViewTeamA = (TextView) convertView.findViewById(R.id.textview_cell_games_team_a);
            viewHolder.textViewTeamB = (TextView) convertView.findViewById(R.id.textview_cell_games_team_b);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Game game = games.get(position);

        if (game != null) {
            viewHolder.textViewTeamA.setText(game.getTeamAName());
            viewHolder.textViewTeamB.setText(game.getTeamBName());
        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewTeamA;
        TextView textViewTeamB;
        Button button;

    }

}
