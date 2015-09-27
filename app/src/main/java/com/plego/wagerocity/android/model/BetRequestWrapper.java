package com.plego.wagerocity.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hassan on 9/18/2015.
 */
public class BetRequestWrapper {

	@SerializedName("num_bets")
	String betCount;

	@SerializedName("res_bets")
	List<BetRequest> bets;

	public void setBets (List<BetRequest> bets) {
		this.bets = bets;
		betCount = String.valueOf( bets.size() );
	}
}
