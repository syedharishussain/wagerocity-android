package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game implements Parcelable {

    @SerializedName("cst_start_time")
    @Expose
    private String cstStartTime;
    @SerializedName("team_A_number")
    @Expose
    private String teamANumber;
    @SerializedName("team_B_number")
    @Expose
    private String teamBNumber;
    @SerializedName("team_A_name")
    @Expose
    private String teamAName;
    @SerializedName("team_B_name")
    @Expose
    private String teamBName;
    @SerializedName("team_A_points")
    @Expose
    private String teamAPoints;
    @SerializedName("team_B_points")
    @Expose
    private String teamBPoints;
    @SerializedName("team_A_money")
    @Expose
    private String teamAMoney;
    @SerializedName("team_B_money")
    @Expose
    private String teamBMoney;
    @SerializedName("team_A_ML")
    @Expose
    private String teamAML;
    @SerializedName("team_B_ML")
    @Expose
    private String teamBML;
    @SerializedName("team_A_over_money")
    @Expose
    private String teamAOverMoney;
    @SerializedName("team_B_over_money")
    @Expose
    private String teamBOverMoney;
    @SerializedName("team_A_under_money")
    @Expose
    private String teamAUnderMoney;
    @SerializedName("team_B_under_money")
    @Expose
    private String teamBUnderMoney;

    /**
     *
     * @return
     * The cstStartTime
     */
    public String getCstStartTime() {
        return cstStartTime;
    }

    /**
     *
     * @param cstStartTime
     * The cst_start_time
     */
    public void setCstStartTime(String cstStartTime) {
        this.cstStartTime = cstStartTime;
    }

    /**
     *
     * @return
     * The teamANumber
     */
    public String getTeamANumber() {
        return teamANumber;
    }

    /**
     *
     * @param teamANumber
     * The team_A_number
     */
    public void setTeamANumber(String teamANumber) {
        this.teamANumber = teamANumber;
    }

    /**
     *
     * @return
     * The teamBNumber
     */
    public String getTeamBNumber() {
        return teamBNumber;
    }

    /**
     *
     * @param teamBNumber
     * The team_B_number
     */
    public void setTeamBNumber(String teamBNumber) {
        this.teamBNumber = teamBNumber;
    }

    /**
     *
     * @return
     * The teamAName
     */
    public String getTeamAName() {
        return teamAName;
    }

    /**
     *
     * @param teamAName
     * The team_A_name
     */
    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    /**
     *
     * @return
     * The teamBName
     */
    public String getTeamBName() {
        return teamBName;
    }

    /**
     *
     * @param teamBName
     * The team_B_name
     */
    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    /**
     *
     * @return
     * The teamAPoints
     */
    public String getTeamAPoints() {
        return teamAPoints;
    }

    /**
     *
     * @param teamAPoints
     * The team_A_points
     */
    public void setTeamAPoints(String teamAPoints) {
        this.teamAPoints = teamAPoints;
    }

    /**
     *
     * @return
     * The teamBPoints
     */
    public String getTeamBPoints() {
        return teamBPoints;
    }

    /**
     *
     * @param teamBPoints
     * The team_B_points
     */
    public void setTeamBPoints(String teamBPoints) {
        this.teamBPoints = teamBPoints;
    }

    /**
     *
     * @return
     * The teamAMoney
     */
    public String getTeamAMoney() {
        return teamAMoney;
    }

    /**
     *
     * @param teamAMoney
     * The team_A_money
     */
    public void setTeamAMoney(String teamAMoney) {
        this.teamAMoney = teamAMoney;
    }

    /**
     *
     * @return
     * The teamBMoney
     */
    public String getTeamBMoney() {
        return teamBMoney;
    }

    /**
     *
     * @param teamBMoney
     * The team_B_money
     */
    public void setTeamBMoney(String teamBMoney) {
        this.teamBMoney = teamBMoney;
    }

    /**
     *
     * @return
     * The teamAML
     */
    public String getTeamAML() {
        return teamAML;
    }

    /**
     *
     * @param teamAML
     * The team_A_ML
     */
    public void setTeamAML(String teamAML) {
        this.teamAML = teamAML;
    }

    /**
     *
     * @return
     * The teamBML
     */
    public String getTeamBML() {
        return teamBML;
    }

    /**
     *
     * @param teamBML
     * The team_B_ML
     */
    public void setTeamBML(String teamBML) {
        this.teamBML = teamBML;
    }

    /**
     *
     * @return
     * The teamAOverMoney
     */
    public String getTeamAOverMoney() {
        return teamAOverMoney;
    }

    /**
     *
     * @param teamAOverMoney
     * The team_A_over_money
     */
    public void setTeamAOverMoney(String teamAOverMoney) {
        this.teamAOverMoney = teamAOverMoney;
    }

    /**
     *
     * @return
     * The teamBOverMoney
     */
    public String getTeamBOverMoney() {
        return teamBOverMoney;
    }

    /**
     *
     * @param teamBOverMoney
     * The team_B_over_money
     */
    public void setTeamBOverMoney(String teamBOverMoney) {
        this.teamBOverMoney = teamBOverMoney;
    }

    /**
     *
     * @return
     * The teamAUnderMoney
     */
    public String getTeamAUnderMoney() {
        return teamAUnderMoney;
    }

    /**
     *
     * @param teamAUnderMoney
     * The team_A_under_money
     */
    public void setTeamAUnderMoney(String teamAUnderMoney) {
        this.teamAUnderMoney = teamAUnderMoney;
    }

    /**
     *
     * @return
     * The teamBUnderMoney
     */
    public String getTeamBUnderMoney() {
        return teamBUnderMoney;
    }

    /**
     *
     * @param teamBUnderMoney
     * The team_B_under_money
     */
    public void setTeamBUnderMoney(String teamBUnderMoney) {
        this.teamBUnderMoney = teamBUnderMoney;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cstStartTime);
        dest.writeString(this.teamANumber);
        dest.writeString(this.teamBNumber);
        dest.writeString(this.teamAName);
        dest.writeString(this.teamBName);
        dest.writeString(this.teamAPoints);
        dest.writeString(this.teamBPoints);
        dest.writeString(this.teamAMoney);
        dest.writeString(this.teamBMoney);
        dest.writeString(this.teamAML);
        dest.writeString(this.teamBML);
        dest.writeString(this.teamAOverMoney);
        dest.writeString(this.teamBOverMoney);
        dest.writeString(this.teamAUnderMoney);
        dest.writeString(this.teamBUnderMoney);
    }

    public Game() {
    }

    private Game(Parcel in) {
        this.cstStartTime = in.readString();
        this.teamANumber = in.readString();
        this.teamBNumber = in.readString();
        this.teamAName = in.readString();
        this.teamBName = in.readString();
        this.teamAPoints = in.readString();
        this.teamBPoints = in.readString();
        this.teamAMoney = in.readString();
        this.teamBMoney = in.readString();
        this.teamAML = in.readString();
        this.teamBML = in.readString();
        this.teamAOverMoney = in.readString();
        this.teamBOverMoney = in.readString();
        this.teamAUnderMoney = in.readString();
        this.teamBUnderMoney = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
