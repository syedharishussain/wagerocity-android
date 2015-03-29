package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.SportsListObject;

import java.util.List;

public class SportsListAdapter extends BaseAdapter {

    List <SportsListObject>sportsListObjects;
    Context context;

    public SportsListAdapter (Context context, List<SportsListObject> sportsListObjects) {
        this.sportsListObjects = sportsListObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.sportsListObjects.size();
    }

    @Override
    public SportsListObject getItem(int position) {
        return this.sportsListObjects.get(position);
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

            convertView = inflater.inflate(R.layout.layout_cell_sports_list, parent, false);

            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview_cell_sports_list_name);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview_cell_sports_list_image);

            convertView.setTag(viewHolder);

        } else {
            viewHolder =   (ViewHolder) convertView.getTag();
        }

        SportsListObject sportsListObject = sportsListObjects.get(position);

        if (sportsListObject != null) {
            viewHolder.textView.setText(sportsListObject.getSportsName());
            viewHolder.imageView.setImageResource(sportsListObject.getSportsImageId());
        }

        return convertView;
    }

    class ViewHolder
    {
        TextView textView;
        ImageView imageView;

    }
}

