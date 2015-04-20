package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bet implements Parcelable{

    @SerializedName("bet_id")
    @Expose
    private String betId;
    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @SerializedName("odd_id")
    @Expose
    private String oddId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @Expose
    private String pos;
    @SerializedName("odd_type")
    @Expose
    private String oddType;
    @SerializedName("match_det")
    @Expose
    private String matchDet;
    @SerializedName("odds_val")
    @Expose
    private String oddsVal;
    @Expose
    private String stake;
    @SerializedName("bet_type")
    @Expose
    private String betType;
    @SerializedName("bet_parent")
    @Expose
    private String betParent;
    @SerializedName("bet_created")
    @Expose
    private String betCreated;
    @SerializedName("bet_result")
    @Expose
    private String betResult;
    @SerializedName("bet_processed")
    @Expose
    private String betProcessed;
    @SerializedName("pool_id")
    @Expose
    private String poolId;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("teaser_point")
    @Expose
    private String teaserPoint;
    @SerializedName("bet_ot")
    @Expose
    private String betOt;
    @SerializedName("bet_dis_odds")
    @Expose
    private String betDisOdds;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("os_bet")
    @Expose
    private String osBet;
    @SerializedName("bet_odd_val")
    @Expose
    private String betOddVal;
    @SerializedName("is_pool_bet")
    @Expose
    private String isPoolBet;

    /**
     *
     * @return
     * The betId
     */
    public String getBetId() {
        return betId;
    }

    /**
     *
     * @param betId
     * The bet_id
     */
    public void setBetId(String betId) {
        this.betId = betId;
    }

    /**
     *
     * @return
     * The usrId
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     *
     * @param usrId
     * The usr_id
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     *
     * @return
     * The oddId
     */
    public String getOddId() {
        return oddId;
    }

    /**
     *
     * @param oddId
     * The odd_id
     */
    public void setOddId(String oddId) {
        this.oddId = oddId;
    }

    /**
     *
     * @return
     * The teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     *
     * @param teamName
     * The team_name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     *
     * @return
     * The pos
     */
    public String getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     * The pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     * The oddType
     */
    public String getOddType() {
        return oddType;
    }

    /**
     *
     * @param oddType
     * The odd_type
     */
    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    /**
     *
     * @return
     * The matchDet
     */
    public String getMatchDet() {
        return matchDet;
    }

    /**
     *
     * @param matchDet
     * The match_det
     */
    public void setMatchDet(String matchDet) {
        this.matchDet = matchDet;
    }

    /**
     *
     * @return
     * The oddsVal
     */
    public String getOddsVal() {
        return oddsVal;
    }

    /**
     *
     * @param oddsVal
     * The odds_val
     */
    public void setOddsVal(String oddsVal) {
        this.oddsVal = oddsVal;
    }

    /**
     *
     * @return
     * The stake
     */
    public String getStake() {
        return stake;
    }

    /**
     *
     * @param stake
     * The stake
     */
    public void setStake(String stake) {
        this.stake = stake;
    }

    /**
     *
     * @return
     * The betType
     */
    public String getBetType() {
        return betType;
    }

    /**
     *
     * @param betType
     * The bet_type
     */
    public void setBetType(String betType) {
        this.betType = betType;
    }

    /**
     *
     * @return
     * The betParent
     */
    public String getBetParent() {
        return betParent;
    }

    /**
     *
     * @param betParent
     * The bet_parent
     */
    public void setBetParent(String betParent) {
        this.betParent = betParent;
    }

    /**
     *
     * @return
     * The betCreated
     */
    public String getBetCreated() {
        return betCreated;
    }

    /**
     *
     * @param betCreated
     * The bet_created
     */
    public void setBetCreated(String betCreated) {
        this.betCreated = betCreated;
    }

    /**
     *
     * @return
     * The betResult
     */
    public String getBetResult() {
        return betResult;
    }

    /**
     *
     * @param betResult
     * The bet_result
     */
    public void setBetResult(String betResult) {
        this.betResult = betResult;
    }

    /**
     *
     * @return
     * The betProcessed
     */
    public String getBetProcessed() {
        return betProcessed;
    }

    /**
     *
     * @param betProcessed
     * The bet_processed
     */
    public void setBetProcessed(String betProcessed) {
        this.betProcessed = betProcessed;
    }

    /**
     *
     * @return
     * The poolId
     */
    public String getPoolId() {
        return poolId;
    }

    /**
     *
     * @param poolId
     * The pool_id
     */
    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    /**
     *
     * @return
     * The matchId
     */
    public String getMatchId() {
        return matchId;
    }

    /**
     *
     * @param matchId
     * The match_id
     */
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    /**
     *
     * @return
     * The teaserPoint
     */
    public String getTeaserPoint() {
        return teaserPoint;
    }

    /**
     *
     * @param teaserPoint
     * The teaser_point
     */
    public void setTeaserPoint(String teaserPoint) {
        this.teaserPoint = teaserPoint;
    }

    /**
     *
     * @return
     * The betOt
     */
    public String getBetOt() {
        return betOt;
    }

    /**
     *
     * @param betOt
     * The bet_ot
     */
    public void setBetOt(String betOt) {
        this.betOt = betOt;
    }

    /**
     *
     * @return
     * The betDisOdds
     */
    public String getBetDisOdds() {
        return betDisOdds;
    }

    /**
     *
     * @param betDisOdds
     * The bet_dis_odds
     */
    public void setBetDisOdds(String betDisOdds) {
        this.betDisOdds = betDisOdds;
    }

    /**
     *
     * @return
     * The leagueName
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     *
     * @param leagueName
     * The league_name
     */
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    /**
     *
     * @return
     * The osBet
     */
    public String getOsBet() {
        return osBet;
    }

    /**
     *
     * @param osBet
     * The os_bet
     */
    public void setOsBet(String osBet) {
        this.osBet = osBet;
    }

    /**
     *
     * @return
     * The betOddVal
     */
    public String getBetOddVal() {
        return betOddVal;
    }

    /**
     *
     * @param betOddVal
     * The bet_odd_val
     */
    public void setBetOddVal(String betOddVal) {
        this.betOddVal = betOddVal;
    }

    /**
     *
     * @return
     * The isPoolBet
     */
    public String getIsPoolBet() {
        return isPoolBet;
    }

    /**
     *
     * @param isPoolBet
     * The is_pool_bet
     */
    public void setIsPoolBet(String isPoolBet) {
        this.isPoolBet = isPoolBet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.betId);
        dest.writeString(this.usrId);
        dest.writeString(this.oddId);
        dest.writeString(this.teamName);
        dest.writeString(this.pos);
        dest.writeString(this.oddType);
        dest.writeString(this.matchDet);
        dest.writeString(this.oddsVal);
        dest.writeString(this.stake);
        dest.writeString(this.betType);
        dest.writeString(this.betParent);
        dest.writeString(this.betCreated);
        dest.writeString(this.betResult);
        dest.writeString(this.betProcessed);
        dest.writeString(this.poolId);
        dest.writeString(this.matchId);
        dest.writeString(this.teaserPoint);
        dest.writeString(this.betOt);
        dest.writeString(this.betDisOdds);
        dest.writeString(this.leagueName);
        dest.writeString(this.osBet);
        dest.writeString(this.betOddVal);
        dest.writeString(this.isPoolBet);
    }

    public Bet() {
    }

    private Bet(Parcel in) {
        this.betId = in.readString();
        this.usrId = in.readString();
        this.oddId = in.readString();
        this.teamName = in.readString();
        this.pos = in.readString();
        this.oddType = in.readString();
        this.matchDet = in.readString();
        this.oddsVal = in.readString();
        this.stake = in.readString();
        this.betType = in.readString();
        this.betParent = in.readString();
        this.betCreated = in.readString();
        this.betResult = in.readString();
        this.betProcessed = in.readString();
        this.poolId = in.readString();
        this.matchId = in.readString();
        this.teaserPoint = in.readString();
        this.betOt = in.readString();
        this.betDisOdds = in.readString();
        this.leagueName = in.readString();
        this.osBet = in.readString();
        this.betOddVal = in.readString();
        this.isPoolBet = in.readString();
    }

    public static final Creator<Bet> CREATOR = new Creator<Bet>() {
        public Bet createFromParcel(Parcel source) {
            return new Bet(source);
        }

        public Bet[] newArray(int size) {
            return new Bet[size];
        }
    };
}
