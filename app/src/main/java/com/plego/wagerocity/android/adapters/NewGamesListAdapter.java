package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.*;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.controller.GameOddController;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Odd;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.List;

/**
 * Created by haris on 29/03/15.
 */
public class NewGamesListAdapter extends BaseAdapter {

	public static final String TAG = NewGamesListAdapter.class.getSimpleName();
	List<GameOddController> games;
	String                  sportsName;
	Context                 context;

	public NewGamesListAdapter (Context context, List<GameOddController> controllers, String sportsName) {
		this.games = controllers;
		this.context = context;
		this.sportsName = sportsName;
	}

	public int getCount () {
		return this.games.size();
	}

	@Override
	public GameOddController getItem (int position) {
		return this.games.get( position );
	}

	@Override
	public long getItemId (int position) {
		return position;
	}


	@Override
	public View getView (final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		final GameOddController gameOddController = games.get( position );
		final Game game = gameOddController.getGame();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			convertView = inflater.inflate( R.layout.layout_cell_games_list, parent, false );

			viewHolder = new ViewHolder( convertView );
			convertView.setTag( viewHolder );

			viewHolder.cbPointSpreadA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbPointSpreadB.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbMoneyLineA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbMoneyLineB.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbOverTeamA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbUnderTeamA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (game == null) return convertView;

		if (sportsName.equals( "mlb" )) {
			viewHolder.teamAPitcher.setVisibility( View.VISIBLE );
			viewHolder.teamAPitcher.setVisibility( View.VISIBLE );
			viewHolder.teamAPitcher.setText( (game.getTeamAPitcher() == null) ? "" : game.getTeamAPitcher() );
			viewHolder.teamBPitcher.setText( (game.getTeamBPitcher() == null) ? "" : game.getTeamBPitcher() );
		}

		Odd teamAOdd = game.getProxyTeamAOdd();
		viewHolder.cbPointSpreadA.setEnabled( teamAOdd.hasPointSpread() );
		viewHolder.cbMoneyLineA.setEnabled( teamAOdd.hasMoney() );
		viewHolder.cbOverTeamA.setEnabled( teamAOdd.hasOver() );
		viewHolder.cbUnderTeamA.setEnabled( teamAOdd.hasUnder() );
		if (game.getTeamAOdd() != null) {
			gameOddController.addPointSpreadA( viewHolder.cbPointSpreadA.getId() );
			gameOddController.addMoneyLineA( viewHolder.cbMoneyLineA.getId() );
			gameOddController.addOverTeamA( viewHolder.cbOverTeamA.getId() );
			gameOddController.addUnderTeamA( viewHolder.cbUnderTeamA.getId() );
		}

		Odd teamBOdd = game.getProxyTeamBOdd();
		viewHolder.cbPointSpreadB.setEnabled( teamBOdd.hasPointSpread() );
		viewHolder.cbMoneyLineB.setEnabled( teamBOdd.hasMoney() );
		if (game.getTeamBOdd() != null) {
			gameOddController.addPointSpreadB( viewHolder.cbPointSpreadB.getId() );
			gameOddController.addMoneyLineB( viewHolder.cbMoneyLineB.getId() );
		}

		viewHolder.tvPointSpreadA.setText( teamAOdd.getPointSpreadString() );
		viewHolder.tvMoneyLineA.setText( teamAOdd.getMoneyLineString() );
		viewHolder.tvOverTeamA.setText( teamAOdd.getOverString() );
		viewHolder.tvUnderTeamA.setText( teamAOdd.getUnderString() );
		String signedTotalMid = teamAOdd.getTotalMidString();
		viewHolder.tvOverUnder.setText( context.getResources()
											   .getString( R.string.text_over_under_value, signedTotalMid ) );
		viewHolder.tvPointSpreadB.setText( teamBOdd.getPointSpreadString() );
		viewHolder.tvMoneyLineB.setText( teamBOdd.getMoneyLineString() );

		viewHolder.cbPointSpreadA.setTag( position );
		viewHolder.cbPointSpreadB.setTag( position );
		viewHolder.cbMoneyLineA.setTag( position );
		viewHolder.cbMoneyLineB.setTag( position );
		viewHolder.cbOverTeamA.setTag(position);
		viewHolder.cbUnderTeamA.setTag( position );

		final SparseArray<Boolean> checkedArray = gameOddController.getCheckedArray();
		viewHolder.cbPointSpreadA.setChecked( checkedArray.get( viewHolder.cbPointSpreadA.getId(), false ) );
		viewHolder.cbPointSpreadB.setChecked( checkedArray.get( viewHolder.cbPointSpreadB.getId(), false ) );
		viewHolder.cbMoneyLineA.setChecked( checkedArray.get( viewHolder.cbMoneyLineA.getId(), false ) );
		viewHolder.cbMoneyLineB.setChecked( checkedArray.get( viewHolder.cbMoneyLineB.getId(), false ) );
		viewHolder.cbOverTeamA.setChecked( checkedArray.get( viewHolder.cbOverTeamA.getId(), false ) );
		viewHolder.cbUnderTeamA.setChecked( checkedArray.get( viewHolder.cbUnderTeamA.getId(), false ) );

		viewHolder.tvTeamAName.setText( game.getTeamADisplayName() );
		viewHolder.tvTeamBName.setText( game.getTeamBDisplayName() );
		try {
			viewHolder.tvGameDate.setText( AndroidUtils.getFormatedDate( game.getCstStartTime() ) );
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory( true ) // default
				.cacheOnDisk( true ) // default
				.showImageOnFail( R.drawable.sports )
				.showImageForEmptyUri( R.drawable.sports )
				.build();

		ImageLoader.getInstance().displayImage( game.getTeamALogo(), viewHolder.ivTeamAFlag, options );
		ImageLoader.getInstance().displayImage( game.getTeamBLogo(), viewHolder.ivTeamBFlag, options );

//		setBettingInfo( game, viewHolder );

		return convertView;
	}

	private void setBettingInfo (Game game, ViewHolder view) {
		String plB = "-", mlA = "-", mlB = "-", oA = "-", uA = "-", ou = "-";

		if (game.getTeamAOdd() != null) {
			CharSequence pointSpreadA = AndroidUtils.noneIfEmpty( game.getTeamAOdd()
																	  .getPoint() );
			view.cbPointSpreadA.setEnabled( game.getTeamAOdd()
												.getPoint() != null );
			view.tvPointSpreadA.setText( pointSpreadA );

			CharSequence moneyLineA = AndroidUtils.noneIfEmpty( game.getTeamAOdd()
																	.getMoney() );
			if (game.getTeamAOdd()
					.getMoney() != null) {
				mlA = game.getTeamAOdd()
						  .getMoney();
			} else {
				view.cbMoneyLineA.setEnabled( false );
			}

			if (game.getTeamAOdd()
					.getOver() != null) {
				oA = game.getTeamAOdd()
						 .getOver();
			} else {
				view.cbOverTeamA.setEnabled( false );
			}

			if (game.getTeamAOdd()
					.getUnder() != null) {
				uA = game.getTeamAOdd()
						 .getUnder();
			} else {
				view.cbUnderTeamA.setEnabled( false );
			}

		}

		if (game.getTeamBOdd() != null) {
			if (game.getTeamBOdd().getPoint() != null) { plB = game.getTeamBOdd().getPoint(); } else {
				view.cbPointSpreadB.setEnabled( false );
			}


			if (game.getTeamBOdd().getMoney() != null) {
				mlB = game.getTeamBOdd().getMoney(); } else {
				view.cbMoneyLineB.setEnabled( false );
			}
		}

		if (game.getTeamAOdd() == null) { view.tvPointSpreadA.setText( "-" ); } else {
			view.tvPointSpreadA.setText( game.getTeamAOdd()
											 .getPointSpreadString() == null ? "-" : game.getTeamAOdd()
																						 .getPointSpreadString() );
		}

		if (game.getTeamBOdd() == null) {
			view.tvPointSpreadB.setText( "-" ); } else {
			view.tvPointSpreadB.setText( game.getTeamBOdd()
											 .getPointSpreadString() == null ? "-" : game.getTeamBOdd()
																						 .getPointSpreadString() );
		}

		view.tvMoneyLineA.setText( AndroidUtils.getSignedOddValue( mlA ) );
		view.tvMoneyLineB.setText( AndroidUtils.getSignedOddValue( mlB ) );
		view.tvOverTeamA.setText( AndroidUtils.getSignedOddValue( oA ) );
		view.tvUnderTeamA.setText( AndroidUtils.getSignedOddValue( uA ) );
	}

	public static class ViewHolder {

		//
		@Bind(R.id.textview_cell_games_team_a)
		TextView  tvTeamAName;
		@Bind(R.id.textview_cell_games_team_b)
		TextView  tvTeamBName;
		@Bind(R.id.textview_cell_games_team_a_pitcher)
		TextView  teamAPitcher;
		@Bind(R.id.textview_cell_games_team_b_pitcher)
		TextView  teamBPitcher;
		@Bind(R.id.textview_cell_games_date_time)
		TextView  tvGameDate;
		@Bind(R.id.imageview_cell_games_list_team_a_flag)
		ImageView ivTeamAFlag;
		@Bind(R.id.imageview_cell_games_list_team_b_flag)
		ImageView ivTeamBFlag;
		@Bind(R.id.button_cell_games_list_pointspread_team_a)
		TextView  tvPointSpreadA;
		@Bind(R.id.button_cell_games_list_pointspread_team_b)
		TextView  tvPointSpreadB;
		@Bind(R.id.button_cell_games_list_money_line_team_a)
		TextView  tvMoneyLineA;
		@Bind(R.id.button_cell_games_list_money_line_team_b)
		TextView  tvMoneyLineB;
		@Bind(R.id.button_cell_games_list_over_team_a)
		TextView  tvOverTeamA;
		@Bind(R.id.button_cell_games_list_over_team_b)
		TextView  tvUnderTeamA;
		@Bind(R.id.textview_cell_games_list_over_under)
		TextView  tvOverUnder;
		@Bind(R.id.checkbox_cell_games_list_pointspread_a)
		CheckBox  cbPointSpreadA;
		@Bind(R.id.checkbox_cell_games_list_pointspread_b)
		CheckBox  cbPointSpreadB;
		@Bind(R.id.checkbox_cell_games_list_moneyline_a)
		CheckBox  cbMoneyLineA;
		@Bind(R.id.checkbox_cell_games_list_moneyline_b)
		CheckBox  cbMoneyLineB;
		@Bind(R.id.checkbox_cell_games_list_over_a)
		CheckBox  cbOverTeamA;
		@Bind(R.id.checkbox_cell_games_list_over_b)
		CheckBox  cbUnderTeamA;


		private ViewHolder (View itemView) {
			ButterKnife.bind( this, itemView );
		}
	}

	private class CheckChangeListener implements CompoundButton.OnCheckedChangeListener {

		private int position;

		public CheckChangeListener (int position) {
			this.position = position;
		}

		@Override
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			final int position = (int) buttonView.getTag();
			final int id = (int) buttonView.getId();
			final GameOddController gameOddController = getItem( position );
			gameOddController.setChecked( id, isChecked );
			Log.d( TAG, "Checking " + id + " with value " + isChecked + " @" + position );
		}
	}
}
