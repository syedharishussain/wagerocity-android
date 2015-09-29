package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.controller.events.EventFactory;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by haris on 08/04/15.
 */
public class PoolsListAdapter extends BaseAdapter {

    private OnPoolsListAdapterFragmentInteractionListener mListner;
    private ArrayList<Pool>                               pools;
    private Context                                       context;
    private Boolean                                       isMyPools;
    @Inject
    EventFactory eventFactory;

    public PoolsListAdapter (ArrayList<Pool> pools, Context context, Boolean isMyPools) {
        this.pools = pools;
        this.context = context;
        WagerocityApplication.component( context ).inject( this );
        this.isMyPools = isMyPools;

        try {
            mListner = (OnPoolsListAdapterFragmentInteractionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException( context.toString()
                    + " must implement OnPoolsListAdapterFragmentInteractionListener" );
        }
    }

    @Override
    public int getCount () {
        return this.pools.size();
    }

    @Override
    public Pool getItem (int position) {
        return this.pools.get( position );
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

//        if (convertView == null) {

        viewHolder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.layout_cell_pools, parent, false);

        viewHolder.textViewPoolName = (TextView) convertView.findViewById(R.id.textview_pools_cell_name);
        viewHolder.textViewStatus = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_open_close_status);
        viewHolder.textViewStartDate = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_start_date);
        viewHolder.textViewEndDate = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_end_date);
        viewHolder.button = (Button) convertView.findViewById(R.id.button_pool_join_unjoin);

        viewHolder.button.setTag( R.id.position, position );
        viewHolder.button.setTag( R.id.is_joined, getItem( position ).isJoined() );

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag( R.id.position );
                if ((boolean) v.getTag( R.id.is_joined )) {
                    eventFactory.unJoinPool( position );
                } else {
                    eventFactory.joinPool( position );
                }

                final Pool pool = pools.get( position );



            }

        });


//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        Pool pool = this.pools.get(position);

        if (pool != null) {
            viewHolder.textViewPoolName.setText(pool.getName());
            viewHolder.textViewStatus.setText(pool.getPrivacy());
            try {
                viewHolder.textViewStartDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getFromDate()));
                viewHolder.textViewEndDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getToDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (pool.isJoined()) {
                viewHolder.button.setText("Joined");
                viewHolder.button.setEnabled(false);
//                viewHolder.button.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewPoolName;
        TextView textViewStatus;
        TextView textViewStartDate;
        TextView textViewEndDate;
        Button button;
    }


    public interface OnPoolsListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPoolsListAdapterFragmentInteraction(Uri uri, ArrayList<MyPool> pools);
    }
}
