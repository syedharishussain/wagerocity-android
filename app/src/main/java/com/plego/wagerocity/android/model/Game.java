package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @Expose
    private List<String> sports = new ArrayList<String>();
    @SerializedName("team_A_Odds")
    @Expose
    private List<Odd> teamAOdds = new ArrayList<>();
    @SerializedName("team_B_Odds")
    @Expose
    private List<Odd> teamBOdds = new ArrayList<>();
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

    public Double getPointSpreadA() {
        return pointSpreadA;
    }

    public void setPointSpreadA(Double pointSpreadA) {
        this.pointSpreadA = pointSpreadA;
    }

    public Double getPointSpreadB() {
        return pointSpreadB;
    }

    public void setPointSpreadB(Double pointSpreadB) {
        this.pointSpreadB = pointSpreadB;
    }

    public Double getMoneyLineA() {
        return moneyLineA;
    }

    public void setMoneyLineA(Double moneyLineA) {
        this.moneyLineA = moneyLineA;
    }

    public Double getMoneyLineB() {
        return moneyLineB;
    }

    public void setMoneyLineB(Double moneyLineB) {
        this.moneyLineB = moneyLineB;
    }

    public Double getOverA() {
        return overA;
    }

    public void setOverA(Double overA) {
        this.overA = overA;
    }

    public Double getUnderA() {
        return underA;
    }

    public void setUnderA(Double underA) {
        this.underA = underA;
    }

    public Double getOverB() {
        return overB;
    }

    public void setOverB(Double overB) {
        this.overB = overB;
    }

    public Double getUnderB() {
        return underB;
    }

    public void setUnderB(Double underB) {
        this.underB = underB;
    }

    public Double getPointA() {
        return pointA;
    }

    public void setPointA(Double pointA) {
        this.pointA = pointA;
    }

    public Double getPointB() {
        return pointB;
    }

    public void setPointB(Double pointB) {
        this.pointB = pointB;
    }

    public String getPointSpreadStringA() {
        return pointSpreadStringA;
    }

    public void setPointSpreadStringA(String pointSpreadStringA) {
        this.pointSpreadStringA = pointSpreadStringA;
    }

    public String getPointSpreadStringB() {
        return pointSpreadStringB;
    }

    public void setPointSpreadStringB(String pointSpreadStringB) {
        this.pointSpreadStringB = pointSpreadStringB;
    }

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }


    private Double pointSpreadA = 0.0;

    private Double pointSpreadB = 0.0;

    private Double moneyLineA = 0.0;

    private Double moneyLineB = 0.0;

    private Double overA = 0.0;

    private Double underA = 0.0;

    private Double overB = 0.0;

    private Double underB = 0.0;

    private Double pointA = 0.0;

    private Double pointB = 0.0;

    private String pointSpreadStringA = "-";

    private String pointSpreadStringB = "-";

    private String sportsName = "";

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
     * The sports
     */
    public List<String> getSports() {
        return sports;
    }

    /**
     *
     * @param sports
     * The sports
     */
    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    /**
     *
     * @return
     * The teamAOdds
     */
    public List<Odd> getTeamAOdds() {
        return teamAOdds;
    }

    /**
     *
     * @param teamAOdds
     * The team_A_Odds
     */
    public void setTeamAOdds(List<Odd> teamAOdds) {
        this.teamAOdds = teamAOdds;
    }

    /**
     *
     * @return
     * The teamBOdds
     */
    public List<Odd> getTeamBOdds() {
        return teamBOdds;
    }

    /**
     *
     * @param teamBOdds
     * The team_B_Odds
     */
    public void setTeamBOdds(List<Odd> teamBOdds) {
        this.teamBOdds = teamBOdds;
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
        return teamAFullname.equals("") ? getTeamAName() : teamAFullname;
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
        return teamBFullname.equals("") ? getTeamBName() : teamBFullname;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cstStartTime);
        dest.writeString(this.teamANumber);
        dest.writeString(this.teamBNumber);
        dest.writeList(this.sports);
        dest.writeTypedList(teamAOdds);
        dest.writeTypedList(teamBOdds);
        dest.writeString(this.teamAName);
        dest.writeString(this.teamBName);
        dest.writeString(this.teamAFullname);
        dest.writeString(this.teamBFullname);
        dest.writeString(this.teamANickname);
        dest.writeString(this.teamBNickname);
        dest.writeString(this.teamALogo);
        dest.writeString(this.teamBLogo);
        dest.writeValue(this.pointSpreadA);
        dest.writeValue(this.pointSpreadB);
        dest.writeValue(this.moneyLineA);
        dest.writeValue(this.moneyLineB);
        dest.writeValue(this.overA);
        dest.writeValue(this.underA);
        dest.writeValue(this.overB);
        dest.writeValue(this.underB);
        dest.writeValue(this.pointA);
        dest.writeValue(this.pointB);
        dest.writeString(this.pointSpreadStringA);
        dest.writeString(this.pointSpreadStringB);
        dest.writeString(this.sportsName);
    }

    public Game() {
    }

    private Game(Parcel in) {
        this.cstStartTime = in.readString();
        this.teamANumber = in.readString();
        this.teamBNumber = in.readString();
        this.sports = new ArrayList<String>();
        in.readList(this.sports, List.class.getClassLoader());
        in.readTypedList(teamAOdds, Odd.CREATOR);
        in.readTypedList(teamBOdds, Odd.CREATOR);
        this.teamAName = in.readString();
        this.teamBName = in.readString();
        this.teamAFullname = in.readString();
        this.teamBFullname = in.readString();
        this.teamANickname = in.readString();
        this.teamBNickname = in.readString();
        this.teamALogo = in.readString();
        this.teamBLogo = in.readString();
        this.pointSpreadA = (Double) in.readValue(Double.class.getClassLoader());
        this.pointSpreadB = (Double) in.readValue(Double.class.getClassLoader());
        this.moneyLineA = (Double) in.readValue(Double.class.getClassLoader());
        this.moneyLineB = (Double) in.readValue(Double.class.getClassLoader());
        this.overA = (Double) in.readValue(Double.class.getClassLoader());
        this.underA = (Double) in.readValue(Double.class.getClassLoader());
        this.overB = (Double) in.readValue(Double.class.getClassLoader());
        this.underB = (Double) in.readValue(Double.class.getClassLoader());
        this.pointA = (Double) in.readValue(Double.class.getClassLoader());
        this.pointB = (Double) in.readValue(Double.class.getClassLoader());
        this.pointSpreadStringA = in.readString();
        this.pointSpreadStringB = in.readString();
        this.sportsName = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public void setBettingValues () {
        for (Odd odd : getTeamAOdds()) {

            pointA = odd.getPoints().doubleValue();

            if (odd.getML().equals("")) {

                overA = odd.getOverMoney().doubleValue();
                underA = odd.getUnderMoney().doubleValue();

            } else if (odd.getML().equals("false")) {

                pointSpreadA = odd.getMoney().doubleValue();

                if (pointA != 0) {
                    pointSpreadStringA = Double.toString(pointA) + "(" + Double.toString(pointSpreadA) + ")";
                } else {
                    pointSpreadStringA = Double.toString(pointSpreadA);
                }

            } else if (odd.getML().equals("true")) {

                moneyLineA = odd.getMoney().doubleValue();

            }

        }

        for (Odd odd : getTeamBOdds()) {

            pointB = odd.getPoints().doubleValue();

            if (odd.getML().equals("")) {

                overB = odd.getOverMoney().doubleValue();
                underB = odd.getUnderMoney().doubleValue();

            } else if (odd.getML().equals("false")) {

                pointSpreadB = odd.getMoney().doubleValue();

                if (pointB != 0) {
                    pointSpreadStringB = Double.toString(pointB) + "(" + Double.toString(pointSpreadB) + ")";
                } else {
                    pointSpreadStringB = Double.toString(pointSpreadB);
                }

            } else if (odd.getML().equals("true")) {

                moneyLineB = odd.getMoney().doubleValue();

            }

        }
    }

    public String getTeamNameFromTeamNumber (String teamNumber) {
        return (getTeamANumber().equals(teamNumber)) ? getTeamAName() : getTeamBName();
    }
}

