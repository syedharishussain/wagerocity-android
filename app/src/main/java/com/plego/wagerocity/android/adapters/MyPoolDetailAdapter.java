package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.PoolMember;

import java.util.ArrayList;

/**
 * Created by haris on 29/04/15.
 */
public class MyPoolDetailAdapter extends BaseAdapter {

    private ArrayList<PoolMember> poolMembers;
    private Context context;

    public MyPoolDetailAdapter(ArrayList<PoolMember> poolMembers, Context context) {
        this.poolMembers = poolMembers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.poolMembers.size();
    }

    @Override
    public PoolMember getItem(int position) {
        return this.poolMembers.get(position);
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

            convertView = inflater.inflate(R.layout.layout_cell_my_pool_players, parent, false);

            viewHolder.rank = (TextView) convertView.findViewById(R.id.textView_pool_members_player_rank);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textView_pool_members_player_name);
            viewHolder.credits = (TextView) convertView.findViewById(R.id.textView_pool_members_player_dollars);
            viewHolder.wlt = (TextView) convertView.findViewById(R.id.textView_pool_members_player_wlt);
//            viewHolder.status = (TextView) convertView.findViewById(R.id.textView_pool_members_player_status);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PoolMember poolMember = this.poolMembers.get(position);

        if (poolMember != null) {
            viewHolder.rank.setText(poolMember.getRank().toString());
            viewHolder.name.setText(poolMember.getUserName());
            viewHolder.credits.setText("$" + poolMember.getDollars().toString());
            viewHolder.wlt.setText(poolMember.getWin().toString() + "-" + poolMember.getLost().toString() + "-" + poolMember.getTie().toString());
//            viewHolder.status.setText(poolMember.getStatus());

        }

        return convertView;
    }

    class ViewHolder {
        TextView rank;
        TextView name;
        TextView credits;
        TextView wlt;
//        TextView status;

    }

}
