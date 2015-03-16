package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pool {

    @SerializedName("pool_id")
    @Expose
    private String poolId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String name;
    @Expose
    private String motto;
    @Expose
    private String description;
    @Expose
    private String privacy;
    @Expose
    private String size;
    @SerializedName("limited_size")
    @Expose
    private String limitedSize;
    @SerializedName("match_up")
    @Expose
    private String matchUp;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("reg_start_date")
    @Expose
    private String regStartDate;
    @SerializedName("reg_end_date")
    @Expose
    private String regEndDate;
    @Expose
    private String games;
    @SerializedName("pick_game")
    @Expose
    private String pickGame;
    @SerializedName("pickgame_date_from")
    @Expose
    private String pickgameDateFrom;
    @SerializedName("pickgame_date_to")
    @Expose
    private String pickgameDateTo;
    @SerializedName("wager_type")
    @Expose
    private String wagerType;
    @SerializedName("pool_type")
    @Expose
    private String poolType;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("joined_members")
    @Expose
    private String joinedMembers;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @Expose
    private Object amount;
    @SerializedName("min_people")
    @Expose
    private String minPeople;

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
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The motto
     */
    public String getMotto() {
        return motto;
    }

    /**
     *
     * @param motto
     * The motto
     */
    public void setMotto(String motto) {
        this.motto = motto;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     *
     * @param privacy
     * The privacy
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    /**
     *
     * @return
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The limitedSize
     */
    public String getLimitedSize() {
        return limitedSize;
    }

    /**
     *
     * @param limitedSize
     * The limited_size
     */
    public void setLimitedSize(String limitedSize) {
        this.limitedSize = limitedSize;
    }

    /**
     *
     * @return
     * The matchUp
     */
    public String getMatchUp() {
        return matchUp;
    }

    /**
     *
     * @param matchUp
     * The match_up
     */
    public void setMatchUp(String matchUp) {
        this.matchUp = matchUp;
    }

    /**
     *
     * @return
     * The fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     *
     * @param fromDate
     * The from_date
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     *
     * @return
     * The toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     *
     * @param toDate
     * The to_date
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     *
     * @return
     * The regStartDate
     */
    public String getRegStartDate() {
        return regStartDate;
    }

    /**
     *
     * @param regStartDate
     * The reg_start_date
     */
    public void setRegStartDate(String regStartDate) {
        this.regStartDate = regStartDate;
    }

    /**
     *
     * @return
     * The regEndDate
     */
    public String getRegEndDate() {
        return regEndDate;
    }

    /**
     *
     * @param regEndDate
     * The reg_end_date
     */
    public void setRegEndDate(String regEndDate) {
        this.regEndDate = regEndDate;
    }

    /**
     *
     * @return
     * The games
     */
    public String getGames() {
        return games;
    }

    /**
     *
     * @param games
     * The games
     */
    public void setGames(String games) {
        this.games = games;
    }

    /**
     *
     * @return
     * The pickGame
     */
    public String getPickGame() {
        return pickGame;
    }

    /**
     *
     * @param pickGame
     * The pick_game
     */
    public void setPickGame(String pickGame) {
        this.pickGame = pickGame;
    }

    /**
     *
     * @return
     * The pickgameDateFrom
     */
    public String getPickgameDateFrom() {
        return pickgameDateFrom;
    }

    /**
     *
     * @param pickgameDateFrom
     * The pickgame_date_from
     */
    public void setPickgameDateFrom(String pickgameDateFrom) {
        this.pickgameDateFrom = pickgameDateFrom;
    }

    /**
     *
     * @return
     * The pickgameDateTo
     */
    public String getPickgameDateTo() {
        return pickgameDateTo;
    }

    /**
     *
     * @param pickgameDateTo
     * The pickgame_date_to
     */
    public void setPickgameDateTo(String pickgameDateTo) {
        this.pickgameDateTo = pickgameDateTo;
    }

    /**
     *
     * @return
     * The wagerType
     */
    public String getWagerType() {
        return wagerType;
    }

    /**
     *
     * @param wagerType
     * The wager_type
     */
    public void setWagerType(String wagerType) {
        this.wagerType = wagerType;
    }

    /**
     *
     * @return
     * The poolType
     */
    public String getPoolType() {
        return poolType;
    }

    /**
     *
     * @param poolType
     * The pool_type
     */
    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    /**
     *
     * @return
     * The dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     * The date_time
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     *
     * @return
     * The joinedMembers
     */
    public String getJoinedMembers() {
        return joinedMembers;
    }

    /**
     *
     * @param joinedMembers
     * The joined_members
     */
    public void setJoinedMembers(String joinedMembers) {
        this.joinedMembers = joinedMembers;
    }

    /**
     *
     * @return
     * The isPaid
     */
    public String getIsPaid() {
        return isPaid;
    }

    /**
     *
     * @param isPaid
     * The is_paid
     */
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    /**
     *
     * @return
     * The amount
     */
    public Object getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(Object amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The minPeople
     */
    public String getMinPeople() {
        return minPeople;
    }

    /**
     *
     * @param minPeople
     * The min_people
     */
    public void setMinPeople(String minPeople) {
        this.minPeople = minPeople;
    }

}
