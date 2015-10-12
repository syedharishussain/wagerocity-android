package com.plego.wagerocity.android.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hassan Jawed on 10/12/2015.
 */
public class BetPayload {

	@SerializedName("odd_id")
	String oddId;
	@SerializedName("team_name")
	String teamName;
	@SerializedName("pos")
	String pos;
	@SerializedName("odd_type")
	String oddType;
	@SerializedName("match_det")
	String matchDetail;
	@SerializedName("odds_val")
	String oddsVal;
	@SerializedName("stake")
	String stake;
	@SerializedName("bet_type")
	String betType;
	@SerializedName("bet_parent")
	String betParent;
	@SerializedName("bet_created")
	String betCreated;
	@SerializedName("bet_result")
	String betResult;
	@SerializedName("bet_processed")
	String betProcessed;
	@SerializedName("pool_id")
	String poolId;
	@SerializedName("match_id")
	String matchId;
	@SerializedName("teaser_point")
	String teaserPoint;
	@SerializedName("bet_ot")
	String betOT;
	@SerializedName("bet_dis_odds")
	String betDisOddds;
	@SerializedName("league_name")
	String leagueName;
	@SerializedName("os_bet")
	String osBet;
	@SerializedName("bet_odd_val")
	String betOddVal;
	@SerializedName("is_pool_bet")
	String isPoolBet;
	@SerializedName("is_mobile_bet")
	String isMobileBet;

	public String getStake () {
		return stake;
	}

	public String getMatchDetail () {
		return matchDetail;
	}

	public String getBetResult () {
		return betResult;
	}
}

