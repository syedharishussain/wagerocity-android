package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.model.OddType;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.utils.AndroidUtils;
import com.sromku.simple.fb.entities.Feed;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by haris on 06/04/15.
 */
public class MyPicksListAdapter extends BaseAdapter {

	ArrayList<Pick> picks;
	Context         context;
	@Inject ImageLoader                      imageLoader;
	private OnMyPickShareInteractionListener mListener;

	public MyPicksListAdapter (Context context, ArrayList<Pick> picks) {
		this.picks = picks;
		this.context = context;
		WagerocityApplication.component( context )
							 .inject( this );
		try {
			mListener = (OnMyPickShareInteractionListener) context;
		}
		catch (ClassCastException e) {
			throw new ClassCastException( context.toString()
										  + " must implement OnMyPickShareInteractionListener" );
		}
	}

	@Override
	public int getCount () {
		return this.picks.size();
	}

	@Override
	public Pick getItem (int position) {
		return this.picks.get( position );
	}

	@Override
	public long getItemId (int position) {
		return position;
	}

	@Override
	public View getView (final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		final Pick pick = picks.get( position );

		final String betTypeString = pick.getTeamName()
										 .equals( "Parlay" ) || pick.getTeamName()
																	.equals( "Teaser" ) ? pick.getTeamName() : AndroidUtils
				.getBetTypeFromBetOT( Integer.parseInt( pick.getBetOt() ), pick.getPos() );

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

			convertView = inflater.inflate( R.layout.layout_cell_my_picks_list, parent, false );
			viewHolder = new ViewHolder( convertView );
			convertView.setTag( viewHolder );
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.share.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick (View v) {

				String name = pick.getMatchDet();
				String caption = "I have put my stakes " + "$" + pick.getStake() + " on " + pick
						.getTeamName() + " " + betTypeString + " " + pick.getOddsVal();

				Feed feed = new Feed.Builder()
//                            .setMessage("message: " + caption)
						.setName( name )
//                            .setCaption(caption)
						.setDescription( caption )
						.setPicture( "https://www.wagerocity.com/user_data/images/logo1.png" )
						.setLink( "https://www.wagerocity.com" )
						.build();
				mListener.onMyPickShareInteraction( feed );

			}
		} );


		if (pick != null) {

			if (pick.getIsPoolBet() != null && pick.getPoolName() != null) {
				if (!pick.getIsPoolBet()
						 .equals( "0" )) {
					viewHolder.poolLabel.setVisibility( View.VISIBLE );
					viewHolder.poolName.setVisibility( View.VISIBLE );

					viewHolder.poolName.setText( pick.getPoolName() );
				}
			} else {
				viewHolder.poolLabel.setVisibility( View.GONE );
				viewHolder.poolName.setVisibility( View.GONE );
			}

			viewHolder.tvMatchTitle.setText( pick.getMatchDet() );
//            viewHolder.tvTeamBName.setText( pick.getTeamBName() );
			try {
				viewHolder.textViewDate.setText( pick.getCstStartTime() );
			}
			catch (ParseException e) {
				e.printStackTrace();
			}

			DecimalFormat f = new DecimalFormat( "##.00" );


			viewHolder.tvTeamName.setText( pick.getTeamName() );
			viewHolder.textViewBetType.setText( betTypeString );
			viewHolder.textViewBetTypeValue.setText( AndroidUtils.getSignedOddValue( pick.getOddsVal() ) );
			viewHolder.textViewStake.setText( "$" + pick.getStake() );

			viewHolder.textViewResult.setText( pick.getBetResult() );

			if (pick.getOddType()
					.equalsIgnoreCase( OddType.PARLEY ) || pick.getTeamName()
															   .equals( "Teaser" )) {
				viewHolder.textViewBetType.setVisibility( View.GONE );
				viewHolder.textViewBetTypeValue.setVisibility( View.GONE );
				viewHolder.textViewToWin.setText( "$" + String.valueOf( f.format( AndroidUtils
																						  .getParlayWinAmount( Float.parseFloat(
																													   pick.getStake() ),
																											   Double
																													   .parseDouble(
																															   pick.getOddsVal() ) ) ) ) );
			} else {
				viewHolder.textViewToWin.setText( "$" + String.valueOf( f.format( AndroidUtils
																						  .getToWinAmount( Double.parseDouble(
																												   pick.getStake() ),
																										   Double
																												   .parseDouble(
																														   pick.getOddsVal() ) ) ) ) );
			}

			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory( true ) // default
					.cacheOnDisk( true ) // default
					.showImageOnFail( AndroidUtils.getDrawableFromLeagueName( pick.getLeagueName() ) )
					.showImageForEmptyUri( AndroidUtils.getDrawableFromLeagueName( pick.getLeagueName() ) )
					.build();

			imageLoader.displayImage( pick.getTeamALogo(), viewHolder.imageViewA, options );
			imageLoader.displayImage( pick.getTeamBLogo(), viewHolder.imageViewB, options );

		}

		return convertView;
	}

	public interface OnMyPickShareInteractionListener {

		public void onMyPickShareInteraction (Feed feed);
	}

	class ViewHolder {

		@Bind(R.id.tv_match_title)
		TextView  tvMatchTitle;
		@Bind(R.id.textview_cell_my_picks_date_time)
		TextView  textViewDate;
		@Bind(R.id.tv_team_name)
		TextView  tvTeamName;
		@Bind(R.id.tv_pool_label)
		TextView  poolLabel;
		@Bind(R.id.tv_pool_name)
		TextView  poolName;
		@Bind(R.id.textview_cell_my_picks_bet_type)
		TextView  textViewBetType;
		@Bind(R.id.textview_cell_my_picks_bet_type_value)
		TextView  textViewBetTypeValue;
		@Bind(R.id.textview_cell_my_picks_stake_value)
		TextView  textViewStake;
		@Bind(R.id.textview_cell_my_picks_to_win_value)
		TextView  textViewToWin;
		@Bind(R.id.textview_cell_my_picks_result)
		TextView  textViewResult;
		@Bind(R.id.imageview_cell_my_picks_team_a_flag)
		ImageView imageViewA;
		@Bind(R.id.imageview_cell_my_picks_team_b_flag)
		ImageView imageViewB;
		@Bind(R.id.button_cell_my_picks_share)
		Button    share;

		ViewHolder (View view) {
			ButterKnife.bind( this, view );
		}
	}
}
