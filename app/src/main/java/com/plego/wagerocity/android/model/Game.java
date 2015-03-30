package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game implements Parcelable{

    @SerializedName("cst_start_time")
    @Expose
    private String cstStartTime;
    @SerializedName("team_A_number")
    @Expose
    private String teamANumber;
    @SerializedName("team_B_number")
    @Expose
    private String teamBNumber;
    @SerializedName("team_A_pointspread")
    @Expose
    private String teamAPointspread;
    @SerializedName("team_B_pointspread")
    @Expose
    private String teamBPointspread;
    @SerializedName("team_A_name")
    @Expose
    private String teamAName;
    @SerializedName("team_B_name")
    @Expose
    private String teamBName;
    @SerializedName("team_A_fullname")
    @Expose
    private String teamAFullname;
    @SerializedName("team_B_fullname")
    @Expose
    private String teamBFullname;
    @SerializedName("team_A_nickname")
    @Expose
    private String teamANickname;
    @SerializedName("team_B_nickname")
    @Expose
    private String teamBNickname;
    @SerializedName("team_A_logo")
    @Expose
    private String teamALogo;
    @SerializedName("team_B_logo")
    @Expose
    private String teamBLogo;
    @SerializedName("team_A_points")
    @Expose
    private String teamAPoints;
    @SerializedName("team_B_points")
    @Expose
    private String teamBPoints;
    @SerializedName("team_A_moneyline")
    @Expose
    private String teamAMoneyline;
    @SerializedName("team_B_moneyline")
    @Expose
    private String teamBMoneyline;
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
     * The teamAPointspread
     */
    public String getTeamAPointspread() {
        return teamAPointspread;
    }

    /**
     *
     * @param teamAPointspread
     * The team_A_pointspread
     */
    public void setTeamAPointspread(String teamAPointspread) {
        this.teamAPointspread = teamAPointspread;
    }

    /**
     *
     * @return
     * The teamBPointspread
     */
    public String getTeamBPointspread() {
        return teamBPointspread;
    }

    /**
     *
     * @param teamBPointspread
     * The team_B_pointspread
     */
    public void setTeamBPointspread(String teamBPointspread) {
        this.teamBPointspread = teamBPointspread;
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
     * The teamAFullname
     */
    public String getTeamAFullname() {
        return teamAFullname;
    }

    /**
     *
     * @param teamAFullname
     * The team_A_fullname
     */
    public void setTeamAFullname(String teamAFullname) {
        this.teamAFullname = teamAFullname;
    }

    /**
     *
     * @return
     * The teamBFullname
     */
    public String getTeamBFullname() {
        return teamBFullname;
    }

    /**
     *
     * @param teamBFullname
     * The team_B_fullname
     */
    public void setTeamBFullname(String teamBFullname) {
        this.teamBFullname = teamBFullname;
    }

    /**
     *
     * @return
     * The teamANickname
     */
    public String getTeamANickname() {
        return teamANickname;
    }

    /**
     *
     * @param teamANickname
     * The team_A_nickname
     */
    public void setTeamANickname(String teamANickname) {
        this.teamANickname = teamANickname;
    }

    /**
     *
     * @return
     * The teamBNickname
     */
    public String getTeamBNickname() {
        return teamBNickname;
    }

    /**
     *
     * @param teamBNickname
     * The team_B_nickname
     */
    public void setTeamBNickname(String teamBNickname) {
        this.teamBNickname = teamBNickname;
    }

    /**
     *
     * @return
     * The teamALogo
     */
    public String getTeamALogo() {
        return teamALogo;
    }

    /**
     *
     * @param teamALogo
     * The team_A_logo
     */
    public void setTeamALogo(String teamALogo) {
        this.teamALogo = teamALogo;
    }

    /**
     *
     * @return
     * The teamBLogo
     */
    public String getTeamBLogo() {
        return teamBLogo;
    }

    /**
     *
     * @param teamBLogo
     * The team_B_logo
     */
    public void setTeamBLogo(String teamBLogo) {
        this.teamBLogo = teamBLogo;
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
     * The teamAMoneyline
     */
    public String getTeamAMoneyline() {
        return teamAMoneyline;
    }

    /**
     *
     * @param teamAMoneyline
     * The team_A_moneyline
     */
    public void setTeamAMoneyline(String teamAMoneyline) {
        this.teamAMoneyline = teamAMoneyline;
    }

    /**
     *
     * @return
     * The teamBMoneyline
     */
    public String getTeamBMoneyline() {
        return teamBMoneyline;
    }

    /**
     *
     * @param teamBMoneyline
     * The team_B_moneyline
     */
    public void setTeamBMoneyline(String teamBMoneyline) {
        this.teamBMoneyline = teamBMoneyline;
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
        dest.writeString(this.teamAPointspread);
        dest.writeString(this.teamBPointspread);
        dest.writeString(this.teamAName);
        dest.writeString(this.teamBName);
        dest.writeString(this.teamAFullname);
        dest.writeString(this.teamBFullname);
        dest.writeString(this.teamANickname);
        dest.writeString(this.teamBNickname);
        dest.writeString(this.teamALogo);
        dest.writeString(this.teamBLogo);
        dest.writeString(this.teamAPoints);
        dest.writeString(this.teamBPoints);
        dest.writeString(this.teamAMoneyline);
        dest.writeString(this.teamBMoneyline);
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
        this.teamAPointspread = in.readString();
        this.teamBPointspread = in.readString();
        this.teamAName = in.readString();
        this.teamBName = in.readString();
        this.teamAFullname = in.readString();
        this.teamBFullname = in.readString();
        this.teamANickname = in.readString();
        this.teamBNickname = in.readString();
        this.teamALogo = in.readString();
        this.teamBLogo = in.readString();
        this.teamAPoints = in.readString();
        this.teamBPoints = in.readString();
        this.teamAMoneyline = in.readString();
        this.teamBMoneyline = in.readString();
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