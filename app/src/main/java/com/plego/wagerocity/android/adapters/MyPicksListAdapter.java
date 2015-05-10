package com.plego.wagerocity.android.adapters;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by haris on 06/04/15.
 */
public class MyPicksListAdapter extends BaseAdapter {

    ArrayList<Pick> picks;
    Context context;



    public MyPicksListAdapter(Context context, ArrayList<Pick> picks) {
        this.picks = picks;
        this.context = context;
    };



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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_my_picks_list, parent, false);

            viewHolder.textViewTeamA = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_a_name);
//            viewHolder.textViewTeamB = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_b_name);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_date_time);

            viewHolder.textViewTeamName = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_team_name);
            viewHolder.textViewBetType = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_bet_type);
            viewHolder.textViewBetTypeValue = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_bet_type_value);
            viewHolder.textViewStake = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_stake_value);
            viewHolder.textViewToWin = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_to_win_value);
            viewHolder.textViewResult = (TextView) convertView.findViewById(R.id.textview_cell_my_picks_result);

            viewHolder.imageViewA = (ImageView) convertView.findViewById(R.id.imageview_cell_my_picks_team_a_flag);
            viewHolder.imageViewB = (ImageView) convertView.findViewById(R.id.imageview_cell_my_picks_team_b_flag);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Pick pick = picks.get(position);

        if (pick != null) {


            viewHolder.textViewTeamA.setText( pick.getMatchDet() );
//            viewHolder.textViewTeamB.setText( pick.getTeamBName() );
            try {
                viewHolder.textViewDate.setText( pick.getStartTime() );
            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewHolder.textViewTeamName.setText( pick.getTeamName() );
            viewHolder.textViewBetType.setText(AndroidUtils.getBetTypeFromBetOT(Integer.parseInt(pick.getBetOt()), pick.getPos()));
            viewHolder.textViewBetTypeValue.setText(pick.getOddsVal());
            viewHolder.textViewStake.setText(pick.getStake());
            viewHolder.textViewToWin.setText(String.valueOf(AndroidUtils.getToWinAmount(Double.parseDouble(pick.getStake()), Double.parseDouble(pick.getOddsVal()))));
            viewHolder.textViewResult.setText(pick.getBetResult());

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

        TextView textViewTeamName;
        TextView textViewBetType;
        TextView textViewBetTypeValue;
        TextView textViewStake;
        TextView textViewToWin;
        TextView textViewResult;

        ImageView imageViewA;
        ImageView imageViewB;
    }
}
