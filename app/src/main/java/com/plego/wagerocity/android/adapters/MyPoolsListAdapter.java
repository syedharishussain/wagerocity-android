package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 28/04/15.
 */
public class MyPoolsListAdapter extends BaseAdapter {

    private OnMyPoolsListAdapterFragmentInteractionListener mListner;
    private ArrayList<MyPool> pools;
    private Context context;
    private Boolean isMyPools;

    public MyPoolsListAdapter(ArrayList<MyPool> pools, Context context, Boolean isMyPools) {
        this.pools = pools;
        this.context = context;
        this.isMyPools = isMyPools;

        try {
            mListner = (OnMyPoolsListAdapterFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMyPoolsListAdapterFragmentInteractionListener");
        }
    }

    @Override
    public int getCount() {
        return this.pools.size();
    }

    @Override
    public MyPool getItem(int position) {
        return this.pools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

//        if (convertView == null) {

        viewHolder = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.layout_cell_my_pools, parent, false);

        viewHolder.textViewPoolName = (TextView) convertView.findViewById(R.id.textview_my_pools_cell_name);
        viewHolder.textViewStatus = (TextView) convertView.findViewById(R.id.textview_my_pools_cell_pool_open_close_status);
        viewHolder.textViewActive = (TextView) convertView.findViewById(R.id.textview_pool_cell_my_pools_members_active_inactive_status);
        viewHolder.textViewStartDate = (TextView) convertView.findViewById(R.id.textview_my_pools_cell_pool_start_date);
        viewHolder.textViewEndDate = (TextView) convertView.findViewById(R.id.textview_my_pools_cell_pool_end_date);
        viewHolder.textViewMembersCount = (TextView) convertView.findViewById(R.id.textview_my_pools_cell_pool_members_count);

        viewHolder.button = (Button) convertView.findViewById(R.id.button_my_pools_join_unjoin);

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyPool pool = pools.get(position);

                Uri uri = Uri.parse(context.getString(R.string.uri_open_my_pool_detail_fragment));
                mListner.onMyPoolsListAdapterFragmentInteraction(uri, pool);
            }
        });

//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        MyPool pool = this.pools.get(position);

        if (pool != null) {
            viewHolder.textViewPoolName.setText(pool.getName());
            viewHolder.textViewMembersCount.setText(pool.getJoinedMembers());
            viewHolder.textViewStatus.setText(pool.getPrivacy());
            try {
                viewHolder.textViewStartDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getFromDate()));
                viewHolder.textViewEndDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getToDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            viewHolder.textViewActive.setText(pool.getStatus());
//            if (isMyPools) viewHolder.button.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView textViewPoolName;
        TextView textViewStatus;
        TextView textViewActive;
        TextView textViewMembersCount;
        TextView textViewStartDate;
        TextView textViewEndDate;
        Button button;
    }

    public interface OnMyPoolsListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMyPoolsListAdapterFragmentInteraction(Uri uri, MyPool pool);
//        public void onMyPoolOpenGames(Uri uri, ArrayList<Game> games, String leagueName);
    }
}
