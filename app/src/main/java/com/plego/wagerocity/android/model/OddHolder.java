package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OddHolder implements Parcelable, Cloneable {

    public OddHolder() {
    }

    public OddHolder(Double stake, String teamId, String oddId, String teamName, String teamVsteam, String oddValue, String betTypeSPT, String betOT, String betTypeString, String pointSpreadString, String leagueName) {
        this.stake = stake;
        this.teamId = teamId;
        this.oddId = oddId;
        this.teamName = teamName;
        this.teamVsteam = teamVsteam;
        this.oddValue = oddValue;
        this.pointSpreadString = pointSpreadString;
        this.betTypeSPT = betTypeSPT;
        this.betOT = betOT;
        this.betTypeString = betTypeString;
        this.isChecked = false;
        this.leagueName = leagueName;
        this.riskValue = "0";
    }

    Double stake;
    String teamId;
    String oddId;
    String teamName;
    String teamVsteam;
    String oddValue;
    String betTypeSPT;
    String betOT;
    String betTypeString;
    String pointSpreadString;
    Boolean isChecked;
    String leagueName;
    String riskValue;
    Double parlayValue;

    public Double getParlayValue() {
        return parlayValue;
    }

    public void setParlayValue(Double parlayValue) {
        this.parlayValue = parlayValue;
    }


    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(String riskValue) {
        this.riskValue = riskValue;
    }


    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }


    public String getPointSpreadString() {
        return pointSpreadString;
    }

    public void setPointSpreadString(String pointSpreadString) {
        this.pointSpreadString = pointSpreadString;
    }


    public String getBetTypeString() {
        return betTypeString;
    }

    public void setBetTypeString(String betTypeString) {
        this.betTypeString = betTypeString;
    }


    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getOddId() {
        return oddId;
    }

    public void setOddId(String oddId) {
        this.oddId = oddId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamVsteam() {
        return teamVsteam;
    }

    public void setTeamVsteam(String teamVsteam) {
        this.teamVsteam = teamVsteam;
    }

    public String getOddValue() {
        return oddValue;
    }

    public void setOddValue(String oddValue) {
        this.oddValue = oddValue;
    }

    public String getBetTypeSPT() {
        return betTypeSPT;
    }

    public void setBetTypeSPT(String betTypeSPT) {
        this.betTypeSPT = betTypeSPT;
    }

    public String getBetOT() {
        return betOT;
    }

    public void setBetOT(String betOT) {
        this.betOT = betOT;
    }




    @Override
    public OddHolder clone() throws CloneNotSupportedException {
        return (OddHolder) super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.stake);
        dest.writeString(this.teamId);
        dest.writeString(this.oddId);
        dest.writeString(this.teamName);
        dest.writeString(this.teamVsteam);
        dest.writeString(this.oddValue);
        dest.writeString(this.betTypeSPT);
        dest.writeString(this.betOT);
        dest.writeString(this.betTypeString);
        dest.writeString(this.pointSpreadString);
        dest.writeValue(this.isChecked);
        dest.writeString(this.leagueName);
        dest.writeString(this.riskValue);
    }

    private OddHolder(Parcel in) {
        this.stake = (Double) in.readValue(Double.class.getClassLoader());
        this.teamId = in.readString();
        this.oddId = in.readString();
        this.teamName = in.readString();
        this.teamVsteam = in.readString();
        this.oddValue = in.readString();
        this.betTypeSPT = in.readString();
        this.betOT = in.readString();
        this.betTypeString = in.readString();
        this.pointSpreadString = in.readString();
        this.isChecked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.leagueName = in.readString();
        this.riskValue = in.readString();
    }

    public static final Creator<OddHolder> CREATOR = new Creator<OddHolder>() {
        public OddHolder createFromParcel(Parcel source) {
            return new OddHolder(source);
        }

        public OddHolder[] newArray(int size) {
            return new OddHolder[size];
        }
    };
}
