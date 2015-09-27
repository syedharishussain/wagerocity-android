package com.plego.wagerocity.android.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Hassan on 9/18/2015.
 */
public class BetRequest {

	@SerializedName("odd_id")
	String id;            //Odd id
	@SerializedName("pool_id")
	String poolId;
	@SerializedName("match_id")
	String matchId;
	@SerializedName("is_pool_bet")
	String isPoolBet;    // 0 (NO)| 1(YES)
	@SerializedName("pool_name")
	String poolName;
	@SerializedName("pos")
	String pos;            // over|under
	@SerializedName("odd_type")
	String oddType;            // ao
	@SerializedName("match_det")
	String matchDetail;        // Same as name
	@SerializedName("input_stake")
	String inputStake;                // no
	@SerializedName("bet_checked")
	String isBetChecked;            //yes|no
	@SerializedName("odds_val")
	String oddsValue;                //oddHolder.getBetTypeSPT().equals( PARLAY ) ? oddHolder.getParlayValue()
	//.toString() : oddHolder.getOddValue();
	@SerializedName("stake")
	String stake;                    //oddHolder.getRiskValue();
	@SerializedName("team_name")
	String detailedTeamName;        // Team name + oddvalue
	@SerializedName("bet_ot")
	String oddTypeInt;                // 1 moneyline, 3 pointspread, 4 over|under
	@SerializedName("mat_cond")
	String matchCondition;                //  EMPTY | FIRST_HALF | SECOND_HALF | GAME
	@SerializedName("league_name")
	String sportsName;                    // oddHolder.getLeagueName
	@SerializedName("usr_id")
	String userId;
	@SerializedName("place_bet_type")
	String placeBetType;
	@SerializedName("num_bets")
	String numBets;


//	@SerializedName("rowid")
//	String rowId;        //1520167over1Scotland vs Georgiaao  md5(id,pos+"1",matchDetail,oddType)
//	String price;        //1
//	String name;        // TeamAName vs TeamBName
//	@SerializedName("pool")
//	String pool;

//	public void generateRowId () throws NoSuchAlgorithmException {
//		this.rowId = md5( id, pos + "1", matchDetail, oddType );
//	}

	private String md5 (String... strings) throws NoSuchAlgorithmException {
		StringBuilder builder = new StringBuilder();
		for (String s : strings) {
			builder.append( s );
		}
		MessageDigest md5 = MessageDigest.getInstance( "MD5" );
		md5.update( builder.toString().getBytes(), 0, builder.length() );
		byte[] digest = md5.digest();

		return new BigInteger( 1, digest ).toString( 16 );
	}

	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}

	public String getPoolId () {
		return poolId;
	}

	public void setPoolId (String poolId) {
		this.poolId = poolId;
	}

	public String getMatchId () {
		return matchId;
	}

	public void setMatchId (String matchId) {
		this.matchId = matchId;
	}

	public String getIsPoolBet () {
		return isPoolBet;
	}

	public void setIsPoolBet (String isPoolBet) {
		this.isPoolBet = isPoolBet;
	}

	public String getPoolName () {
		return poolName;
	}

	public void setPoolName (String poolName) {
		this.poolName = poolName;
	}

	public String getPos () {
		return pos;
	}

	public void setPos (String pos) {
		this.pos = pos;
	}

	public String getOddType () {
		return oddType;
	}

	public void setOddType (String oddType) {
		this.oddType = oddType;
	}

	public String getMatchDetail () {
		return matchDetail;
	}

	public void setMatchDetail (String matchDetail) {
		this.matchDetail = matchDetail;
	}

	public String getInputStake () {
		return inputStake;
	}

	public void setInputStake (String inputStake) {
		this.inputStake = inputStake;
	}

	public String getIsBetChecked () {
		return isBetChecked;
	}

	public void setIsBetChecked (String isBetChecked) {
		this.isBetChecked = isBetChecked;
	}

	public String getOddsValue () {
		return oddsValue;
	}

	public void setOddsValue (String oddsValue) {
		this.oddsValue = oddsValue;
	}

	public String getStake () {
		return stake;
	}

	public void setStake (String stake) {
		this.stake = stake;
	}

	public String getDetailedTeamName () {
		return detailedTeamName;
	}

	public void setDetailedTeamName (String detailedTeamName) {
		this.detailedTeamName = detailedTeamName;
	}

	public String getOddTypeInt () {
		return oddTypeInt;
	}

	public void setOddTypeInt (String oddTypeInt) {
		this.oddTypeInt = oddTypeInt;
	}

	public String getMatchCondition () {
		return matchCondition;
	}

	public void setMatchCondition (String matchCondition) {
		this.matchCondition = matchCondition;
	}

	public String getSportsName () {
		return sportsName;
	}

	public void setSportsName (String sportsName) {
		this.sportsName = sportsName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlaceBetType() {
		return placeBetType;
	}

	public void setPlaceBetType(String placeBetType) {
		this.placeBetType = placeBetType;
	}

	public String getNumBets() {
		return numBets;
	}

	public void setNumBets(String numBets) {
		this.numBets = numBets;
	}
}
