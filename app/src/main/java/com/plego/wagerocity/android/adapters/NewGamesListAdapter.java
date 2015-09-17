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

			viewHolder.cbPSA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
			viewHolder.cbPSB.setOnCheckedChangeListener( new CheckChangeListener( position ) );
//			viewHolder.cbMLA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
//			viewHolder.cbMLB.setOnCheckedChangeListener( new CheckChangeListener( position ) );
//			viewHolder.cbOA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
//			viewHolder.cbUA.setOnCheckedChangeListener( new CheckChangeListener( position ) );
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (game == null) return convertView;

		gameOddController.addPointSpreadA( viewHolder.cbPSA.getId() );
		gameOddController.addPointSpreadB( viewHolder.cbPSB.getId() );
		gameOddController.addMoneyLineA( viewHolder.cbMLA.getId() );
		gameOddController.addMoneyLineB( viewHolder.cbMLB.getId() );
		gameOddController.addOverTeamA( viewHolder.cbOA.getId() );
		gameOddController.addUnderTeamA( viewHolder.cbUA.getId() );

		if (sportsName.equals( "mlb" )) {
			viewHolder.teamAPitcher.setVisibility( View.VISIBLE );
			viewHolder.teamAPitcher.setVisibility( View.VISIBLE );
			viewHolder.teamAPitcher.setText( (game.getTeamAPitcher() == null) ? "" : game.getTeamAPitcher() );
			viewHolder.teamBPitcher.setText( (game.getTeamBPitcher() == null) ? "" : game.getTeamBPitcher() );
		}

		if (game.getTeamAOdd() == null) {
			viewHolder.cbPSA.setEnabled( false );
			viewHolder.cbMLA.setEnabled( false );
			viewHolder.cbOA.setEnabled( false );
			viewHolder.cbUA.setEnabled( false );
		}

		if (game.getTeamBOdd() == null) {
			viewHolder.cbPSB.setEnabled( false );
			viewHolder.cbMLB.setEnabled( false );
		}

		viewHolder.cbPSA.setTag( position );
		viewHolder.cbPSB.setTag( position );
		viewHolder.cbMLA.setTag( position );
		viewHolder.cbMLB.setTag( position );
		viewHolder.cbOA.setTag( position );
		viewHolder.cbUA.setTag( position );

		final SparseArray<Boolean> checkedArray = gameOddController.getCheckedArray();
		viewHolder.cbPSA.setChecked( checkedArray.get( viewHolder.cbPSA.getId(), false ) );
		viewHolder.cbPSB.setChecked( checkedArray.get( viewHolder.cbPSB.getId(), false ) );
		viewHolder.cbMLA.setChecked( checkedArray.get( viewHolder.cbMLA.getId(), false ) );
		viewHolder.cbMLB.setChecked( checkedArray.get( viewHolder.cbMLB.getId(), false ) );
		viewHolder.cbOA.setChecked( checkedArray.get( viewHolder.cbOA.getId(), false ) );
		viewHolder.cbUA.setChecked( checkedArray.get( viewHolder.cbUA.getId(), false ) );

		viewHolder.textViewTeamA.setText( game.getTeamAFullname() );
		viewHolder.textViewTeamB.setText( game.getTeamBFullname() );
		try {
			viewHolder.textViewDate.setText( AndroidUtils.getFormatedDate( game.getCstStartTime() ) );
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

		ImageLoader.getInstance().displayImage( game.getTeamALogo(), viewHolder.imageViewA, options );
		ImageLoader.getInstance().displayImage( game.getTeamBLogo(), viewHolder.imageViewB, options );

		setBettingInfo( game, viewHolder );

		return convertView;
	}

	private void setBettingInfo (Game game, ViewHolder view) {
		String plA = "-", plB = "-", mlA = "-", mlB = "-", oA = "-", uA = "-", ou = "-";

		if (game.getTeamAOdd() != null) {

			if (game.getTeamAOdd().getPoint() != null) { plA = game.getTeamAOdd().getPoint(); } else {
				view.cbPSA.setEnabled( false );
			}

			if (game.getTeamAOdd().getMoney() != null) { mlA = game.getTeamAOdd().getMoney(); } else {
				view.cbMLA.setEnabled( false );
			}

			if (game.getTeamAOdd().getOver() != null) { oA = game.getTeamAOdd().getOver(); } else {
				view.cbOA.setEnabled( false );
			}

			if (game.getTeamAOdd().getUnder() != null) { uA = game.getTeamAOdd().getUnder(); } else {
				view.cbUA.setEnabled( false );
			}

		}

		if (game.getTeamBOdd() != null) {
			if (game.getTeamBOdd().getPoint() != null) { plB = game.getTeamBOdd().getPoint(); } else {
				view.cbPSB.setEnabled( false );
			}


			if (game.getTeamBOdd().getMoney() != null) { mlB = game.getTeamBOdd().getMoney(); } else {
				view.cbMLB.setEnabled( false );
			}
		}

		if (game.getTeamAOdd() == null) { view.tvPSA.setText( "-" ); } else {
			view.tvPSA.setText( game.getTeamAOdd().getPointSpreadString() == null ? "-" : game.getTeamAOdd()
					.getPointSpreadString() );
		}

		if (game.getTeamBOdd() == null) { view.tvPSB.setText( "-" ); } else {
			view.tvPSB.setText( game.getTeamBOdd().getPointSpreadString() == null ? "-" : game.getTeamBOdd()
					.getPointSpreadString() );
		}

		view.tvMLA.setText( AndroidUtils.getSignedOddValue( mlA ) );
		view.tvMLB.setText( AndroidUtils.getSignedOddValue( mlB ) );
		view.tvOA.setText( AndroidUtils.getSignedOddValue( oA ) );
		view.tvUA.setText( AndroidUtils.getSignedOddValue( uA ) );

	}

	public static class ViewHolder {

		//
		@Bind(R.id.textview_cell_games_team_a)
		TextView  textViewTeamA;
		@Bind(R.id.textview_cell_games_team_b)
		TextView  textViewTeamB;
		@Bind(R.id.textview_cell_games_team_a_pitcher)
		TextView  teamAPitcher;
		@Bind(R.id.textview_cell_games_team_b_pitcher)
		TextView  teamBPitcher;
		@Bind(R.id.textview_cell_games_date_time)
		TextView  textViewDate;
		@Bind(R.id.imageview_cell_games_list_team_a_flag)
		ImageView imageViewA;
		@Bind(R.id.imageview_cell_games_list_team_b_flag)
		ImageView imageViewB;
		@Bind(R.id.button_cell_games_list_pointspread_team_a)
		TextView  tvPSA;
		@Bind(R.id.button_cell_games_list_pointspread_team_b)
		TextView  tvPSB;
		@Bind(R.id.button_cell_games_list_money_line_team_a)
		TextView  tvMLA;
		@Bind(R.id.button_cell_games_list_money_line_team_b)
		TextView  tvMLB;
		@Bind(R.id.button_cell_games_list_over_team_a)
		TextView  tvOA;
		@Bind(R.id.button_cell_games_list_over_team_b)
		TextView  tvUA;
		@Bind(R.id.textview_cell_games_list_over_under)
		TextView  tvOU;
		@Bind(R.id.checkbox_cell_games_list_pointspread_a)
		CheckBox  cbPSA;
		@Bind(R.id.checkbox_cell_games_list_pointspread_b)
		CheckBox  cbPSB;
		@Bind(R.id.checkbox_cell_games_list_moneyline_a)
		CheckBox  cbMLA;
		@Bind(R.id.checkbox_cell_games_list_moneyline_b)
		CheckBox  cbMLB;
		@Bind(R.id.checkbox_cell_games_list_over_a)
		CheckBox  cbOA;
		@Bind(R.id.checkbox_cell_games_list_over_b)
		CheckBox  cbUA;


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
