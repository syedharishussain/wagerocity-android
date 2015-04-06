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
import com.plego.wagerocity.android.model.ExpertPlayer;

import java.util.ArrayList;

/**
 * Created by haris on 06/04/15.
 */
public class ExpertPlayerListAdapter extends BaseAdapter {

    private ArrayList<ExpertPlayer> expertPlayers;
    private Context context;

    public ExpertPlayerListAdapter(ArrayList<ExpertPlayer> expertPlayers, Context context) {
        this.expertPlayers = expertPlayers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.expertPlayers.size();
    }

    @Override
    public ExpertPlayer getItem(int position) {
        return this.expertPlayers.get(position);
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

            convertView = inflater.inflate(R.layout.layout_cell_experts, parent, false);

            viewHolder.textViewPlayerName = (TextView) convertView.findViewById(R.id.textview_experts_cell_user_name);
            viewHolder.textViewDisc = (TextView) convertView.findViewById(R.id.textview_experts_cell_description);
            viewHolder.imageViewUserImage = (ImageView) convertView.findViewById(R.id.imageview_expert_user);
            viewHolder.button = (Button) convertView.findViewById(R.id.button_experts_cell_Picks);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ExpertPlayer expertPlayer = this.expertPlayers.get(position);

        if (expertPlayer != null) {
            viewHolder.textViewPlayerName.setText(expertPlayer.getDisplayname());
            viewHolder.textViewDisc.setText(expertPlayer.getDescription());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(R.drawable.user1)
                    .showImageForEmptyUri(R.drawable.user1)
                    .build();

            ImageLoader.getInstance().displayImage(expertPlayer.getImageUrl(), viewHolder.imageViewUserImage, options);

        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewPlayerName;
        TextView textViewDisc;
        ImageView imageViewUserImage;
        Button button;

    }
}
