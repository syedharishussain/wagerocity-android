package com.plego.wagerocity.android.controller;

import android.util.Log;
import android.util.SparseArray;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.OddHolder;

import static com.plego.wagerocity.android.model.OddHolder.*;

/**
 * Created by Hassan on 9/17/2015.
 */
public class GameOddController {


	public static final String TAG = GameOddController.class.getSimpleName();
	private String                 sportsName;
	private Game                   game;
	private SparseArray<OddHolder> oddHolderArray;
	private SparseArray<Boolean>   checkedArray;

	public GameOddController (String sportsName, Game game) {
		this.sportsName = sportsName;
		this.game = game;
		oddHolderArray = new SparseArray<>();
		checkedArray = new SparseArray<>();
	}

	public Game getGame () {
		return game;
	}

	public SparseArray<OddHolder> getOddHolderArray () {
		return oddHolderArray;
	}

	public SparseArray<Boolean> getCheckedArray () {
		return checkedArray;
	}

	public void setChecked (int id, boolean isChecked) {
		Log.d( TAG, "Checking " + id + "with value " + isChecked );
		checkedArray.put( id, isChecked );
	}

	public void addPointSpreadA (int id) {
		OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamANumber(),
				game.getTeamAOdd().getId(),
				game.getTeamAFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamAOdd().getPoint(),
				SINGLE,
				"3",
				POINT_SPREAD,
				game.getTeamAOdd().getPointSpreadString(),
				sportsName,
				true,
				game.getPoolCredits()
		);
		oddHolderArray.put( id, oddHolder );
	}

	public void addPointSpreadB (int id) {
		OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamBNumber(),
				game.getTeamBOdd().getId(),
				game.getTeamBFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamBOdd().getPoint(),
				SINGLE,
				"3",
				POINT_SPREAD,
				game.getTeamBOdd().getPointSpreadString(),
				sportsName,
				false,
				game.getPoolCredits()
		);
		oddHolderArray.put( id, oddHolder );
	}

	public void addMoneyLineA (int id) {
		final OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamANumber(),
				game.getTeamAOdd().getId(),
				game.getTeamAFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamAOdd().getMoney(),
				SINGLE,
				"1",
				MONEY_LINE,
				game.getTeamAOdd().getMoney(),
				sportsName,
				true,
				game.getPoolCredits()
		);

		oddHolderArray.put( id, oddHolder );
	}

	public void addMoneyLineB (int id) {
		final OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamBNumber(),
				game.getTeamBOdd().getId(),
				game.getTeamBFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamBOdd().getMoney(),
				SINGLE,
				"1",
				MONEY_LINE,
				game.getTeamBOdd().getMoney(),
				sportsName,
				false,
				game.getPoolCredits()
		);
		oddHolderArray.put( id, oddHolder );
	}

	public void addOverTeamA (int id) {
		final OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamANumber(),
				game.getTeamAOdd().getId(),
				game.getTeamAFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamAOdd().getOver(),
				SINGLE,
				"4",
				OVER,
				game.getTeamAOdd().getPointSpreadString(),
				sportsName,
				true,
				game.getPoolCredits()
		);
		oddHolderArray.put( id, oddHolder );
	}

	public void addUnderTeamA (int id) {
		OddHolder oddHolder = new OddHolder(
				0.0,
				game.getTeamANumber(),
				game.getTeamAOdd().getId(),
				game.getTeamBFullname(),
				game.getTeamAFullname() + " vs " + game.getTeamBFullname(),
				game.getTeamAOdd().getUnder(),
				SINGLE,
				"4",
				UNDER,
				game.getTeamAOdd().getPointSpreadString(),
				sportsName,
				false,
				game.getPoolCredits()
		);
		oddHolderArray.put( id, oddHolder );
	}
}
